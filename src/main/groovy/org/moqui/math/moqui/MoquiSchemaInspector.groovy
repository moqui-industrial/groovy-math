/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 *
 * To the extent possible under law, the author(s) have dedicated all
 * copyright and related and neighboring rights to this software to the
 * public domain worldwide. This software is distributed without any
 * warranty.
 *
 * You should have received a copy of the CC0 Public Domain Dedication
 * along with this software (see the LICENSE.md file). If not, see
 * <http://creativecommons.org/publicdomain/zero/1.0/>.
 */

package org.moqui.math.moqui

import groovy.transform.CompileStatic
import org.moqui.math.entity.EntityDefinition
import org.moqui.math.entity.EnumerationDefinition
import org.moqui.math.entity.FieldDefinition
import org.moqui.math.entity.ModelDefinition
import org.moqui.math.entity.RelationshipDefinition
import org.moqui.math.entity.StatusDefinition
import org.moqui.math.entity.StatusFlowItemDefinition
import org.moqui.math.entity.StatusTransitionDefinition
import org.moqui.math.entity.UomConversionDefinition
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList

import javax.xml.XMLConstants
import javax.xml.parsers.DocumentBuilderFactory

@CompileStatic
final class MoquiSchemaInspector {
    /** Classpath location of the schema vendored by scripts/sync_schema.py. */
    static final String EMBEDDED_RESOURCE_PATH = '/moqui-math'

    /**
     * Schema sources merged by {@link #embedded()}, in load order. BasicEntities must come
     * first so that moqui.basic entities exist before moqui.math relates to them.
     * MathData contributes no entities, only the enumeration seed data.
     * MathViewEntities is deliberately absent: it declares only view-entity elements, which
     * this inspector does not model yet.
     */
    static final List<String> EMBEDDED_SOURCES =
        Collections.unmodifiableList(['BasicEntities.xml', 'MathEntities.xml', 'MathData.xml'])

    /**
     * The moqui.basic entities Groovy Math actually needs. moqui.math relates to exactly
     * Enumeration, Uom, StatusItem and StatusFlow (plus moqui.security.UserAccount, which
     * lives in another file and is only ever a one-nofk reference). The enumeration and
     * status-flow families are pulled in whole because the enum catalogue and the FSM are
     * built on them. Email, print, geo, localization and datasource entities are left out:
     * they would only pollute the DSL namespace.
     */
    static final Set<String> EMBEDDED_BASIC_ENTITIES = Collections.unmodifiableSet(new LinkedHashSet<String>([
        'moqui.basic.Enumeration', 'moqui.basic.EnumerationType', 'moqui.basic.EnumGroupMember',
        'moqui.basic.StatusItem', 'moqui.basic.StatusType', 'moqui.basic.StatusFlow',
        'moqui.basic.StatusFlowItem', 'moqui.basic.StatusFlowTransition',
        'moqui.basic.Uom', 'moqui.basic.UomConversion', 'moqui.basic.UomGroupMember',
        'moqui.basic.UomDimensionType', 'moqui.basic.UomDimTypeGroupMember',
    ]))

    private static volatile ModelDefinition embeddedModel

    static ModelDefinition inspect(final File source) {
        if (source == null || !source.isFile()) {
            throw new IllegalArgumentException("Schema file does not exist: ${source}")
        }
        ModelDefinition model = new ModelDefinition()
        source.withInputStream { InputStream stream -> merge(model, stream, source.name) }
        model
    }

    /**
     * The schema vendored inside the jar, merged into one definition. This is what lets a
     * plain clone of Groovy Math build and run without a Moqui checkout. The returned
     * definition is cached and shared: treat it as read-only.
     */
    static ModelDefinition embedded() {
        ModelDefinition cached = embeddedModel
        if (cached != null) return cached
        synchronized (MoquiSchemaInspector) {
            if (embeddedModel == null) embeddedModel = loadEmbedded()
            return embeddedModel
        }
    }

    private static ModelDefinition loadEmbedded() {
        ModelDefinition model = new ModelDefinition()
        EMBEDDED_SOURCES.each { String name ->
            String path = "${EMBEDDED_RESOURCE_PATH}/${name}"
            InputStream stream = MoquiSchemaInspector.getResourceAsStream(path)
            if (stream == null) {
                throw new IllegalStateException(
                    "Embedded schema resource ${path} is missing; run ./gradlew syncMoquiSchema")
            }
            try {
                merge(model, stream, name)
            } finally {
                stream.close()
            }
        }
        model
    }

    /**
     * Parses one schema source into an existing definition. Entities outside
     * {@link #EMBEDDED_BASIC_ENTITIES} are skipped for non-math packages, and an
     * extend-entity whose target was skipped is skipped with it rather than failing.
     */
    static ModelDefinition merge(final ModelDefinition model, final InputStream source, final String sourceName) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance()
        factory.setFeature('http://apache.org/xml/features/disallow-doctype-decl', true)
        factory.setFeature('http://xml.org/sax/features/external-general-entities', false)
        factory.setFeature('http://xml.org/sax/features/external-parameter-entities', false)
        factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, '')
        factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, '')

        Element root
        try {
            root = factory.newDocumentBuilder().parse(source).documentElement
        } catch (Exception failure) {
            throw new IllegalStateException("Cannot parse Moqui schema ${sourceName}: ${failure.message}", failure)
        }

        childElements(root, 'entity').each { Element node ->
            if (accepts(node)) model.addEntity(parseEntity(node))
        }
        childElements(root, 'extend-entity').each { Element node ->
            if (accepts(node) && model.entities.containsKey(fullName(node))) mergeExtension(model, node)
        }
        // Enumerations are global data: the seed-data block they sit in is documentation, so
        // every one found in this source joins the single catalogue. A later source redeclaring
        // an id wins, which is how MathData.xml is meant to refine MathEntities.xml.
        descendantElements(root, 'moqui.basic.EnumerationType').each { Element node ->
            model.addEnumerationType(node.getAttribute('enumTypeId'))
        }
        List<Element> enumerationNodes = descendantElements(root, 'moqui.basic.Enumeration')
        enumerationNodes.each { Element node ->
            model.addEnumeration(new EnumerationDefinition(
                node.getAttribute('enumId'),
                attributeOrNull(node, 'enumTypeId'),
                attributeOrNull(node, 'enumCode'),
                attributeOrNull(node, 'parentEnumId'),
                attributeOrNull(node, 'description')))
        }
        model.enumerationCount += enumerationNodes.size()

        // Status seed data is global in the same way enumerations are: the entity whose seed-data
        // block declares a StatusItem is documentation, not scope.
        descendantElements(root, 'moqui.basic.StatusItem').each { Element node ->
            model.addStatus(new StatusDefinition(
                node.getAttribute('statusId'),
                attributeOrNull(node, 'statusTypeId'),
                attributeOrNull(node, 'statusCode'),
                integerOrNull(node, 'sequenceNum'),
                attributeOrNull(node, 'description')))
        }
        descendantElements(root, 'moqui.basic.StatusFlow').each { Element node ->
            model.addStatusFlow(node.getAttribute('statusFlowId'))
        }
        descendantElements(root, 'moqui.basic.StatusFlowItem').each { Element node ->
            model.addStatusFlowItem(new StatusFlowItemDefinition(
                node.getAttribute('statusFlowId'),
                node.getAttribute('statusId'),
                attributeOrNull(node, 'isInitial'),
                integerOrNull(node, 'sequenceNum')))
        }
        descendantElements(root, 'moqui.basic.StatusFlowTransition').each { Element node ->
            model.addStatusTransition(new StatusTransitionDefinition(
                attributeOrNull(node, 'statusFlowId'),
                node.getAttribute('statusId'),
                node.getAttribute('toStatusId'),
                attributeOrNull(node, 'transitionName'),
                integerOrNull(node, 'transitionSequence'),
                attributeOrNull(node, 'userPermissionId')))
        }
        descendantElements(root, 'moqui.basic.UomConversion').each { Element node ->
            model.addUomConversion(new UomConversionDefinition(
                attributeOrNull(node, 'uomConversionId'),
                node.getAttribute('uomId'),
                node.getAttribute('toUomId'),
                timestampOrNull(node, 'fromDate'),
                timestampOrNull(node, 'thruDate'),
                doubleOrNull(node, 'conversionFactor'),
                decimalOrNull(node, 'conversionOffset'),
                attributeOrNull(node, 'purposeEnumId')))
        }
        model
    }

    private static boolean accepts(final Element node) {
        String packageName = node.getAttribute('package') ?: ''
        packageName.startsWith('moqui.math') || EMBEDDED_BASIC_ENTITIES.contains(fullName(node))
    }

    private static String fullName(final Element node) {
        String packageName = node.getAttribute('package') ?: ''
        String entityName = node.getAttribute('entity-name')
        packageName ? "${packageName}.${entityName}" : entityName
    }

    static String summary(final ModelDefinition model) {
        int fieldCount = 0
        int relationshipCount = 0
        model.entities.values().each { EntityDefinition entity ->
            fieldCount += entity.fields.size()
            relationshipCount += entity.relationships.size()
        }
        "entities=${model.entities.size()}, extensions=${model.extensionCount}, fields=${fieldCount}, " +
            "relationships=${relationshipCount}, enumerations=${model.enumerationCount}"
    }

    private static EntityDefinition parseEntity(final Element node) {
        EntityDefinition entity = new EntityDefinition(
            node.getAttribute('package'), node.getAttribute('entity-name'), attributeOrNull(node, 'short-alias')
        )
        childElements(node, 'field').each { Element field -> entity.addField(parseField(field)) }
        childElements(node, 'relationship').each { Element relationship ->
            entity.addRelationship(parseRelationship(relationship))
            recordEnumField(entity, relationship)
        }
        entity
    }

    /**
     * Records which enumeration type an *EnumId field draws from, taken from the relationship
     * title. Moqui's convention is that a relationship to moqui.basic.Enumeration is titled with
     * the enumTypeId, and every one of the 95 such relationships in MathEntities.xml follows it.
     */
    private static void recordEnumField(final EntityDefinition entity, final Element relationship) {
        if (relationship.getAttribute('related') != 'moqui.basic.Enumeration') return
        String enumTypeId = attributeOrNull(relationship, 'title')
        if (enumTypeId == null) return
        List<Element> keys = childElements(relationship, 'key-map')
        if (keys.size() != 1) return
        entity.declareEnumField(keys.first().getAttribute('field-name'), enumTypeId)
    }

    private static void mergeExtension(final ModelDefinition model, final Element node) {
        String packageName = node.getAttribute('package')
        String entityName = node.getAttribute('entity-name')
        String fullEntityName = packageName ? packageName + '.' + entityName : entityName
        EntityDefinition entity = model.entity(fullEntityName)
        childElements(node, 'field').each { Element field -> entity.replaceOrAddField(parseField(field)) }
        childElements(node, 'relationship').each { Element relationship ->
            entity.replaceOrAddRelationship(parseRelationship(relationship))
            recordEnumField(entity, relationship)
        }
        model.extensionCount++
    }

    private static FieldDefinition parseField(final Element node) {
        new FieldDefinition(
            node.getAttribute('name'),
            node.getAttribute('type'),
            node.getAttribute('is-pk') == 'true',
            node.getAttribute('not-null') == 'true',
            attributeOrNull(node, 'default')
        )
    }

    private static RelationshipDefinition parseRelationship(final Element node) {
        LinkedHashMap<String, String> keyMap = new LinkedHashMap<>()
        childElements(node, 'key-map').each { Element key ->
            String fieldName = key.getAttribute('field-name')
            String relatedName = attributeOrNull(key, 'related') ?: fieldName
            keyMap.put(fieldName, relatedName)
        }
        String related = node.getAttribute('related')
        String alias = attributeOrNull(node, 'short-alias') ?: attributeOrNull(node, 'title')
        String name = alias ?: related.tokenize('.').last()
        new RelationshipDefinition(name, node.getAttribute('type'), related, keyMap)
    }

    private static Integer integerOrNull(final Element node, final String name) {
        String value = attributeOrNull(node, name)
        value == null ? null : Integer.valueOf(value)
    }

    private static Double doubleOrNull(final Element node, final String name) {
        String value = attributeOrNull(node, name)
        value == null ? null : Double.valueOf(value)
    }

    private static BigDecimal decimalOrNull(final Element node, final String name) {
        String value = attributeOrNull(node, name)
        value == null ? null : new BigDecimal(value)
    }

    private static java.sql.Timestamp timestampOrNull(final Element node, final String name) {
        String value = attributeOrNull(node, name)
        if (value == null) return null
        String normalized = value.trim()
        if (normalized.length() == 10) normalized += ' 00:00:00'
        if (normalized.contains('T')) normalized = normalized.replace('T', ' ')
        try {
            return java.sql.Timestamp.valueOf(normalized)
        } catch (Exception ignored) {
            return null
        }
    }

    private static String attributeOrNull(final Element node, final String name) {
        String value = node.getAttribute(name)
        value ? value : null
    }

    private static List<Element> childElements(final Element parent, final String tagName) {
        List<Element> result = []
        Node child = parent.firstChild
        while (child != null) {
            if (child instanceof Element && ((Element) child).tagName == tagName) result.add((Element) child)
            child = child.nextSibling
        }
        result
    }

    private static List<Element> descendantElements(final Element parent, final String tagName) {
        List<Element> result = []
        NodeList nodes = parent.getElementsByTagName(tagName)
        for (int i = 0; i < nodes.length; i++) result.add((Element) nodes.item(i))
        result
    }
}

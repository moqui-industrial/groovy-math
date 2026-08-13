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
import org.moqui.math.model.EntityDefinition
import org.moqui.math.model.FieldDefinition
import org.moqui.math.model.ModelDefinition
import org.moqui.math.model.RelationshipDefinition
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList

import javax.xml.XMLConstants
import javax.xml.parsers.DocumentBuilderFactory

@CompileStatic
final class MoquiSchemaInspector {
    static ModelDefinition inspect(final File source) {
        if (source == null || !source.isFile()) {
            throw new IllegalArgumentException("Schema file does not exist: ${source}")
        }

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance()
        factory.setFeature('http://apache.org/xml/features/disallow-doctype-decl', true)
        factory.setFeature('http://xml.org/sax/features/external-general-entities', false)
        factory.setFeature('http://xml.org/sax/features/external-parameter-entities', false)
        factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, '')
        factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, '')

        Element root = factory.newDocumentBuilder().parse(source).documentElement
        ModelDefinition model = new ModelDefinition()

        childElements(root, 'entity').each { Element node -> model.addEntity(parseEntity(node)) }
        childElements(root, 'extend-entity').each { Element node -> mergeExtension(model, node) }
        model.enumerationCount = descendantElements(root, 'moqui.basic.Enumeration').size()
        model
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
        }
        entity
    }

    private static void mergeExtension(final ModelDefinition model, final Element node) {
        String packageName = node.getAttribute('package')
        String entityName = node.getAttribute('entity-name')
        String fullEntityName = packageName ? packageName + '.' + entityName : entityName
        EntityDefinition entity = model.entity(fullEntityName)
        childElements(node, 'field').each { Element field -> entity.replaceOrAddField(parseField(field)) }
        childElements(node, 'relationship').each { Element relationship ->
            entity.replaceOrAddRelationship(parseRelationship(relationship))
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

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

package org.moqui.math.entity

import groovy.transform.CompileStatic

@CompileStatic
final class ModelDefinition {
    final LinkedHashMap<String, EntityDefinition> entities = new LinkedHashMap<>()
    /** Every declared enumeration value, by enumId. */
    final LinkedHashMap<String, EnumerationDefinition> enumerations = new LinkedHashMap<>()
    /** enumTypeId -> enumCode -> enumId. Codes are unique per type, not globally. */
    private final LinkedHashMap<String, LinkedHashMap<String, String>> codeIndex = new LinkedHashMap<>()
    /** enumTypeId values actually declared as moqui.basic.EnumerationType. */
    final LinkedHashSet<String> enumerationTypes = new LinkedHashSet<>()
    /** Every declared moqui.basic.StatusItem, by statusId. */
    final LinkedHashMap<String, StatusDefinition> statuses = new LinkedHashMap<>()
    /** Every declared moqui.basic.StatusFlowTransition, in declaration order. */
    final List<StatusTransitionDefinition> statusTransitions = []
    /** statusFlowId values declared as moqui.basic.StatusFlow. */
    final LinkedHashSet<String> statusFlows = new LinkedHashSet<>()
    /** Every declared moqui.basic.StatusFlowItem. */
    final LinkedHashMap<String, StatusFlowItemDefinition> statusFlowItems = new LinkedHashMap<>()
    /** Every declared moqui.basic.UomConversion. */
    final List<UomConversionDefinition> uomConversions = []
    int extensionCount
    /** Number of Enumeration elements parsed, including any that redeclare an existing id. */
    int enumerationCount

    void addEnumeration(final EnumerationDefinition enumeration) {
        Objects.requireNonNull(enumeration, 'Enumeration definition must not be null')
        enumerations.put(enumeration.enumId, enumeration)
        if (enumeration.enumCode && enumeration.enumTypeId) {
            codeIndex.computeIfAbsent(enumeration.enumTypeId) { String key -> new LinkedHashMap<String, String>() }
                .put(enumeration.enumCode, enumeration.enumId)
        }
    }

    void addEnumerationType(final String enumTypeId) {
        if (enumTypeId) enumerationTypes.add(enumTypeId)
    }

    /**
     * Whether this name is a declared enumeration type.
     *
     * <p>Not every relationship title is one. A relationship to moqui.basic.Enumeration is titled
     * to give the relationship a name, and that title usually equals the enumTypeId, but for
     * fields such as Matrix.domainSpaceEnumId it names the role ("DomainVectorSpace") while the
     * field draws from an enum group instead. Validating against a type that was never declared
     * would report every such field as broken, so those fields go unchecked and are listed
     * separately as a gap in the schema rather than as an error in the model.
     */
    boolean isDeclaredEnumerationType(final String enumTypeId) {
        enumerationTypes.contains(enumTypeId)
    }

    void addStatus(final StatusDefinition status) {
        Objects.requireNonNull(status, 'Status definition must not be null')
        statuses.put(status.statusId, status)
    }

    void addStatusTransition(final StatusTransitionDefinition transition) {
        Objects.requireNonNull(transition, 'Status transition must not be null')
        statusTransitions.add(transition)
    }

    void addStatusFlow(final String statusFlowId) {
        if (statusFlowId) statusFlows.add(statusFlowId)
    }

    void addStatusFlowItem(final StatusFlowItemDefinition item) {
        Objects.requireNonNull(item, 'Status flow item must not be null')
        statusFlowItems.put("${item.statusFlowId}:${item.statusId}".toString(), item)
    }

    void addUomConversion(final UomConversionDefinition conversion) {
        Objects.requireNonNull(conversion, 'UOM conversion must not be null')
        uomConversions.add(conversion)
    }

    /** The declared status for this id, or null. */
    StatusDefinition status(final String statusId) {
        statuses.get(statusId)
    }

    /** Declared statuses of one type, ordered by sequenceNum then id. */
    List<StatusDefinition> statusesOfType(final String statusTypeId) {
        List<StatusDefinition> found = new ArrayList<StatusDefinition>(
            statuses.values().findAll { StatusDefinition value -> value.statusTypeId == statusTypeId })
        found.sort { StatusDefinition left, StatusDefinition right ->
            int bySequence = (left.sequenceNum != null ? left.sequenceNum : Integer.MAX_VALUE) <=>
                (right.sequenceNum != null ? right.sequenceNum : Integer.MAX_VALUE)
            bySequence != 0 ? bySequence : left.statusId <=> right.statusId
        }
        found
    }

    /** Alias for statusesOfType, matching Moqui service semantics. */
    List<StatusDefinition> statusItemsByType(final String statusTypeId) {
        statusesOfType(statusTypeId)
    }

    /**
     * Resolves the initial status of a status flow (from StatusFlowItem.isInitial, or flow transitions).
     */
    String initialStatus(final String statusFlowId) {
        if (!statusFlowId) return null
        for (StatusFlowItemDefinition item : statusFlowItems.values()) {
            if (item.statusFlowId == statusFlowId && (item.isInitial == 'Y' || item.isInitial == 'true')) {
                return item.statusId
            }
        }
        List<StatusFlowItemDefinition> flowItems = statusFlowItems.values().findAll { it.statusFlowId == statusFlowId } as List<StatusFlowItemDefinition>
        if (!flowItems.isEmpty()) {
            flowItems.sort { StatusFlowItemDefinition a, StatusFlowItemDefinition b ->
                int seqA = a.sequenceNum != null ? a.sequenceNum : Integer.MAX_VALUE
                int seqB = b.sequenceNum != null ? b.sequenceNum : Integer.MAX_VALUE
                seqA <=> seqB
            }
            return flowItems.first().statusId
        }
        // Fallback: look at transitions for this flow
        List<StatusTransitionDefinition> trans = statusTransitions.findAll { it.statusFlowId == statusFlowId }
        if (!trans.isEmpty()) {
            Set<String> toStatuses = trans.collect { it.toStatusId } as Set<String>
            StatusTransitionDefinition initialCandidate = trans.find { !toStatuses.contains(it.statusId) }
            if (initialCandidate != null) return initialCandidate.statusId
            return trans.first().statusId
        }
        null
    }

    /**
     * Transitions out of a status. With no flow given the search spans every flow, which is what
     * Moqui's own validity check does; naming a flow narrows it the way automatic advancement does.
     */
    List<StatusTransitionDefinition> transitionsFrom(final String statusId, final String statusFlowId = null) {
        // If statusId looks like a statusFlowId and statusFlowId is a valid statusId, handle inverted order gracefully
        if (statusFlowId != null && statusFlows.contains(statusId) && statuses.containsKey(statusFlowId)) {
            return transitionsFrom(statusFlowId, statusId)
        }
        List<StatusTransitionDefinition> found = new ArrayList<StatusTransitionDefinition>(
            statusTransitions.findAll { StatusTransitionDefinition value ->
                value.statusId == statusId && (statusFlowId == null || value.statusFlowId == statusFlowId)
            })
        found.sort { StatusTransitionDefinition left, StatusTransitionDefinition right ->
            int bySequence = (left.transitionSequence != null ? left.transitionSequence : Integer.MAX_VALUE) <=>
                (right.transitionSequence != null ? right.transitionSequence : Integer.MAX_VALUE)
            bySequence != 0 ? bySequence : left.toStatusId <=> right.toStatusId
        }
        found
    }

    /** The declared enumeration for this id, or null. */
    EnumerationDefinition enumeration(final String enumId) {
        enumerations.get(enumId)
    }

    /** Alias for enumerationsOfType matching find#Enumeration. */
    List<EnumerationDefinition> enumerationsByType(final String enumTypeId) {
        enumerationsOfType(enumTypeId)
    }

    /** Alias for enumerationsUnder matching find#EnumerationByParent. */
    List<EnumerationDefinition> enumerationsByParent(final String parentEnumId,
                                                    final boolean includeParent = true,
                                                    final boolean includeNested = false) {
        enumerationsUnder(parentEnumId, includeParent, includeNested)
    }

    /**
     * Converts a numeric amount from one UOM to another following convert#Uom service semantics:
     * - if uomId == toUomId, returns amount unchanged.
     * - finds valid UomConversion at effectiveDate, ordered by -fromDate.
     * - direct: (amount * factor) + offset
     * - inverse: (amount - offset) / factor
     * - throws exception if conversion cannot be performed.
     */
    BigDecimal uomConvert(final Number amount, final String uomId, final String toUomId, final Object effectiveDate = null) {
        if (amount == null) return null
        BigDecimal amt = amount instanceof BigDecimal ? (BigDecimal) amount : new BigDecimal(amount.toString())
        if (uomId == null || toUomId == null) {
            throw new IllegalArgumentException("uomId and toUomId must not be null (got uomId=${uomId}, toUomId=${toUomId})")
        }
        if (uomId == toUomId) return amt

        java.sql.Timestamp effectiveTime
        if (effectiveDate instanceof java.sql.Timestamp) {
            effectiveTime = (java.sql.Timestamp) effectiveDate
        } else if (effectiveDate instanceof java.util.Date) {
            effectiveTime = new java.sql.Timestamp(((java.util.Date) effectiveDate).getTime())
        } else if (effectiveDate instanceof CharSequence) {
            effectiveTime = java.sql.Timestamp.valueOf(effectiveDate.toString())
        } else {
            effectiveTime = new java.sql.Timestamp(System.currentTimeMillis())
        }

        // 1. Direct conversion
        List<UomConversionDefinition> directCandidates = uomConversions.findAll { UomConversionDefinition conv ->
            conv.uomId == uomId && conv.toUomId == toUomId &&
            (conv.fromDate == null || conv.fromDate.time <= effectiveTime.time) &&
            (conv.thruDate == null || conv.thruDate.time >= effectiveTime.time)
        }
        if (!directCandidates.isEmpty()) {
            directCandidates.sort { UomConversionDefinition left, UomConversionDefinition right ->
                long lTime = left.fromDate != null ? left.fromDate.time : Long.MIN_VALUE
                long rTime = right.fromDate != null ? right.fromDate.time : Long.MIN_VALUE
                rTime <=> lTime // -fromDate
            }
            UomConversionDefinition best = directCandidates.first()
            BigDecimal factor = best.conversionFactor != null ? BigDecimal.valueOf(best.conversionFactor) : BigDecimal.ONE
            BigDecimal offset = best.conversionOffset != null ? best.conversionOffset : BigDecimal.ZERO
            return (amt.multiply(factor)).add(offset)
        }

        // 2. Inverse conversion
        List<UomConversionDefinition> inverseCandidates = uomConversions.findAll { UomConversionDefinition conv ->
            conv.uomId == toUomId && conv.toUomId == uomId &&
            (conv.fromDate == null || conv.fromDate.time <= effectiveTime.time) &&
            (conv.thruDate == null || conv.thruDate.time >= effectiveTime.time)
        }
        if (!inverseCandidates.isEmpty()) {
            inverseCandidates.sort { UomConversionDefinition left, UomConversionDefinition right ->
                long lTime = left.fromDate != null ? left.fromDate.time : Long.MIN_VALUE
                long rTime = right.fromDate != null ? right.fromDate.time : Long.MIN_VALUE
                rTime <=> lTime // -fromDate
            }
            UomConversionDefinition best = inverseCandidates.first()
            BigDecimal factor = best.conversionFactor != null ? BigDecimal.valueOf(best.conversionFactor) : BigDecimal.ONE
            BigDecimal offset = best.conversionOffset != null ? best.conversionOffset : BigDecimal.ZERO
            if (factor.compareTo(BigDecimal.ZERO) == 0) {
                throw new ArithmeticException("Cannot convert UOM: conversion factor is zero for ${toUomId} -> ${uomId}")
            }
            return (amt.subtract(offset)).divide(factor, java.math.MathContext.DECIMAL64)
        }

        throw new IllegalArgumentException("No UOM conversion found from ${uomId} to ${toUomId}")
    }

    /**
     * Resolves a code within its type to the enumId actually stored.
     *
     * <p>enumCode is not unique on its own, only within an enumeration type, so it cannot be a
     * key. Selection in Groovy Math is on enumId throughout; this exists to read the schema
     * faithfully, not as an addressing scheme.
     */
    String enumIdForCode(final String enumTypeId, final String enumCode) {
        codeIndex.get(enumTypeId)?.get(enumCode)
    }

    /**
     * Every declared value of one enumeration type, ordered by description.
     *
     * <p>Mirrors Moqui's find#Enumeration service, which orders by description by default, so a
     * model that reads an enumeration type here sees it in the same order the framework would
     * have served it from the database.
     */
    List<EnumerationDefinition> enumerationsOfType(final String enumTypeId) {
        List<EnumerationDefinition> found = new ArrayList<EnumerationDefinition>(
            enumerations.values().findAll { EnumerationDefinition value -> value.enumTypeId == enumTypeId })
        found.sort { EnumerationDefinition left, EnumerationDefinition right ->
            (left.description ?: left.enumId) <=> (right.description ?: right.enumId)
        }
        found
    }

    /**
     * The values declared beneath a parent enumeration, ordered by description.
     *
     * <p>Mirrors Moqui's find#EnumerationByParent service, including its two switches: the parent
     * itself is included by default, and the walk is one level deep unless nested is asked for.
     * The schema states backend families this way, so this is how a rule asks "which runtimes
     * are OpenFOAM" without writing the answer down and letting it go stale.
     */
    List<EnumerationDefinition> enumerationsUnder(final String parentEnumId,
                                                  final boolean includeParent = true,
                                                  final boolean includeNested = false) {
        List<EnumerationDefinition> found = []
        EnumerationDefinition parent = enumerations.get(parentEnumId)
        if (includeParent && parent != null) found.add(parent)
        collectChildren(parentEnumId, found, includeNested, new LinkedHashSet<String>())
        found.sort { EnumerationDefinition left, EnumerationDefinition right ->
            (left.description ?: left.enumId) <=> (right.description ?: right.enumId)
        }
        found
    }

    /** The ids of {@link #enumerationsUnder}, for membership tests. */
    Set<String> enumIdsUnder(final String parentEnumId, final boolean includeParent = true,
                             final boolean includeNested = false) {
        Set<String> ids = new LinkedHashSet<>()
        enumerationsUnder(parentEnumId, includeParent, includeNested)
            .each { EnumerationDefinition value -> ids.add(value.enumId) }
        ids
    }

    private void collectChildren(final String parentEnumId, final List<EnumerationDefinition> target,
                                 final boolean includeNested, final Set<String> visited) {
        if (!visited.add(parentEnumId)) return
        enumerations.values().each { EnumerationDefinition candidate ->
            if (candidate.parentEnumId != parentEnumId) return
            target.add(candidate)
            if (includeNested) collectChildren(candidate.enumId, target, true, visited)
        }
    }

    /**
     * Whether an enumeration is the given one or descends from it through parentEnumId.
     *
     * <p>The schema already expresses backend families this way: MmsmJaxJit declares MmsmJax as
     * its parent, MmsmOpenFoamIcoFoam declares MmsmOpenFoam. Walking the hierarchy is how a rule
     * can say "any JAX runtime" without listing the variants by hand and going stale when a new
     * one is seeded.
     */
    boolean isEnumOrDescendantOf(final String enumId, final String ancestorEnumId) {
        if (enumId == null || ancestorEnumId == null) return false
        String current = enumId
        Set<String> seen = new LinkedHashSet<>()
        while (current != null && seen.add(current)) {
            if (current == ancestorEnumId) return true
            current = enumerations.get(current)?.parentEnumId
        }
        false
    }

    void addEntity(final EntityDefinition entity) {
        Objects.requireNonNull(entity, 'Entity definition must not be null')
        if (entities.putIfAbsent(entity.fullName, entity) != null) {
            throw new IllegalArgumentException("Duplicate entity ${entity.fullName}")
        }
    }

    boolean hasEntity(final String name) {
        if (entities.containsKey(name)) return true
        entities.values().any { EntityDefinition entity -> entity.name == name || entity.shortAlias == name }
    }

    EntityDefinition entity(final String name) {
        EntityDefinition exact = entities.get(name)
        if (exact != null) return exact

        List<EntityDefinition> matches = new ArrayList<EntityDefinition>(entities.values().findAll {
            EntityDefinition entity -> entity.name == name || entity.shortAlias == name
        })
        if (matches.size() == 1) return matches.first()
        if (matches.empty) throw new IllegalArgumentException("Unknown entity ${name}")
        throw new IllegalArgumentException("Ambiguous entity ${name}: ${matches*.fullName.join(', ')}")
    }
}

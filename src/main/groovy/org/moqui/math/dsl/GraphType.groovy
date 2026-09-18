/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: GraphType
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum GraphType implements DslEnumValue {
    Directed('GtDirected', '', 'Directed', ''),
    DirectedSimple('GtDirectedSimple', '', 'Directed Simple Graph', 'GtDirected'),
    DirectedSimpleWithLoops('GtDirectedSimpleWithLoops', '', 'Directed Simple Graph Permitting Loops', 'GtDirectedSimple'),
    DirectedMulti('GtDirectedMulti', '', 'Directed Multigraph', 'GtDirected'),
    DirectedMultiWithLoops('GtDirectedMultiWithLoops', '', 'Directed Multigraph Permitting Loops', 'GtDirectedMulti'),
    Oriented('GtOriented', '', 'Oriented Graph', 'GtDirected'),
    DirectedRegular('GtDirectedRegular', '', 'Directed Regular Graph', 'GtDirected'),
    DAG('GtDAG', '', 'Directed Acyclic Graph (DAG)', 'GtDirected'),
    Polytree('GtPolytree', '', 'Polytree Graph', 'GtDAG'),
    Polyforest('GtPolyforest', '', 'Polyforest Graph', 'GtDAG'),
    Undirected('GtUndirected', '', 'Undirected', ''),
    UndirectedSimple('GtUndirectedSimple', '', 'Undirected Simple Graph', 'GtUndirected'),
    UndirectedSimpleWithLoops('GtUndirectedSimpleWithLoops', '', 'Undirected Simple Graph Permitting Loops', 'GtUndirectedSimple'),
    UndirectedMulti('GtUndirectedMulti', '', 'Undirected Multigraph', 'GtUndirected'),
    UndirectedMultiWithLoops('GtUndirectedMultiWithLoops', '', 'Undirected Multigraph Permitting loops', 'GtUndirectedMulti'),
    UndirectedRegular('GtUndirectedRegular', '', 'Undirected Regular Graph', 'GtUndirected'),
    Tree('GtTree', '', 'Tree Graph', 'GtUndirected'),
    Forest('GtForest', '', 'Forest Graph', 'GtUndirected'),
    Mixed('GtMixed', '', 'Mixed Graph', ''),
    Weighted('GtWeighted', '', 'Weighted Graph', ''),
    Hypergraph('GtHypergraph', '', 'Hypergraph', ''),
    Lattice('GtLattice', '', 'Lattice Graph', '');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    GraphType(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
        this.id = id
        this.enumCode = enumCode
        this.description = description
        this.parentEnumId = parentEnumId
    }

    @Override
    String getId() { id }

    @Override
    String getEnumCode() { enumCode }

    @Override
    String getDescription() { description }

    @Override
    String getParentEnumId() { parentEnumId }

    static GraphType fromId(final String id) {
        if (id == null) return null
        for (GraphType val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static GraphType fromCode(final String code) {
        if (code == null) return null
        for (GraphType val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }

    static GraphType fromName(final String name) {
        if (name == null) return null
        for (GraphType val : values()) {
            if (val.name().equalsIgnoreCase(name) || val.id.equalsIgnoreCase(name) || (val.enumCode != null && val.enumCode.equalsIgnoreCase(name))) return val
        }
        if ('DirectedSimpleGraph'.equalsIgnoreCase(name)) return DirectedSimple
        if ('DirectedSimpleGraphPermittingLoops'.equalsIgnoreCase(name)) return DirectedSimpleWithLoops
        if ('DirectedMultigraph'.equalsIgnoreCase(name)) return DirectedMulti
        if ('DirectedMultigraphPermittingLoops'.equalsIgnoreCase(name)) return DirectedMultiWithLoops
        if ('OrientedGraph'.equalsIgnoreCase(name)) return Oriented
        if ('DirectedRegularGraph'.equalsIgnoreCase(name)) return DirectedRegular
        if ('DirectedAcyclicGraph'.equalsIgnoreCase(name)) return DAG
        if ('PolytreeGraph'.equalsIgnoreCase(name)) return Polytree
        if ('PolyforestGraph'.equalsIgnoreCase(name)) return Polyforest
        if ('UndirectedSimpleGraph'.equalsIgnoreCase(name)) return UndirectedSimple
        if ('UndirectedSimpleGraphPermittingLoops'.equalsIgnoreCase(name)) return UndirectedSimpleWithLoops
        if ('UndirectedMultigraph'.equalsIgnoreCase(name)) return UndirectedMulti
        if ('UndirectedMultigraphPermittingLoops'.equalsIgnoreCase(name)) return UndirectedMultiWithLoops
        if ('UndirectedRegularGraph'.equalsIgnoreCase(name)) return UndirectedRegular
        if ('TreeGraph'.equalsIgnoreCase(name)) return Tree
        if ('ForestGraph'.equalsIgnoreCase(name)) return Forest
        if ('MixedGraph'.equalsIgnoreCase(name)) return Mixed
        if ('WeightedGraph'.equalsIgnoreCase(name)) return Weighted
        if ('LatticeGraph'.equalsIgnoreCase(name)) return Lattice
        null
    }
}

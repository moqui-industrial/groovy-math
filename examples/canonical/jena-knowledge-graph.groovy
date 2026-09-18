/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

Graph('ResearchLabGraph', name: 'AI Research Institute Graph',
    description: 'Knowledge Graph of Researchers, Roles, Departments and Projects') {

    // Ontology Classes (as Vertices)
    GraphVertex('Class_Person', label: 'Person')
    GraphVertex('Class_Researcher', label: 'Researcher')
    GraphVertex('Class_SeniorResearcher', label: 'SeniorResearcher')
    GraphVertex('Class_Department', label: 'Department')

    // Instances (as Vertices) with Parameters
    GraphVertex('Alice', label: 'Alice Cooper') {
        Parameter('Param_Alice_Role', parameterDefId: 'jobTitle', textValue: 'Principal AI Scientist')
        Parameter('Param_Alice_Email', parameterDefId: 'email', textValue: 'alice@moqui-ai.org')
    }
    GraphVertex('Bob', label: 'Bob Martin') {
        Parameter('Param_Bob_Role', parameterDefId: 'jobTitle', textValue: 'Postdoc Researcher')
    }
    GraphVertex('Charlie', label: 'Charlie Brown')
    GraphVertex('AI_Department', label: 'Deep Learning & Neuro-Symbolic Lab') {
        Parameter('Param_Dept_Loc', parameterDefId: 'location', textValue: 'Rome Innovation Hub')
    }

    // Class Hierarchy (RDFS SubClassOf Edges)
    GraphEdge('Edge_H1', fromVertexId: 'Class_SeniorResearcher', toVertexId: 'Class_Researcher', label: 'subClassOf')
    GraphEdge('Edge_H2', fromVertexId: 'Class_Researcher', toVertexId: 'Class_Person', label: 'subClassOf')

    // Instance Types (RDF Type Edges)
    GraphEdge('Edge_T1', fromVertexId: 'Alice', toVertexId: 'Class_SeniorResearcher', label: 'type')
    GraphEdge('Edge_T2', fromVertexId: 'Bob', toVertexId: 'Class_Researcher', label: 'type')
    GraphEdge('Edge_T3', fromVertexId: 'Charlie', toVertexId: 'Class_Person', label: 'type')
    GraphEdge('Edge_T4', fromVertexId: 'AI_Department', toVertexId: 'Class_Department', label: 'type')

    // Semantic Relationships (Graph Edges)
    GraphEdge('Edge_R1', fromVertexId: 'Alice', toVertexId: 'AI_Department', label: 'leads')
    GraphEdge('Edge_R2', fromVertexId: 'Bob', toVertexId: 'AI_Department', label: 'memberOf')
    GraphEdge('Edge_R3', fromVertexId: 'Alice', toVertexId: 'Bob', label: 'supervises')
}

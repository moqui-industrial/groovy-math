graph('ResearchLabGraph', name: 'AI Research Institute Graph',
    description: 'Knowledge Graph of Researchers, Roles, Departments and Projects') {

    // Ontology Classes (as Vertices)
    vertex('Class_Person', label: 'Person')
    vertex('Class_Researcher', label: 'Researcher')
    vertex('Class_SeniorResearcher', label: 'SeniorResearcher')
    vertex('Class_Department', label: 'Department')

    // Instances (as Vertices) with Parameters
    vertex('Alice', label: 'Alice Cooper') {
        parameter('Param_Alice_Role', parameterDefId: 'jobTitle', textValue: 'Principal AI Scientist')
        parameter('Param_Alice_Email', parameterDefId: 'email', textValue: 'alice@moqui-ai.org')
    }
    vertex('Bob', label: 'Bob Martin') {
        parameter('Param_Bob_Role', parameterDefId: 'jobTitle', textValue: 'Postdoc Researcher')
    }
    vertex('Charlie', label: 'Charlie Brown')
    vertex('AI_Department', label: 'Deep Learning & Neuro-Symbolic Lab') {
        parameter('Param_Dept_Loc', parameterDefId: 'location', textValue: 'Rome Innovation Hub')
    }

    // Class Hierarchy (RDFS SubClassOf Edges)
    edge('Edge_H1', from: 'Class_SeniorResearcher', to: 'Class_Researcher', label: 'subClassOf')
    edge('Edge_H2', from: 'Class_Researcher', to: 'Class_Person', label: 'subClassOf')

    // Instance Types (RDF Type Edges)
    edge('Edge_T1', from: 'Alice', to: 'Class_SeniorResearcher', label: 'type')
    edge('Edge_T2', from: 'Bob', to: 'Class_Researcher', label: 'type')
    edge('Edge_T3', from: 'Charlie', to: 'Class_Person', label: 'type')
    edge('Edge_T4', from: 'AI_Department', to: 'Class_Department', label: 'type')

    // Semantic Relationships (Graph Edges)
    edge('Edge_R1', from: 'Alice', to: 'AI_Department', label: 'leads')
    edge('Edge_R2', from: 'Bob', to: 'AI_Department', label: 'memberOf')
    edge('Edge_R3', from: 'Alice', to: 'Bob', label: 'supervises')
}

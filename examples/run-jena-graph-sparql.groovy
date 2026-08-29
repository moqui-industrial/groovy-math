/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

import groovy.math.dsl.MathDsl
import groovy.math.dsl.MathMeta
import groovy.math.jena.Jena
import org.apache.jena.ontology.OntModel
import org.apache.jena.rdf.model.Model

String schemaPath = System.getenv('MOQUI_MATH_ENTITIES') ?:
    '../../moqui/tests/ai/moqui-framework/runtime/component/moqui-math/entity/MathEntities.xml'
File schemaFile = new File(schemaPath)
File dslFile = new File('examples/jena-knowledge-graph.groovy')

println '==================================================================='
println ' Moqui-Math: Knowledge Graph & Apache Jena Interoperability'
println ' Semantic Web (RDF / OWL / SPARQL) over Neutral Graph Metamodel'
println '==================================================================='
println "Schema : ${schemaFile.absolutePath}"
println "Model  : ${dslFile.name}"

// 1. Evaluate the external DSL Model File
MathMeta mathMeta = MathDsl.evaluate(schemaFile, dslFile)

// 2. Lower Moqui Graph to Apache Jena RDF Model
Model rdfModel = Jena.toModel(mathMeta, 'ResearchLabGraph')
println "\n1. Lowered Moqui Graph to Apache Jena RDF Model (${rdfModel.size()} Triples)"

// 3. Convert to Jena Ontology Model with OWL/RDFS Reasoning Inference
OntModel ontModel = Jena.toOntology(mathMeta, 'ResearchLabGraph')
println '2. Applied OWL Reasoning & Inference Engine'

// 4. Execute SPARQL Query 1: Direct relationships and vertex parameters
println '\n3. Executing SPARQL Query (Direct Lab Structure):'
String sparqlDirect = '''
PREFIX mg: <http://moqui.org/math/graph#>
PREFIX mp: <http://moqui.org/math/property#>
PREFIX param: <http://moqui.org/math/parameter#>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>

SELECT ?personName ?jobTitle ?email ?deptName WHERE {
    ?person mp:leads ?dept .
    ?person rdfs:label ?personName .
    ?person param:jobTitle ?jobTitle .
    ?person param:email ?email .
    ?dept rdfs:label ?deptName .
}
'''
def leaders = Jena.sparql(rdfModel, sparqlDirect)
leaders.each { row ->
    println "   * Leader: ${row.personName} | Title: ${row.jobTitle} | Email: ${row.email} | Dept: ${row.deptName}"
}

// 5. Execute SPARQL Query 2 with RDFS/OWL Transitive SubClass Inference
println '\n4. Executing SPARQL Query with OWL Reasoning (Find ALL Persons by Transitivity):'
String sparqlInferred = '''
PREFIX mg: <http://moqui.org/math/graph#>
PREFIX mp: <http://moqui.org/math/property#>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>

SELECT ?individualName ?type WHERE {
    ?individual rdf:type mg:Class_Person .
    ?individual rdfs:label ?individualName .
    ?individual rdf:type ?type .
}
'''
def allPersons = Jena.sparql(ontModel, sparqlInferred)
allPersons.each { row ->
    println "   * Inferred Person: ${row.individualName} (Direct or Transitive Type: ${row.type})"
}

// 6. Export to Standard W3C Semantic Formats
println '\n5. Exported Semantic Representation (Turtle RDF):'
println Jena.toTurtle(rdfModel)

println '==================================================================='
println ' SUCCESS: Apache Jena Semantic Interoperability Layer Verified!'
println '==================================================================='

/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

import groovy.math.dsl.MathDsl
import groovy.math.dsl.MathMeta
import groovy.math.jena.Jena
import org.apache.jena.rdf.model.InfModel
import org.apache.jena.rdf.model.Model

String schemaPath = System.getenv('MOQUI_MATH_ENTITIES') ?:
    '../../moqui/tests/ai/moqui-framework/runtime/component/moqui-math/entity/MathEntities.xml'
File schemaFile = new File(schemaPath)
File dslFile = new File('examples/product-catalog-graph.groovy')

println '==================================================================='
println ' Moqui-Math: E-Commerce Product Catalog & Pricing Rules via Apache Jena'
println ' Semantic Web (RDF / Jena Rules Reasoner / SPARQL) over Moqui Graph'
println '==================================================================='
println "Schema : ${schemaFile.absolutePath}"
println "Model  : ${dslFile.name}"

// 1. Evaluate declarative Moqui Math Graph DSL
MathMeta mathMeta = MathDsl.evaluate(schemaFile, dslFile)

// 2. Lower Moqui Graph to Apache Jena RDF Model
Model rdfModel = Jena.toModel(mathMeta, 'ProductCatalogGraph')
println "\n1. Lowered Moqui Product Graph to Apache Jena RDF Model (${rdfModel.size()} Base Triples)"

// 3. Define Business Logic Rule in Apache Jena Rule Syntax
// Business Rule: If a product is in category 'CAT_DEMO_HARDWARE' and its unit price exceeds 250.0 USD,
// the Jena Forward-Chaining Engine deduces that it is a 'HighEndProduct' marketing segment.
String businessRules = '''
[HighEndPricingRule:
    (?p http://www.w3.org/1999/02/22-rdf-syntax-ns#type http://moqui.org/math/graph#Class_Product)
    (?p http://moqui.org/math/property#hasCategory http://moqui.org/math/graph#CAT_DEMO_HARDWARE)
    (?p http://moqui.org/math/property#priceSpecification ?spec)
    (?spec http://moqui.org/math/parameter#price ?priceValue)
    greaterThan(?priceValue, 250.0)
    -> (?p http://moqui.org/math/property#marketingSegment 'HighEndProduct')
]
'''

// 4. Create Jena Inference Model with Rule Engine
InfModel infModel = Jena.toInfModel(rdfModel, businessRules)
println '2. Applied Jena Forward-Chaining Generic Rule Reasoner (Pricing & Marketing Policies)'

// 5. Query the Inferred Semantic Catalog via SPARQL 1.1
println '\n3. Executing SPARQL Query: Fetching Inferred Strategic High-End Products:'
String sparqlQuery = '''
PREFIX mg: <http://moqui.org/math/graph#>
PREFIX mp: <http://moqui.org/math/property#>
PREFIX param: <http://moqui.org/math/parameter#>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>

SELECT ?product ?label ?price ?currency ?segment WHERE {
    ?product mp:marketingSegment 'HighEndProduct' ;
             rdfs:label ?label ;
             mp:priceSpecification ?spec ;
             mp:marketingSegment ?segment .
    ?spec param:price ?price ;
          param:priceCurrency ?currency .
}
'''

def strategicProducts = Jena.sparql(infModel, sparqlQuery)
println '--- PRODOTTI STRATEGICI RILEVATI DA JENA RULE REASONER ---'
strategicProducts.each { row ->
    String prodUri = row.product
    String prodId = prodUri.contains('#') ? prodUri.substring(prodUri.lastIndexOf('#') + 1) : prodUri
    println "   * Prodotto: ID=${prodId} | Nome: ${row.label} | Prezzo: \$${row.price} ${row.currency} | Segmento: ${row.segment}"
}

assert strategicProducts.size() == 2 : "Expected 2 high-end products (DEMO_1_1 and DEMO_1_3), found ${strategicProducts.size()}"

// 6. Export RDF Graph Representation in W3C Turtle Syntax
println '\n4. Exported Semantic Representation (Turtle RDF with Inferred Segment):'
println Jena.toTurtle(rdfModel)

println '==================================================================='
println ' SUCCESS: Apache Jena E-Commerce Rule Reasoning Verified!'
println '==================================================================='

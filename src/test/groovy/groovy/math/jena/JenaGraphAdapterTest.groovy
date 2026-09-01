/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package groovy.math.jena

import org.apache.jena.ontology.OntModel
import org.apache.jena.rdf.model.Model
import org.junit.jupiter.api.Test
import groovy.math.dsl.MathDsl
import groovy.math.dsl.MathMeta
import groovy.math.moqui.MoquiSchemaInspector

import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertNotNull
import static org.junit.jupiter.api.Assertions.assertTrue

class JenaGraphAdapterTest {

    @Test
    void testMoquiGraphToRdfAndSparql() {
        String schemaPath = System.getenv('MOQUI_MATH_ENTITIES') ?: '../../moqui/tests/ai/moqui-framework/runtime/component/moqui-math/entity/MathEntities.xml'
        File schemaFile = new File(schemaPath)

        MathMeta mathMeta = MathDsl.math(MoquiSchemaInspector.inspect(schemaFile)) {
            Graph('NetworkGraph', name: 'Test Network Graph') {
                GraphVertex('V_Router', label: 'Router Node')
                GraphVertex('V_Server', label: 'Compute Server') {
                    Parameter('P_IP', parameterDefId: 'ipAddress', textValue: '192.168.1.100')
                }
                GraphEdge('E_Link', fromVertexId: 'V_Router', toVertexId: 'V_Server', label: 'connectsTo')
            }
        }

        Model rdfModel = Jena.toModel(mathMeta, 'NetworkGraph')
        assertNotNull(rdfModel)
        assertTrue(rdfModel.size() >= 3)

        String sparql = '''
            PREFIX mg: <http://moqui.org/math/graph#>
            PREFIX mp: <http://moqui.org/math/property#>
            PREFIX param: <http://moqui.org/math/parameter#>
            PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>

            SELECT ?fromLabel ?toLabel ?ip WHERE {
                ?from mp:connectsTo ?to .
                ?from rdfs:label ?fromLabel .
                ?to rdfs:label ?toLabel .
                ?to param:ipAddress ?ip .
            }
        '''
        List<Map<String, String>> results = Jena.sparql(rdfModel, sparql)
        assertEquals(1, results.size())
        assertEquals('Router Node', results[0].fromLabel)
        assertEquals('Compute Server', results[0].toLabel)
        assertEquals('192.168.1.100', results[0].ip)
    }

    @Test
    void testOwlInference() {
        String schemaPath = System.getenv('MOQUI_MATH_ENTITIES') ?: '../../moqui/tests/ai/moqui-framework/runtime/component/moqui-math/entity/MathEntities.xml'
        File schemaFile = new File(schemaPath)

        MathMeta mathMeta = MathDsl.math(MoquiSchemaInspector.inspect(schemaFile)) {
            Graph('TaxonomyGraph', name: 'Biological Classification') {
                GraphVertex('Animal', label: 'Animal')
                GraphVertex('Mammal', label: 'Mammal')
                GraphVertex('Dog', label: 'Dog')
                GraphVertex('Fido', label: 'Fido')

                GraphEdge('E1', fromVertexId: 'Mammal', toVertexId: 'Animal', label: 'subClassOf')
                GraphEdge('E2', fromVertexId: 'Dog', toVertexId: 'Mammal', label: 'subClassOf')
                GraphEdge('E3', fromVertexId: 'Fido', toVertexId: 'Dog', label: 'type')
            }
        }

        OntModel ontModel = Jena.toOntology(mathMeta, 'TaxonomyGraph')
        assertNotNull(ontModel)

        // Fido is a Dog, Dog is a Mammal, Mammal is an Animal -> Fido is an Animal!
        String sparql = '''
            PREFIX mg: <http://moqui.org/math/graph#>
            PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
            PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>

            SELECT ?type WHERE {
                mg:Fido rdf:type ?type .
            }
        '''
        List<Map<String, String>> results = Jena.sparql(ontModel, sparql)
        Set<String> types = results.collect { it.type } as Set<String>
        assertTrue(types.contains('http://moqui.org/math/graph#Dog'))
        assertTrue(types.contains('http://moqui.org/math/graph#Mammal'))
        assertTrue(types.contains('http://moqui.org/math/graph#Animal'))
    }

    @Test
    void testJenaRuleInference() {
        String schemaPath = System.getenv('MOQUI_MATH_ENTITIES') ?: '../../moqui/tests/ai/moqui-framework/runtime/component/moqui-math/entity/MathEntities.xml'
        File schemaFile = new File(schemaPath)

        MathMeta mathMeta = MathDsl.math(MoquiSchemaInspector.inspect(schemaFile)) {
            Graph('PricingGraph', name: 'Product Pricing Graph') {
                GraphVertex('Class_Product', label: 'Product')
                GraphVertex('CAT_DEMO', label: 'Demo Category')

                GraphVertex('P_100', label: 'Premium Item')
                GraphVertex('Price_100', label: 'Price 300.0') {
                    Parameter('PriceVal_1', parameterDefId: 'price', numericValue: 300.0)
                }

                GraphVertex('P_200', label: 'Standard Item')
                GraphVertex('Price_200', label: 'Price 50.0') {
                    Parameter('PriceVal_2', parameterDefId: 'price', numericValue: 50.0)
                }

                GraphEdge('T1', fromVertexId: 'P_100', toVertexId: 'Class_Product', label: 'type')
                GraphEdge('T2', fromVertexId: 'P_200', toVertexId: 'Class_Product', label: 'type')
                GraphEdge('C1', fromVertexId: 'P_100', toVertexId: 'CAT_DEMO', label: 'hasCategory')
                GraphEdge('C2', fromVertexId: 'P_200', toVertexId: 'CAT_DEMO', label: 'hasCategory')
                GraphEdge('S1', fromVertexId: 'P_100', toVertexId: 'Price_100', label: 'priceSpecification')
                GraphEdge('S2', fromVertexId: 'P_200', toVertexId: 'Price_200', label: 'priceSpecification')
            }
        }

        String rule = '''
        [HighEndRule:
            (?p http://www.w3.org/1999/02/22-rdf-syntax-ns#type http://moqui.org/math/graph#Class_Product)
            (?p http://moqui.org/math/property#hasCategory http://moqui.org/math/graph#CAT_DEMO)
            (?p http://moqui.org/math/property#priceSpecification ?spec)
            (?spec http://moqui.org/math/parameter#price ?price)
            greaterThan(?price, 250.0)
            -> (?p http://moqui.org/math/property#marketingSegment 'HighEnd')
        ]
        '''

        org.apache.jena.rdf.model.InfModel infModel = Jena.toInfModel(mathMeta, 'PricingGraph', rule)
        assertNotNull(infModel)

        String sparql = '''
            PREFIX mp: <http://moqui.org/math/property#>
            PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>

            SELECT ?label ?segment WHERE {
                ?p mp:marketingSegment ?segment .
                ?p rdfs:label ?label .
            }
        '''
        List<Map<String, String>> results = Jena.sparql(infModel, sparql)
        assertEquals(1, results.size())
        assertEquals('Premium Item', results[0].label)
        assertEquals('HighEnd', results[0].segment)
    }
}

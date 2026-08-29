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
}

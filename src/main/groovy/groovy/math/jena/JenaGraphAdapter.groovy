/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package groovy.math.jena

import groovy.transform.CompileStatic
import org.apache.jena.ontology.OntModel
import org.apache.jena.ontology.OntModelSpec
import org.apache.jena.query.QueryExecution
import org.apache.jena.query.QueryExecutionFactory
import org.apache.jena.query.QueryFactory
import org.apache.jena.query.QuerySolution
import org.apache.jena.query.ResultSet
import org.apache.jena.rdf.model.Model
import org.apache.jena.rdf.model.ModelFactory
import org.apache.jena.rdf.model.Property
import org.apache.jena.rdf.model.RDFNode
import org.apache.jena.rdf.model.Resource
import org.apache.jena.vocabulary.OWL
import org.apache.jena.vocabulary.RDF
import org.apache.jena.vocabulary.RDFS
import groovy.math.dsl.MathMeta
import groovy.math.entity.ModelValue

import java.io.StringWriter

@CompileStatic
class JenaGraphAdapter {

    public static final String DEFAULT_NS = 'http://moqui.org/math/graph#'
    public static final String DEFAULT_PROP_NS = 'http://moqui.org/math/property#'
    public static final String DEFAULT_PARAM_NS = 'http://moqui.org/math/parameter#'

    /**
     * Converts a Moqui Graph and its Vertices / Edges / Parameters into an Apache Jena RDF Model.
     */
    static Model toRdfModel(final MathMeta mathMeta, final String graphId, final String baseNamespace = DEFAULT_NS) {
        Objects.requireNonNull(mathMeta, 'MathMeta must not be null')
        Objects.requireNonNull(graphId, 'GraphId must not be null')

        Model model = ModelFactory.createDefaultModel()
        model.setNsPrefix('mg', baseNamespace)
        model.setNsPrefix('mp', DEFAULT_PROP_NS)
        model.setNsPrefix('param', DEFAULT_PARAM_NS)
        model.setNsPrefix('rdf', RDF.uri)
        model.setNsPrefix('rdfs', RDFS.uri)
        model.setNsPrefix('owl', OWL.NS)

        // Find Graph
        ModelValue graph = mathMeta.entity('Graph').findByName(graphId)
        if (graph == null) throw new IllegalArgumentException("Unknown Graph '${graphId}'")

        Resource graphResource = model.createResource(baseNamespace + graphId)
        if (graph.get('name')) graphResource.addProperty(RDFS.label, graph.get('name') as String)
        if (graph.get('description')) graphResource.addProperty(RDFS.comment, graph.get('description') as String)

        // Process Vertices
        Map<String, Resource> vertexResources = [:]
        for (ModelValue vertex : mathMeta.entity('GraphVertex')) {
            if (vertex.get('graphId') == graphId) {
                String vertexId = vertex.get('graphVertexId') as String
                String label = vertex.get('label') as String
                Resource vRes = model.createResource(baseNamespace + vertexId)
                if (label) vRes.addProperty(RDFS.label, label)
                vertexResources.put(vertexId, vRes)
            }
        }

        // Process Parameters on Vertices & Edges
        if (mathMeta.entity('Parameter') != null) {
            for (ModelValue param : mathMeta.entity('Parameter')) {
                if (param.get('graphId') == graphId) {
                    String vertexId = param.get('graphVertexId') as String
                    String defId = param.get('parameterDefId') as String ?: 'value'
                    Object val = param.get('textValue') ?: param.get('numericValue')
                    if (vertexId && vertexResources.containsKey(vertexId) && val != null) {
                        Property paramProp = model.createProperty(DEFAULT_PARAM_NS, defId)
                        vertexResources.get(vertexId).addProperty(paramProp, val.toString())
                    }
                }
            }
        }

        // Process Edges
        for (ModelValue edge : mathMeta.entity('GraphEdge')) {
            if (edge.get('graphId') == graphId) {
                String fromId = edge.get('fromVertexId') as String
                String toId = edge.get('toVertexId') as String
                String label = (edge.get('label') ?: 'connectedTo') as String

                Resource fromRes = vertexResources.get(fromId) ?: model.createResource(baseNamespace + fromId)
                Resource toRes = vertexResources.get(toId) ?: model.createResource(baseNamespace + toId)

                Property predicate = resolvePredicate(model, label)
                fromRes.addProperty(predicate, toRes)

                // Optional edge weight
                if (edge.get('weight') != null) {
                    // Reification or edge property statement
                    Property weightProp = model.createProperty(DEFAULT_PROP_NS, 'weight')
                    fromRes.addLiteral(weightProp, edge.get('weight'))
                }
            }
        }

        model
    }

    /**
     * Converts a Moqui Graph into an Apache Jena Ontology Model with OWL/RDFS reasoning inference.
     */
    static OntModel toOntModel(final MathMeta mathMeta, final String graphId,
                               final OntModelSpec spec = OntModelSpec.OWL_MEM_MICRO_RULE_INF,
                               final String baseNamespace = DEFAULT_NS) {
        Model baseModel = toRdfModel(mathMeta, graphId, baseNamespace)
        OntModel ontModel = ModelFactory.createOntologyModel(spec, baseModel)
        ontModel
    }

    /**
     * Executes a SPARQL query over an Apache Jena RDF Model and returns raw tabular results.
     */
    static List<Map<String, String>> select(final Model model, final String sparqlQuery) {
        List<Map<String, String>> results = []
        QueryExecution qexec = QueryExecutionFactory.create(QueryFactory.create(sparqlQuery), model)
        try {
            ResultSet resultSet = qexec.execSelect()
            List<String> varNames = resultSet.resultVars
            while (resultSet.hasNext()) {
                QuerySolution solution = resultSet.nextSolution()
                Map<String, String> row = [:]
                for (String var : varNames) {
                    RDFNode node = solution.get(var)
                    row.put(var, node != null ? (node.isLiteral() ? node.asLiteral().lexicalForm : node.toString()) : '')
                }
                results.add(row)
            }
        } finally {
            qexec.close()
        }
        results
    }

    /**
     * Serializes a Jena RDF model to a formatted string (TURTLE, RDF/XML, JSON-LD, N-TRIPLES).
     */
    static String serialize(final Model model, final String format = 'TURTLE') {
        StringWriter writer = new StringWriter()
        model.write(writer, format)
        writer.toString()
    }

    private static Property resolvePredicate(final Model model, final String label) {
        String trimmed = label.trim()
        if (trimmed == 'type' || trimmed == 'rdf:type' || trimmed == 'a') {
            return RDF.type
        } else if (trimmed == 'subClassOf' || trimmed == 'rdfs:subClassOf') {
            return RDFS.subClassOf
        } else if (trimmed == 'subPropertyOf' || trimmed == 'rdfs:subPropertyOf') {
            return RDFS.subPropertyOf
        } else if (trimmed == 'sameAs' || trimmed == 'owl:sameAs') {
            return OWL.sameAs
        } else if (trimmed == 'inverseOf' || trimmed == 'owl:inverseOf') {
            return OWL.inverseOf
        } else if (trimmed.startsWith('http://') || trimmed.startsWith('https://')) {
            return model.createProperty(trimmed)
        } else {
            return model.createProperty(DEFAULT_PROP_NS, trimmed)
        }
    }
}

/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package groovy.math.jena

import groovy.transform.CompileStatic
import org.apache.jena.ontology.OntModel
import org.apache.jena.ontology.OntModelSpec
import org.apache.jena.rdf.model.InfModel
import org.apache.jena.rdf.model.Model
import groovy.math.dsl.MathMeta

@CompileStatic
final class Jena {
    private Jena() { }

    static Model toModel(final MathMeta mathMeta, final String graphId,
                         final String baseNamespace = JenaGraphAdapter.DEFAULT_NS) {
        JenaGraphAdapter.toRdfModel(mathMeta, graphId, baseNamespace)
    }

    static OntModel toOntology(final MathMeta mathMeta, final String graphId,
                               final OntModelSpec spec = OntModelSpec.OWL_MEM_MICRO_RULE_INF,
                               final String baseNamespace = JenaGraphAdapter.DEFAULT_NS) {
        JenaGraphAdapter.toOntModel(mathMeta, graphId, spec, baseNamespace)
    }

    static InfModel toInfModel(final Model model, final String rulesString) {
        JenaGraphAdapter.toInfModel(model, rulesString)
    }

    static InfModel toInfModel(final MathMeta mathMeta, final String graphId, final String rulesString,
                               final String baseNamespace = JenaGraphAdapter.DEFAULT_NS) {
        JenaGraphAdapter.toInfModel(mathMeta, graphId, rulesString, baseNamespace)
    }

    static List<Map<String, String>> sparql(final Model model, final String queryString) {
        JenaGraphAdapter.select(model, queryString)
    }

    static List<Map<String, String>> sparql(final MathMeta mathMeta, final String graphId, final String queryString) {
        Model model = toModel(mathMeta, graphId)
        JenaGraphAdapter.select(model, queryString)
    }

    static String toTurtle(final Model model) {
        JenaGraphAdapter.serialize(model, 'TURTLE')
    }

    static String toJsonLd(final Model model) {
        JenaGraphAdapter.serialize(model, 'JSON-LD')
    }

    static String toRdfXml(final Model model) {
        JenaGraphAdapter.serialize(model, 'RDF/XML')
    }
}

/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.GraphContent
 */
package groovy.math.model

import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.transform.AutoClone
import java.util.Map
import java.util.List
import java.util.ArrayList

@CompileStatic
@EqualsAndHashCode(includes = ['graphContentId'])
@ToString(includePackage = false, includeNames = true)
@AutoClone
class GraphContent implements Serializable {
    private static final long serialVersionUID = 1L

    /** graphContentId */
    String graphContentId

    /** graphId */
    String graphId

    /** contentLocation */
    String contentLocation

    /** contentTypeEnumId */
    String contentTypeEnumId

    /** contentDate */
    java.sql.Timestamp contentDate

    /** description */
    String description

    /** userId */
    String userId

    Graph graph

    GraphContent() {}

    GraphContent(Map<String, Object> args) {
        if (args != null) {
            if (args.containsKey('graphContentId')) this.graphContentId = args.get('graphContentId')?.toString()
            if (args.containsKey('graphId')) this.graphId = args.get('graphId')?.toString()
            if (args.containsKey('contentLocation')) this.contentLocation = args.get('contentLocation')?.toString()
            if (args.containsKey('contentTypeEnumId')) this.contentTypeEnumId = args.get('contentTypeEnumId')?.toString()
            if (args.containsKey('contentDate')) this.contentDate = (java.sql.Timestamp) args.get('contentDate')
            if (args.containsKey('description')) this.description = args.get('description')?.toString()
            if (args.containsKey('userId')) this.userId = args.get('userId')?.toString()
        }
    }

    GraphContent graphContentId(String value) {
        this.graphContentId = value
        return this;
    }

    GraphContent graphId(String value) {
        this.graphId = value
        return this;
    }

    GraphContent contentLocation(String value) {
        this.contentLocation = value
        return this;
    }

    GraphContent contentTypeEnumId(String value) {
        this.contentTypeEnumId = value
        return this;
    }

    GraphContent contentDate(java.sql.Timestamp value) {
        this.contentDate = value
        return this;
    }

    GraphContent description(String value) {
        this.description = value
        return this;
    }

    GraphContent userId(String value) {
        this.userId = value
        return this;
    }

    GraphContent graph(Graph item) {
        this.graph = item;
        return this;
    }

    Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        if (this.graphContentId != null) map.put('graphContentId', this.graphContentId);
        if (this.graphId != null) map.put('graphId', this.graphId);
        if (this.contentLocation != null) map.put('contentLocation', this.contentLocation);
        if (this.contentTypeEnumId != null) map.put('contentTypeEnumId', this.contentTypeEnumId);
        if (this.contentDate != null) map.put('contentDate', this.contentDate);
        if (this.description != null) map.put('description', this.description);
        if (this.userId != null) map.put('userId', this.userId);
        return map;
    }
}
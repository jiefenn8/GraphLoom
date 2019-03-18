package r2graph.mapper.impl;

import org.apache.jena.ext.com.google.common.base.Preconditions;
import org.apache.jena.rdf.model.*;
import org.apache.jena.vocabulary.RDF;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import r2graph.mapper.Mapper;
import r2graph.r2rml.PredicateObjectMap;
import r2graph.r2rml.R2RML;
import r2graph.r2rml.TriplesMap;
import r2graph.util.InputDatabase;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.apache.jena.ext.com.google.common.base.Preconditions.*;

/**
 * Process R2RML map and Input Database to RDF Triples.
 */
public class MapperCom implements Mapper {
    private final static Logger LOGGER = LoggerFactory.getLogger(MapperCom.class.getName());
    private final Pattern pattern = Pattern.compile("\\{(.*?)}");

    private Model rdfGraph;
    private boolean cancelled = false;

    /**
     * Main mapping function converting RDF SQL database data to RDF triples using r2graph.r2rml.R2RML map tree.
     * <p>
     * @param input of the sql database to send query to retrieve data.
     * @param r2rmlmap to configure the mapping of data from database into RDF triples.
     */
    public Model mapToGraph(InputDatabase input, R2RML r2rmlmap) {
        checkNotNull(input);
        checkNotNull(r2rmlmap);

        rdfGraph = ModelFactory.createDefaultModel();
        r2rmlmap.listTriplesMap().forEach((key, triplesMap) -> {
            processRow(input, triplesMap);
        });

        LOGGER.info("Mapping complete");
        return rdfGraph;
    }

    /**
     * Generate Triple for subject and for its properties.
     * <p>
     * @param input database interface to query sql data from.
     * @param triplesMap configuration specifying what data to map to Triples.
     */
    private void processRow(InputDatabase input, TriplesMap triplesMap ){
        for (Map<String, String> row : input.getRows(triplesMap.getTableName())) {
            if (cancelled) break;

            String subjectURI = generateURIFromTemplate(triplesMap.getTemplate(), row);
            if (subjectURI.isEmpty()) break;

            Resource subject = rdfGraph.createResource(subjectURI).addProperty(RDF.type, triplesMap.getClassType());
            generateTriplesFromRowColumns(subject, row, triplesMap.getPredicateObjectMaps());
        }
    }

    /**
     * Generate uri for a row using provided template and data.
     * <p>
     * @param template to use to generate the uri.
     * @param row containing the row data.
     * @return String uri of subject.
     */
    private String generateURIFromTemplate(String template, Map<String, String> row){
        Matcher matcher = pattern.matcher(template);
        if(matcher.find()) {
            return template.replace(matcher.group(0),  row.get(matcher.group(1)));
        }
        return "";
    }

    /**
     * Generate RDF Triples for each Column of a single Row that is specified in each PredicateObjectMap.
     * <p>
     * @param subject of the Row (Or known as row/record id)
     * @param row containing the row data to map to Triples.
     * @param poms list to use to determine what data to map to Triples.
     */
    private void generateTriplesFromRowColumns(Resource subject, Map<String, String> row, List<PredicateObjectMap> poms) {
        poms.forEach((pom) -> {
            Property predicate = rdfGraph.createProperty(pom.getPredicate());
            RDFNode object = rdfGraph.createLiteral(row.get(pom.getColumnName()));
            subject.addProperty(predicate, object);
        });
    }
}
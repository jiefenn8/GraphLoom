/*
 *    Copyright (c) 2019 - GraphLoom contributors (github.com/jiefenn8/graphloom)
 *    This software is made available under the terms of Apache License, Version 2.0.
 */

package com.github.jiefenn8.graphloom.rdf.r2rml;

import com.github.jiefenn8.graphloom.api.*;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.jena.rdf.model.Resource;

import java.util.*;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Implementation of R2RML TriplesMap with {@link EntityMap} interface.
 * This class is an immutable class and requires the use of its
 * {@link Builder} class to populate and create an instance.
 */
public class TriplesMap implements EntityMap {

    private final String idName;
    private final LogicalTable logicalTable;
    private final SubjectMap subjectMap;
    private final Map<RelationMap, NodeMap> predicateObjectMaps;

    protected TriplesMap(Builder builder) {
        checkNotNull(builder);
        idName = builder.idName;
        logicalTable = builder.logicalTable.withParentMap(this);
        subjectMap = builder.subjectMap.withParentMap(this);
        predicateObjectMaps = ImmutableMap.copyOf(builder.predicateObjectMaps);
    }

    @Override
    public SourceMap applySource(InputSource source) {
        return logicalTable.loadInputSource(source);
    }

    @Override
    public String getIdName() {
        return idName;
    }

    @Override
    public Set<RelationMap> listRelationMaps() {
        return Collections.unmodifiableSet(predicateObjectMaps.keySet());
    }

    @Override
    public NodeMap getNodeMapWithRelation(RelationMap relationMap) {
        return predicateObjectMaps.get(relationMap);
    }

    @Override
    public boolean hasRelationNodeMaps() {
        return !predicateObjectMaps.isEmpty();
    }

    @Override
    public Resource generateEntityTerm(Record entityProps) {
        return subjectMap.generateEntityTerm(entityProps);
    }

    @Override
    public List<Resource> listEntityClasses() {
        return subjectMap.listEntityClasses();
    }

    public static class Builder {

        private final String idName;
        private final LogicalTable logicalTable;
        private final SubjectMap subjectMap;
        private final Map<RelationMap, NodeMap> predicateObjectMaps = new HashMap<>();

        /**
         * Default constructor for triples map builder.
         *
         * @param idName       the name of this triples map definition
         * @param logicalTable the logical table to set on this triples map
         * @param subjectMap   the subject map to set on this triples map
         */
        public Builder(String idName, LogicalTable logicalTable, SubjectMap subjectMap) {
            this.idName = checkNotNull(idName, "ID name must not be null.");
            this.logicalTable = checkNotNull(logicalTable, "Logical table must not be null.");
            this.subjectMap = checkNotNull(subjectMap, "Subject map must not be null.");
        }

        /**
         * Adds a predicate map and object map pair to this triples map.
         *
         * @param pom the pair to add to triples map
         */
        public Builder addPredicateObjectMap(Pair<PredicateMap, NodeMap> pom) {
            predicateObjectMaps.put(pom.getKey(), pom.getValue());
            return this;
        }

        /**
         * Returns an immutable instance of triples map containing the
         * properties given to the its builder.
         *
         * @return the triples map created with this builder parameters
         */
        public TriplesMap build() {
            return new TriplesMap(this);
        }
    }
}

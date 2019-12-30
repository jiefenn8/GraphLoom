/*
 *    Copyright (c) 2019 - GraphLoom contributors (github.com/jiefenn8/graphloom)
 *    This software is made available under the terms of Apache License, Version 2.0.
 */

package com.github.jiefenn8.graphloom.rdf.r2rml;

import com.github.jiefenn8.graphloom.api.Record;
import org.apache.jena.rdf.model.RDFNode;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * This interface defines the base methods that manages the mapping of any
 * source record to their respective rdf term through the use of a constant
 * term.
 */
public class ConstTermMap implements TermMap {

    private RDFNode constTerm;

    /**
     * Constructs a ConstTermMap with the specified constant value to use
     * as the term.
     *
     * @param n the constant value to use as term
     */
    protected ConstTermMap(RDFNode n) {
        constTerm = checkNotNull(n, "Constant term must not be null.");
    }

    @Override
    public RDFNode generateRDFTerm(Record r) {
        return constTerm;
    }
}

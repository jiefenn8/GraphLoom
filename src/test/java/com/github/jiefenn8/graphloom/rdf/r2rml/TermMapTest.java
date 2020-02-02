/*
 * Copyright (c) 2019 - GraphLoom contributors (github.com/jiefenn8/graphloom
 * This software is made available under the terms of Apache License, Version 2.0.
 */

package com.github.jiefenn8.graphloom.rdf.r2rml;

import com.github.jiefenn8.graphloom.api.Record;
import com.google.common.collect.ImmutableList;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.apache.jena.rdf.model.RDFNode;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit test interface for {@link TermMap}.
 */
@RunWith(JUnitParamsRunner.class)
public class TermMapTest {

    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock private Record mockRecord;

    @Before
    public void setUp() {
        when(mockRecord.getPropertyValue(any())).thenReturn("VALUE");
    }

    public List<TermMap> termMapParameters() {
        return ImmutableList.of(
                new ConstTermMap(mock(RDFNode.class)),
                new ColumnTermMap("COLUMN_NAME", TermMap.TermType.UNDEFINED),
                new TemplateTermMap("{COLUMN_NAME}", TermMap.TermType.UNDEFINED));
    }

    @Test
    @Parameters(method = "termMapParameters")
    public void GivenTermMap_WhenGenerateRDFTerm_ThenReturnRDFNode(TermMap termMap) {
        RDFNode result = termMap.generateRDFTerm(mockRecord);
        assertThat(result, is(notNullValue()));
    }
}

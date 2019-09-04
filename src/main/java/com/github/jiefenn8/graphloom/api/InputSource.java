/*
 *    Copyright (c) 2019 - Javen Liu (github.com/jiefenn8)
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.github.jiefenn8.graphloom.api;

import java.util.List;
import java.util.Map;

/**
 * Input Source
 * <p>
 * This interface defines the base methods to retrieve data from
 * a data source.
 */
public interface InputSource {
    /**
     * Returns a list of records for an entity containing records
     * that represent the data and its column-type.
     *
     * @param entity to get records from data-source.
     * @return the list of records in maps.
     */
    List<Map<String, String>> getEntityRecords(String entity);
}
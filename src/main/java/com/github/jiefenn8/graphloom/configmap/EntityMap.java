package com.github.jiefenn8.graphloom.configmap;

import java.util.List;

/**
 * Entity Map
 * <p>
 * This interface defines the base methods that manages the mapping
 * configurations of data to graph sharing the same entity.
 */
public interface EntityMap {

    /**
     * Returns the name of this entity source where the entity data is located.
     * The name should  be a reference to a database table or file name.
     *
     * @return the String of the entity source name
     */
    String getEntitySource();

    /**
     * Sets the entity source name. Depending on the type of source, the name
     * should be a reference to a database table name or a file name storing
     * the data of this entity.
     *
     * @param source the String of the source name to use as reference
     */
    void setEntitySource(String source);

    /**
     * Returns the template pattern to be used to generate URIs of this entity.
     *
     * @return the String containing template pattern
     */
    String getTemplate();

    /**
     * Sets the template to use as a pattern to generate URIs of this entity.
     *
     * @param template the String of the template to use as a pattern
     */
    void setTemplate(String template);

    /**
     * Returns the class type that is associated with the entity, or
     * {@code null} if the entity has no class type.
     *
     * @return the class type String associated with the entity, or
     * {@code null} if this entity is has no class type
     */
    String getClassType();

    /**
     * Sets the class type to associate the entity with. This method is optional
     * if the entity does not have class type that it can be associated with.
     *
     * @param classType the String of the class type to associate this entity with
     */
    void setClassType(String classType);

    /**
     * Appends a {@code PredicateMap} to the list that will be used to configure
     * the generation rules of specified predicate and object in each pair for
     * this entity.
     *
     * @param predicateMap the {@code PredicateMap} to be appended to the map
     */
    void addPredicateMap(PredicateMap predicateMap);

    /**
     * Returns list of {@code PredicateMap} that contains the configuration rules
     * for the generation of each predicate and object for this entity.
     *
     * @return list of {@code PredicateMap} containing predicate and any
     * {@code ObjectMap} configs.
     */
    List<PredicateMap> listPredicateMaps();
}
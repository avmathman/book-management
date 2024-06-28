package com.book.management.application.mapper;

import java.util.List;

/**
 * Common mapper for converting model object(s) to entity object(s) and vice versa.
 */
public interface ModelMapper<Entity, Model> {

    /**
     * Converts model object to entity object.
     *
     * @param model - The model object.
     * @return The entity object.
     */
    Entity modelToEntity(Model model);

    /**
     * Converts entity object to model object.
     *
     * @param entity - The entity object.
     * @return The model object.
     */
    Model entityToModel(Entity entity);

    /**
     * Converts a list of entities objects to a list of model objects.
     *
     * @param entities - The list of entity objects.
     * @return The list of model objects.
     */
    List<Model> entitiesToModels(List<Entity> entities);

    /**
     * Converts a list of models objects to a list of entity objects.
     *
     * @param models - The list of models objects.
     * @return The list of entity objects.
     */
    List<Entity> modelsToEntities(List<Model> models);
}

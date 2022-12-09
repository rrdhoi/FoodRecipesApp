package com.jagoteori.foodrecipesapp.data.model

import com.jagoteori.foodrecipesapp.domain.entity.RecipeEntity

object DataMapper {
    fun recipeModelToEntity(model: RecipeModel): RecipeEntity = RecipeEntity(
        id = model.id,
        title = model.title,
        description = model.description,
        publisher = model.publisher,
        recipePicture = model.recipePicture,
    )

    fun recipeEntityToModel(entity: RecipeEntity) = RecipeModel(
        id = entity.id,
        title = entity.title,
        description = entity.description,
        publisher = entity.publisher,
        recipePicture = entity.recipePicture,
    )
}
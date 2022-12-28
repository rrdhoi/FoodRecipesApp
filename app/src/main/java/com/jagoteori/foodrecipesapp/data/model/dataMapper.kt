package com.jagoteori.foodrecipesapp.data.model

import com.jagoteori.foodrecipesapp.domain.entity.RecipeEntity
import com.jagoteori.foodrecipesapp.domain.entity.StepCookEntity
import com.jagoteori.foodrecipesapp.domain.entity.ingredient.IngredientEntity

object DataMapper {
    fun recipeModelToEntity(model: RecipeModel): RecipeEntity = RecipeEntity(
        id = model.id,
        title = model.title,
        description = model.description,
        publisherId = model.publisherId,
        publisher = model.publisher,
        recipePicture = model.recipePicture,
        listIngredients = model.listIngredients.map { ingredientModelToEntity(it) }.toList(),
        listStepCooking = model.listStepCooking.map { stepCookModelToEntity(it) }.toList(),
    )

    fun recipeEntityToModel(entity: RecipeEntity) = RecipeModel(
        id = entity.id,
        title = entity.title,
        description = entity.description,
        publisherId = entity.publisherId,
        publisher = entity.publisher,
        recipePicture = entity.recipePicture,
        listIngredients = entity.listIngredients.map { ingredientEntityToModel(it) }.toList(),
        listStepCooking = entity.listStepCooking.map { stepCookEntityToModel(it) }.toList(),
    )

    private fun ingredientEntityToModel(entity: IngredientEntity) = IngredientModel(
        ingredient = entity.ingredient,
        quantity = entity.quantity,
        typeQuantity = entity.typeQuantity,
    )

    private fun ingredientModelToEntity(model: IngredientModel) = IngredientEntity(
        ingredient = model.ingredient,
        quantity = model.quantity,
        typeQuantity = model.typeQuantity,
    )

    private fun stepCookEntityToModel(entity: StepCookEntity) = StepCookModel(
        stepNumber = entity.stepNumber,
        stepDescription = entity.stepDescription,
        stepImages = entity.stepImages,
    )

    private fun stepCookModelToEntity(model: StepCookModel) = StepCookEntity(
        stepNumber = model.stepNumber,
        stepDescription = model.stepDescription,
        stepImages = model.stepImages,
    )
}
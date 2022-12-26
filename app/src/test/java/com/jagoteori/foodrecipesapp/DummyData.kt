package com.jagoteori.foodrecipesapp

import com.jagoteori.foodrecipesapp.data.model.RecipeModel
import com.jagoteori.foodrecipesapp.domain.entity.RecipeEntity

object DummyData {

    const val errorMessage = "Error Data"
    const val notFoundRecipeMessage = "Recipe not found"
    const val successMessage = "Add data Success"

    val listRecipeModel: List<RecipeModel> = listOf(
        RecipeModel(
            id = "1",
            description = "testDescription",
            title = "testTitle",
            publisher = "testPublisher",
            recipePicture = "testRecipePicture",
        )
    )

    val listRecipeEntity: List<RecipeEntity> = listOf(
        RecipeEntity(
            id = "1",
            description = "testDescription",
            title = "testTitle",
            publisher = "testPublisher",
            recipePicture = "testRecipePicture",
        )
    )

    val recipeEntity = RecipeEntity(
        id = "1",
        description = "testDescription",
        title = "testTitle",
        publisher = "testPublisher",
        recipePicture = "testRecipePicture",
    )

    val recipeModel = RecipeModel(
        id = "1",
        description = "testDescription",
        title = "testTitle",
        publisher = "testPublisher",
        recipePicture = "testRecipePicture",
    )
}
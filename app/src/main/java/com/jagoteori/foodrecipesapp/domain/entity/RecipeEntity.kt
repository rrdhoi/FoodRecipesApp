package com.jagoteori.foodrecipesapp.domain.entity

import com.jagoteori.foodrecipesapp.data.model.RecipeModel
import com.jagoteori.foodrecipesapp.domain.entity.ingredient.IngredientEntity

data class RecipeEntity(
    val id: String?,
    val title: String?,
    val description: String?,
    val publisherId: String?,
    val publisher: String?,
    val recipePicture: String?,
    val listIngredients: List<IngredientEntity>,
    val listStepCooking: List<StepCookEntity>
)
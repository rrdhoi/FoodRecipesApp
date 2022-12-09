package com.jagoteori.foodrecipesapp.domain.entity

import com.jagoteori.foodrecipesapp.data.model.RecipeModel

data class RecipeEntity(
    val id: String?,
    val title: String?,
    val description: String?,
    val publisher: String?,
    val recipePicture: String?,
)
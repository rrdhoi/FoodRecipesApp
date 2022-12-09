package com.jagoteori.foodrecipesapp.domain.usecase

import com.jagoteori.foodrecipesapp.data.Resource
import com.jagoteori.foodrecipesapp.data.model.RecipeModel
import com.jagoteori.foodrecipesapp.domain.entity.RecipeEntity
import kotlinx.coroutines.flow.Flow

interface RecipeUseCase {
    suspend fun getAllRecipes(): Flow<Resource<List<RecipeEntity>>>
    suspend fun getRecipeById(recipeId: String): Resource<RecipeEntity>
    suspend fun addRecipe(recipe: RecipeEntity): Resource<String>
}
package com.jagoteori.foodrecipesapp.data.source.remote

import com.jagoteori.foodrecipesapp.data.model.RecipeModel
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {
    suspend fun getAllRecipes(): Flow<ApiResponse<List<RecipeModel>>>
    suspend fun getRecipeById(recipeId: String): ApiResponse<RecipeModel>
    suspend fun addRecipe(recipe: RecipeModel): ApiResponse<String>
}
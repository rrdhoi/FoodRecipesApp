package com.jagoteori.foodrecipesapp.domain.usecase

import com.jagoteori.foodrecipesapp.data.Resource
import com.jagoteori.foodrecipesapp.domain.entity.RecipeEntity
import com.jagoteori.foodrecipesapp.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow

class RecipeInteractor(private val recipeRepository: RecipeRepository) : RecipeUseCase {
    override suspend fun getAllRecipes(): Flow<Resource<List<RecipeEntity>>> =
        recipeRepository.getAllRecipes()

    override suspend fun getRecipeById(recipeId: String): Resource<RecipeEntity> =
        recipeRepository.getRecipeById(recipeId)

    override suspend fun addRecipe(recipe: RecipeEntity): Resource<String> =
        recipeRepository.addRecipe(recipe)
}
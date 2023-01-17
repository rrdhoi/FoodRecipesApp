package com.jagoteori.foodrecipesapp.domain.usecase

import com.jagoteori.foodrecipesapp.data.Resource
import com.jagoteori.foodrecipesapp.data.model.RecipeModel
import com.jagoteori.foodrecipesapp.domain.entity.CommentEntity
import com.jagoteori.foodrecipesapp.domain.entity.RecipeEntity
import com.jagoteori.foodrecipesapp.domain.entity.UserEntity
import kotlinx.coroutines.flow.Flow

interface RecipeUseCase {
    suspend fun getAllRecipes(): Flow<Resource<List<RecipeEntity>>>
    suspend fun getRecipeById(recipeId: String): Resource<RecipeEntity>
    suspend fun addRecipe(recipe: RecipeEntity): Resource<String>
    suspend fun getCategoryRecipe(category: String) : Resource<List<RecipeEntity>>
    suspend fun getMyRecipes(): Resource<List<RecipeEntity>>
    suspend fun addComment(recipeId: String, commentEntity: CommentEntity): Resource<String>
    suspend fun userSignIn(email: String, password: String): Flow<Resource<String>>
    suspend fun userSignUp(user: UserEntity, password: String): Flow<Resource<String>>
    suspend fun getMyUser(): Resource<UserEntity>
    suspend fun updateUser(user: UserEntity) : Resource<String>
}
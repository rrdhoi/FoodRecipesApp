package com.jagoteori.foodrecipesapp.data.source.remote

import com.jagoteori.foodrecipesapp.data.model.CommentModel
import com.jagoteori.foodrecipesapp.data.model.RecipeModel
import com.jagoteori.foodrecipesapp.data.model.UserModel
import com.jagoteori.foodrecipesapp.domain.entity.UserEntity
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {
    suspend fun getAllRecipes(): Flow<ApiResponse<List<RecipeModel>>>
    suspend fun getRecipeById(recipeId: String): ApiResponse<RecipeModel>
    suspend fun getCategoryRecipe(category: String) : ApiResponse<List<RecipeModel>>
    suspend fun getMyUser(): ApiResponse<UserModel>
    suspend fun getComments(recipeId: String): List<CommentModel>
    suspend fun getMyRecipes(): ApiResponse<List<RecipeModel>>
    suspend fun addRecipe(recipe: RecipeModel): ApiResponse<String>
    suspend fun addComment(recipeId: String, commentModel: CommentModel): ApiResponse<String>
    suspend fun userSignIn(email: String, password: String): ApiResponse<String>
    suspend fun userSignUp(user: UserModel, password: String): ApiResponse<String>
    suspend fun updateUser(user: UserModel) : ApiResponse<String>
}
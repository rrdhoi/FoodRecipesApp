package com.jagoteori.foodrecipesapp.domain.usecase

import com.jagoteori.foodrecipesapp.data.Resource
import com.jagoteori.foodrecipesapp.domain.entity.CommentEntity
import com.jagoteori.foodrecipesapp.domain.entity.RecipeEntity
import com.jagoteori.foodrecipesapp.domain.entity.UserEntity
import com.jagoteori.foodrecipesapp.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow

class RecipeInteractor(private val recipeRepository: RecipeRepository) : RecipeUseCase {
    override suspend fun getAllRecipes(): Flow<Resource<List<RecipeEntity>>> =
        recipeRepository.getAllRecipes()

    override suspend fun getRecipeById(recipeId: String): Resource<RecipeEntity> =
        recipeRepository.getRecipeById(recipeId)

    override suspend fun getMyRecipes(): Flow<Resource<List<RecipeEntity>>> = recipeRepository.getMyRecipes()

    override suspend fun addRecipe(recipe: RecipeEntity): Resource<String> =
        recipeRepository.addRecipe(recipe)

    override suspend fun getCategoryRecipe(category: String): Resource<List<RecipeEntity>> = recipeRepository.getCategoryRecipe(category)

    override suspend fun addComment(
        recipeId: String,
        commentEntity: CommentEntity
    ): Resource<String> = recipeRepository.addComment(recipeId, commentEntity)

    override suspend fun userSignIn(email: String, password: String): Flow<Resource<Boolean>> =
        recipeRepository.userSignIn(email, password)

    override suspend fun userSignUp(user: UserEntity, password: String): Flow<Resource<Boolean>> =
        recipeRepository.userSignUp(user, password)

    override suspend fun getMyUser(): Resource<UserEntity> = recipeRepository.getMyUser()

    override suspend fun updateUser(user: UserEntity): Resource<String> = recipeRepository.updateUser(user)
}
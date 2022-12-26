package com.jagoteori.foodrecipesapp.data

import com.jagoteori.foodrecipesapp.data.model.DataMapper
import com.jagoteori.foodrecipesapp.data.source.remote.ApiResponse
import com.jagoteori.foodrecipesapp.data.source.remote.RemoteDataSource
import com.jagoteori.foodrecipesapp.domain.entity.RecipeEntity
import com.jagoteori.foodrecipesapp.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RecipeRepositoryImpl(private val remoteDataSource: RemoteDataSource) : RecipeRepository {

    override suspend fun getAllRecipes(): Flow<Resource<List<RecipeEntity>>> = flow {
        emit(Resource.Loading())
        val response = remoteDataSource.getAllRecipes()
        response.collect { apiResponse ->
            when (apiResponse) {
                is ApiResponse.Success -> {
                    val data = apiResponse.data
                    val recipeToEntity = data.map { DataMapper.recipeModelToEntity(it) }.toList()
                    emit(Resource.Success(recipeToEntity))
                }
                is ApiResponse.Empty -> {
                    emit(Resource.Success(emptyList()))
                }
                is ApiResponse.Error -> {
                    emit(Resource.Error(apiResponse.errorMessage))
                }
            }
        }
    }

    override suspend fun getRecipeById(recipeId: String): Resource<RecipeEntity> =
        when (val response = remoteDataSource.getRecipeById(recipeId)) {
            is ApiResponse.Success -> Resource.Success(DataMapper.recipeModelToEntity(response.data))
            is ApiResponse.Empty -> Resource.Error("Recipe not found")
            is ApiResponse.Error -> Resource.Error(response.errorMessage)
        }

    override suspend fun addRecipe(recipe: RecipeEntity): Resource<String> =
        when (val response = remoteDataSource.addRecipe(DataMapper.recipeEntityToModel(recipe))) {
            is ApiResponse.Success -> Resource.Success(response.data)
            is ApiResponse.Error -> Resource.Error(response.errorMessage)
            else -> Resource.Error("Unknown Error")
        }
}
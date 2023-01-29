package com.jagoteori.foodrecipesapp.data

import com.jagoteori.foodrecipesapp.app.utils.DataMapper
import com.jagoteori.foodrecipesapp.data.source.remote.ApiResponse
import com.jagoteori.foodrecipesapp.data.source.remote.RemoteDataSource
import com.jagoteori.foodrecipesapp.domain.entity.CommentEntity
import com.jagoteori.foodrecipesapp.domain.entity.RecipeEntity
import com.jagoteori.foodrecipesapp.domain.entity.UserEntity
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
                    val recipeToEntity = data.map {
                        it.listComments = remoteDataSource.getComments(it.id!!)
                        DataMapper.recipeModelToEntity(it)
                    }.toList()
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

    override suspend fun getMyRecipes(): Flow<Resource<List<RecipeEntity>>> = flow {
        emit(Resource.Loading())
        emit(
            when (val response = remoteDataSource.getMyRecipes()) {
                is ApiResponse.Success -> {
                    val recipeToEntity = response.data.map {
                        it.listComments = remoteDataSource.getComments(it.id!!)
                        DataMapper.recipeModelToEntity(it)
                    }.toList()
                    Resource.Success(recipeToEntity)
                }
                is ApiResponse.Error -> Resource.Error(response.errorMessage)
                is ApiResponse.Empty -> Resource.Error("Recipes is Empty")
            }
        )
    }

    override suspend fun getRecipeById(recipeId: String): Resource<RecipeEntity> =
        when (val response = remoteDataSource.getRecipeById(recipeId)) {
            is ApiResponse.Success -> Resource.Success(DataMapper.recipeModelToEntity(response.data))
            is ApiResponse.Empty -> Resource.Error("Recipe not found")
            is ApiResponse.Error -> Resource.Error(response.errorMessage)
        }

    override suspend fun getCategoryRecipe(category: String): Resource<List<RecipeEntity>> =
        when (val response = remoteDataSource.getCategoryRecipe(category)) {
            is ApiResponse.Success -> {
                val recipeToEntity = response.data.map {
                    DataMapper.recipeModelToEntity(it)
                }.toList()
                Resource.Success(recipeToEntity)
            }
            is ApiResponse.Error -> Resource.Error(response.errorMessage)
            is ApiResponse.Empty -> Resource.Error("Recipes is Empty")
        }

    override suspend fun addRecipe(recipe: RecipeEntity): Resource<String> =
        when (val response = remoteDataSource.addRecipe(DataMapper.recipeEntityToModel(recipe))) {
            is ApiResponse.Success -> Resource.Success(response.data)
            is ApiResponse.Error -> Resource.Error(response.errorMessage)
            else -> Resource.Error("Unknown Error")
        }

    override suspend fun addComment(
        recipeId: String,
        commentEntity: CommentEntity
    ): Resource<String> =
        when (val response =
            remoteDataSource.addComment(recipeId, DataMapper.commentEntityToModel(commentEntity))) {
            is ApiResponse.Success -> Resource.Success(response.data)
            is ApiResponse.Error -> Resource.Error(response.errorMessage)
            else -> Resource.Error("Unknown Error")
        }

    override suspend fun userSignIn(email: String, password: String): Flow<Resource<String>> =
        flow {
            emit(Resource.Loading())
            emit(
                when (val response = remoteDataSource.userSignIn(email, password)) {
                    is ApiResponse.Success -> Resource.Success(response.data)
                    is ApiResponse.Error -> Resource.Error(response.errorMessage)
                    else -> Resource.Error("Unknown Error")
                }
            )
        }


    override suspend fun userSignUp(user: UserEntity, password: String): Flow<Resource<String>> =
        flow {
            emit(Resource.Loading())
            when (val response =
                remoteDataSource.userSignUp(DataMapper.userEntityToModel(user), password)) {
                is ApiResponse.Success -> emit(Resource.Success(response.data))
                is ApiResponse.Error -> emit(Resource.Error(response.errorMessage))
                else -> emit(Resource.Error("Unknown Error"))
            }
        }

    override suspend fun getMyUser(): Resource<UserEntity> = when (val response =
        remoteDataSource.getMyUser()) {
        is ApiResponse.Success -> Resource.Success(DataMapper.userModelToEntity(response.data))
        is ApiResponse.Error -> Resource.Error(response.errorMessage)
        else -> Resource.Error("Unknown Error")
    }

    override suspend fun updateUser(user: UserEntity): Resource<String> = when (val response =
        remoteDataSource.updateUser(DataMapper.userEntityToModel(user))) {
        is ApiResponse.Success -> Resource.Success(response.data)
        is ApiResponse.Error -> Resource.Error(response.errorMessage)
        else -> Resource.Error("Unknown Error")
    }

}
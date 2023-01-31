package com.jagoteori.foodrecipesapp.data.source.remote

import com.google.firebase.firestore.QuerySnapshot
import com.jagoteori.foodrecipesapp.data.model.CommentModel
import com.jagoteori.foodrecipesapp.data.model.RecipeModel
import com.jagoteori.foodrecipesapp.data.model.UserModel
import com.jagoteori.foodrecipesapp.data.source.remote.firestore.FirestoreQuery
import com.jagoteori.foodrecipesapp.data.source.remote.firestore.ResponseCallBack
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.withContext

class RemoteDataSourceImpl(private val firestore: FirestoreQuery) : RemoteDataSource {
    override suspend fun getAllRecipes(): Flow<ApiResponse<List<RecipeModel>>> = callbackFlow {
        try {
            val subscription =
                firestore.getAllRecipe(
                    object : ResponseCallBack {
                        override fun onCallBackSuccess(response: QuerySnapshot) {
                            val listRecipe = response.toObjects(RecipeModel::class.java)
                            trySend(ApiResponse.Success(listRecipe))
                        }

                        override fun onCallBackEmpty() {
                            trySend(ApiResponse.Empty)
                        }

                        override fun onCallBackError(response: String) {
                            trySend(ApiResponse.Error(response))
                        }
                    }
                )

            awaitClose {
                subscription.remove()
                channel.close()
            }
        } catch (e: Exception) {
            trySend(ApiResponse.Error(e.message.toString()))
        }
    }

    override suspend fun getMyRecipes(): ApiResponse<List<RecipeModel>> =
        withContext(Dispatchers.IO) {
            safeCall {
                val response = firestore.getMyRecipes().await()
                if (response != null && !response.isEmpty) {
                    val myListRecipes = response.toObjects(RecipeModel::class.java)
                    if (myListRecipes.isEmpty()) ApiResponse.Empty
                    else ApiResponse.Success(myListRecipes)
                } else ApiResponse.Empty
            }
        }

    override suspend fun getComments(recipeId: String): List<CommentModel> {
        val response = firestore.getComments(recipeId).await()
        val comments = response?.toObjects(CommentModel::class.java)

        return if (comments.isNullOrEmpty()) {
            mutableListOf()
        } else {
            comments
        }
    }

    override suspend fun getRecipeById(recipeId: String): ApiResponse<RecipeModel> =
        withContext(Dispatchers.IO) {
            safeCall {
                val response = firestore.getRecipeById(recipeId).await()
                val recipe = response?.toObject(RecipeModel::class.java)
                if (recipe != null) {
                    ApiResponse.Success(recipe)
                } else {
                    ApiResponse.Empty
                }
            }
        }

    override suspend fun getCategoryRecipe(category: String): ApiResponse<List<RecipeModel>> {
        val response = firestore.getCategoryRecipe(category).await()
        val listRecipe = response?.toObjects(RecipeModel::class.java)

        return if (listRecipe.isNullOrEmpty()) {
            ApiResponse.Empty
        } else {
            ApiResponse.Success(listRecipe)
        }
    }

    override suspend fun addRecipe(recipe: RecipeModel): ApiResponse<String> =
        withContext(Dispatchers.IO) {
            safeCall {
                firestore.addRecipe(recipe).await()
                ApiResponse.Success("Add data Success")
            }
        }

    override suspend fun addComment(
        recipeId: String,
        commentModel: CommentModel
    ): ApiResponse<String> = withContext(Dispatchers.IO) {
        safeCall {
            firestore.addComment(recipeId, commentModel).await()
            ApiResponse.Success("Add Comment Success")
        }
    }

    override suspend fun userSignIn(email: String, password: String): ApiResponse<Boolean> =
        withContext(Dispatchers.IO) {
            safeCall {
                firestore.userSignIn(email, password).await()
                ApiResponse.Success(true)
            }
        }

    override suspend fun userSignUp(user: UserModel, password: String): ApiResponse<Boolean> =
        withContext(Dispatchers.IO) {
            safeCall {
                firestore.userSignUp(user, password).await()
                ApiResponse.Success(true)
            }
        }

    override suspend fun getMyUser(): ApiResponse<UserModel> = withContext(Dispatchers.IO) {
        safeCall {
            val response = firestore.getMyUser().await()
            val user = response?.toObject(UserModel::class.java)
            if (user != null) ApiResponse.Success(user)
            else ApiResponse.Empty
        }
    }

    override suspend fun updateUser(user: UserModel): ApiResponse<String> =
        withContext(Dispatchers.IO) {
            safeCall {
                firestore.updateUser(user).await()
                ApiResponse.Success("Update User Successfully")
            }
        }

}
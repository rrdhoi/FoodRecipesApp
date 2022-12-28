package com.jagoteori.foodrecipesapp.data.source.remote

import com.google.firebase.firestore.QuerySnapshot
import com.jagoteori.foodrecipesapp.data.model.RecipeModel
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

    override suspend fun addRecipe(recipe: RecipeModel): ApiResponse<String> =
        withContext(Dispatchers.IO) {
            safeCall {
                firestore.addRecipe(recipe).await()
                ApiResponse.Success("Add data Success")
            }
        }
}
package com.jagoteori.foodrecipesapp.data.source.remote

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.jagoteori.foodrecipesapp.data.model.RecipeModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.withContext

class RemoteDataSourceImpl(private val firestore: FirebaseFirestore) : RemoteDataSource {
    override suspend fun getAllRecipes(): Flow<ApiResponse<List<RecipeModel>>> = callbackFlow {
        try {
            val subscription =
                firestore.collection("recipes").addSnapshotListener { snapshot, error ->
                    when {
                        error != null -> {
                            trySend(ApiResponse.Error(error.message.toString()))
                        }
                        snapshot != null -> {
                            val listRecipe = snapshot.toObjects(RecipeModel::class.java)
                            trySend(ApiResponse.Success(listRecipe))
                        }
                        else -> {
                            trySend(ApiResponse.Empty)
                        }
                    }
                }
            awaitClose { subscription.remove() }
        } catch (e: Exception) {
            trySend(ApiResponse.Error(e.message.toString()))
        }
    }

    override suspend fun getRecipeById(recipeId: String): ApiResponse<RecipeModel> =
        withContext(Dispatchers.IO) {
            safeCall {
                val snapshot = firestore.collection("recipes").document(recipeId).get().await()
                val recipe = snapshot?.toObject(RecipeModel::class.java)
                if (recipe == null) {
                    ApiResponse.Empty
                } else {
                    ApiResponse.Success(recipe)
                }
            }
        }

    /*override suspend fun getRecipeById(recipeId: String): ApiResponse<RecipeModel> = withContext(Dispatchers.IO) {
        try {
            val recipe = firestore.collection("recipes").document(recipeId).get()
            if (recipe.result != null) {
                val data = recipe.result!!.toObject(RecipeModel::class.java)
                if (data != null) {
                    emit(ApiResponse.Success(data))
                } else {
                    emit(ApiResponse.Empty)
                }
            } else {
                emit(ApiResponse.Empty)
            }
        } catch (e: Exception) {
            emit(ApiResponse.Error(e.toString()))
        }
    }*/

    override suspend fun addRecipe(recipe: RecipeModel): ApiResponse<String> {
        return safeCall {
            withContext(Dispatchers.IO) {
                Log.d("RemoteDataSource :::", "Call")
                val createDocument = firestore.collection("recipes").document()
                firestore.collection("recipes").document(createDocument.id)
                    .set(recipe.copy(id = createDocument.id)).await()
                ApiResponse.Success("Add data Success")
            }
        }
    }
}
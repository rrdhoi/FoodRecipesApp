package com.jagoteori.foodrecipesapp.data.source.remote.firestore

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.QuerySnapshot
import com.jagoteori.foodrecipesapp.data.model.CommentModel
import com.jagoteori.foodrecipesapp.data.model.RecipeModel
import com.jagoteori.foodrecipesapp.data.model.UserModel

interface IFirestoreQuery {
    fun getAllRecipe(callBack: ResponseCallBack): ListenerRegistration
    fun getCategoryRecipe(category: String): Task<QuerySnapshot>
    fun getRecipeById(recipeId: String): Task<DocumentSnapshot>
    fun getMyRecipes(): Task<QuerySnapshot>
    fun getMyUser(): Task<DocumentSnapshot>
    suspend fun addRecipe(recipe: RecipeModel): Task<Void>
    suspend fun addComment(recipeId: String, commentModel: CommentModel): Task<Void>
    fun getComments(recipeId: String): Task<QuerySnapshot>
    fun userSignUp(userModel: UserModel, password: String): Task<AuthResult>
    fun userSignIn(email: String, password: String): Task<AuthResult>
    suspend fun updateUser(user: UserModel): Task<Void>
}
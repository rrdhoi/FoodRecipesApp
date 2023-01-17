package com.jagoteori.foodrecipesapp.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.jagoteori.foodrecipesapp.data.Resource
import com.jagoteori.foodrecipesapp.domain.entity.RecipeEntity
import com.jagoteori.foodrecipesapp.domain.usecase.RecipeUseCase

class HomeViewModel(private val recipeUseCase: RecipeUseCase) : ViewModel() {
    val allRecipes: LiveData<Resource<List<RecipeEntity>>> = liveData {
        recipeUseCase.getAllRecipes().collect {
            emit(it)
        }
    }

    /* fun sendNotification(notification: PushNotification) =
         viewModelScope.launch(Dispatchers.IO) {
             try {
                 val response = RetrofitInstance.api.postNotification(notification)
                 if (response.isSuccessful) {
                     Log.d("HomeViewModel", "Response Success")
                 } else {
                     Log.e("HomeViewModel", response.errorBody().toString())
                 }
             } catch (e: Exception) {
                 Log.e("HomeViewModel", e.toString())
             }
         }*/
}
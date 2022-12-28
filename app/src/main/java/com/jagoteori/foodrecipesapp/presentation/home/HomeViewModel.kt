package com.jagoteori.foodrecipesapp.presentation.home

import android.util.Log
import androidx.lifecycle.*
import com.jagoteori.foodrecipesapp.app.notification.PushNotification
import com.jagoteori.foodrecipesapp.app.notification.RetrofitInstance
import com.jagoteori.foodrecipesapp.data.Resource
import com.jagoteori.foodrecipesapp.domain.entity.RecipeEntity
import com.jagoteori.foodrecipesapp.domain.usecase.RecipeUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(private val recipeUseCase: RecipeUseCase) : ViewModel() {
    private val _getRecipe = MutableLiveData<Resource<RecipeEntity>>()
    val getRecipe: LiveData<Resource<RecipeEntity>> get() = _getRecipe

    fun getRecipeById(recipeId: String) {
        viewModelScope.launch(Dispatchers.Main) {
            _getRecipe.postValue(recipeUseCase.getRecipeById(recipeId))
        }
    }

    val allRecipes: LiveData<Resource<List<RecipeEntity>>> = liveData {
        recipeUseCase.getAllRecipes().collect {
            emit(it)
        }
    }

    fun sendNotification(notification: PushNotification) =
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
        }
}
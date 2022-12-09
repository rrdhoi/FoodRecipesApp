package com.jagoteori.foodrecipesapp.presentation.home

import android.util.Log
import androidx.lifecycle.*
import com.jagoteori.foodrecipesapp.data.Resource
import com.jagoteori.foodrecipesapp.domain.entity.RecipeEntity
import com.jagoteori.foodrecipesapp.domain.usecase.RecipeUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(private val recipeUseCase: RecipeUseCase) : ViewModel() {
    private val _recipeId = MutableLiveData<Resource<RecipeEntity>>()
    val recipeId: LiveData<Resource<RecipeEntity>> get() = _recipeId

    fun getRecipeById(recipeId: String) {
        viewModelScope.launch(Dispatchers.Main) {
            _recipeId.postValue(recipeUseCase.getRecipeById(recipeId))
        }
    }

    val allRecipes: LiveData<Resource<List<RecipeEntity>>> = liveData {
        recipeUseCase.getAllRecipes().collect {
            emit(it)
        }
    }

    fun addRecipe(recipeEntity: RecipeEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            recipeUseCase.addRecipe(recipeEntity)
        }
    }
}
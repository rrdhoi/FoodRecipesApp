package com.jagoteori.foodrecipesapp.presentation.add_recipe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jagoteori.foodrecipesapp.data.Resource
import com.jagoteori.foodrecipesapp.domain.entity.RecipeEntity
import com.jagoteori.foodrecipesapp.domain.usecase.RecipeUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddRecipeViewModel(private val useCase: RecipeUseCase) : ViewModel() {
    private val _addRecipe = MutableLiveData<Resource<String>>()
    val addRecipe: LiveData<Resource<String>> get() = _addRecipe

    fun addRecipe(recipeEntity: RecipeEntity) = viewModelScope.launch(Dispatchers.Main) {
        _addRecipe.postValue(useCase.addRecipe(recipeEntity))
    }
}
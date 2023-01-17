package com.jagoteori.foodrecipesapp.presentation.detail_recipe

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jagoteori.foodrecipesapp.data.Resource
import com.jagoteori.foodrecipesapp.domain.entity.RecipeEntity
import com.jagoteori.foodrecipesapp.domain.usecase.RecipeUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailRecipeViewModel(private val useCase: RecipeUseCase) : ViewModel() {
    private var _getRecipeId = MutableLiveData<Resource<RecipeEntity>>()
    val recipe get() = _getRecipeId

    fun getRecipeById(id: String) = viewModelScope.launch(Dispatchers.IO) {
        _getRecipeId.value = useCase.getRecipeById(id)
    }
}
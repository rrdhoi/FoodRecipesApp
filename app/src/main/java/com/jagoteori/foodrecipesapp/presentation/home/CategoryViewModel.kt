package com.jagoteori.foodrecipesapp.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jagoteori.foodrecipesapp.data.Resource
import com.jagoteori.foodrecipesapp.domain.entity.RecipeEntity
import com.jagoteori.foodrecipesapp.domain.usecase.RecipeUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CategoryViewModel(val useCase: RecipeUseCase) : ViewModel() {
    private var _getRecipeCategory = MutableLiveData<Resource<List<RecipeEntity>>>()
    val recipes : LiveData<Resource<List<RecipeEntity>>> get() = _getRecipeCategory

    fun getRecipeCategory(category: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _getRecipeCategory.value = useCase.getCategoryRecipe(category)
        }
    }
}
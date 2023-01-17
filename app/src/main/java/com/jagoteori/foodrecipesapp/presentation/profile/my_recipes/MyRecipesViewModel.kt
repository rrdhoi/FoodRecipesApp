package com.jagoteori.foodrecipesapp.presentation.profile.my_recipes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jagoteori.foodrecipesapp.data.Resource
import com.jagoteori.foodrecipesapp.domain.entity.RecipeEntity
import com.jagoteori.foodrecipesapp.domain.entity.UserEntity
import com.jagoteori.foodrecipesapp.domain.usecase.RecipeUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyRecipesViewModel(val useCase: RecipeUseCase) : ViewModel() {
    private var _getMyRecipes = MutableLiveData<Resource<List<RecipeEntity>>>()
    val myRecipes: LiveData<Resource<List<RecipeEntity>>> get() = _getMyRecipes

    fun getMyRecipes() = viewModelScope.launch(Dispatchers.Main) {
        _getMyRecipes.value = useCase.getMyRecipes()
    }
}
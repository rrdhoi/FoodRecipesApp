package com.jagoteori.foodrecipesapp.presentation.ui.pages.profile.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jagoteori.foodrecipesapp.domain.entity.RecipeEntity
import com.jagoteori.foodrecipesapp.domain.usecase.RecipeUseCase
import com.jagoteori.foodrecipesapp.presentation.ui.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class MyRecipesViewModel(val useCase: RecipeUseCase) : ViewModel() {
    private var _getMyRecipes: MutableStateFlow<UiState<List<RecipeEntity>>> =
        MutableStateFlow(UiState.Loading)
    val myRecipes: StateFlow<UiState<List<RecipeEntity>>> get() = _getMyRecipes

    private fun getMyRecipes() = viewModelScope.launch(Dispatchers.Main) {
        useCase.getMyRecipes().catch { error ->
            _getMyRecipes.value = UiState.Error(errorMessage = error.message.toString())
        }.collect {
            _getMyRecipes.value = UiState.Success(it.data)
        }
    }

    init {
        getMyRecipes()
    }
}
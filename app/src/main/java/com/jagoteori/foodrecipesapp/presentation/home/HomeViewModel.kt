package com.jagoteori.foodrecipesapp.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.jagoteori.foodrecipesapp.data.Resource
import com.jagoteori.foodrecipesapp.domain.entity.RecipeEntity
import com.jagoteori.foodrecipesapp.domain.usecase.RecipeUseCase
import com.jagoteori.foodrecipesapp.presentation.ui.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(private val recipeUseCase: RecipeUseCase) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<RecipeEntity>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<RecipeEntity>>>
        get() = _uiState

    init {
        viewModelScope.launch {
            recipeUseCase.getAllRecipes()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { recipes ->
                    _uiState.value = UiState.Success(recipes.data)
                }
        }
    }
}
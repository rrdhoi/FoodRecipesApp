package com.jagoteori.foodrecipesapp.presentation.add_recipe

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
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

class AddRecipeViewModel(private val useCase: RecipeUseCase) : ViewModel() {
    private val _addRecipe = MutableLiveData<Resource<String>>()
    val addRecipe: LiveData<Resource<String>> get() = _addRecipe

    private var _getMyUser = MutableLiveData<Resource<UserEntity>>()
    val myUser: LiveData<Resource<UserEntity>> get() = _getMyUser

    fun getMyUser() = viewModelScope.launch(Dispatchers.Main) {
        _getMyUser.value = useCase.getMyUser()
    }

    fun addRecipe(recipeEntity: RecipeEntity) = viewModelScope.launch(Dispatchers.Main) {
        _addRecipe.postValue(useCase.addRecipe(recipeEntity))
    }

    var title by mutableStateOf(TextFieldValue(""))
    var titleError by mutableStateOf(false)
    var titleErrorMessage by mutableStateOf("")

    var category by mutableStateOf(TextFieldValue(""))
    var categoryError by mutableStateOf(false)
    var categoryErrorMessage by mutableStateOf("")

    var description by mutableStateOf(TextFieldValue(""))
    var descriptionError by mutableStateOf(false)
    var descriptionErrorMessage by mutableStateOf("")
}
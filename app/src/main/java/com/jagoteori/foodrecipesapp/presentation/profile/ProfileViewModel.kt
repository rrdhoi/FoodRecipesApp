package com.jagoteori.foodrecipesapp.presentation.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jagoteori.foodrecipesapp.data.Resource
import com.jagoteori.foodrecipesapp.domain.entity.UserEntity
import com.jagoteori.foodrecipesapp.domain.usecase.RecipeUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel(private val recipeUseCase: RecipeUseCase) : ViewModel() {
    private var _getMyUser = MutableLiveData<Resource<UserEntity>>()
    val myUser: LiveData<Resource<UserEntity>> get() = _getMyUser

    init {
        viewModelScope.launch(Dispatchers.Main) {
            _getMyUser.value = recipeUseCase.getMyUser()
        }
    }

    fun updateUser(userEntity: UserEntity) = viewModelScope.launch(Dispatchers.IO) {
        recipeUseCase.updateUser(userEntity)
    }
}
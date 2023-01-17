package com.jagoteori.foodrecipesapp.presentation.auth.sign_up

import androidx.lifecycle.*
import com.jagoteori.foodrecipesapp.data.Resource
import com.jagoteori.foodrecipesapp.domain.entity.UserEntity
import com.jagoteori.foodrecipesapp.domain.usecase.RecipeUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignUpViewModel(val useCase: RecipeUseCase) : ViewModel() {
    private val _userSignUp = MutableLiveData<Resource<String>>()
    val userSignUp: LiveData<Resource<String>> get() = _userSignUp

    fun createUserAccount(user: UserEntity, password: String) = viewModelScope.launch(Dispatchers.Main) {
        useCase.userSignUp(user, password).collect {
            _userSignUp.postValue(it)
        }
    }
}
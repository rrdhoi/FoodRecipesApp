package com.jagoteori.foodrecipesapp.presentation.auth.sign_in

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jagoteori.foodrecipesapp.data.Resource
import com.jagoteori.foodrecipesapp.domain.entity.UserEntity
import com.jagoteori.foodrecipesapp.domain.usecase.RecipeUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignInViewModel(val useCase: RecipeUseCase) : ViewModel() {
    private val _userSignIn = MutableLiveData<Resource<String>>()
    val userSignIn: LiveData<Resource<String>> get() = _userSignIn

    fun login(email:String, password: String) = viewModelScope.launch(Dispatchers.Main) {
        useCase.userSignIn(email, password).collect {
            _userSignIn.postValue(it)
        }
    }
}
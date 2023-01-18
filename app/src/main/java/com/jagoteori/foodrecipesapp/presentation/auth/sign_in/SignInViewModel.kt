package com.jagoteori.foodrecipesapp.presentation.auth.sign_in

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jagoteori.foodrecipesapp.app.extention.errorHandler
import com.jagoteori.foodrecipesapp.app.extention.isNotErrorHandler
import com.jagoteori.foodrecipesapp.data.Resource
import com.jagoteori.foodrecipesapp.domain.usecase.RecipeUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignInViewModel(val useCase: RecipeUseCase) : ViewModel() {
    var email by mutableStateOf(TextFieldValue(""))
    var emailError by mutableStateOf(false)
    var emailErrorMessage by mutableStateOf("")

    var password by mutableStateOf(TextFieldValue(""))
    var passwordError by mutableStateOf(false)
    var passwordErrorMessage by mutableStateOf("")

    private val _userSignIn = MutableLiveData<Resource<String>>()
    val userSignIn: LiveData<Resource<String>> get() = _userSignIn

    fun checkFormIsValid(): Boolean {
        val isNotEmailValid =
            ((!android.util.Patterns.EMAIL_ADDRESS.matcher(email.text).matches()).errorHandler {
                emailErrorMessage = "Masukkan email valid"
                emailError = true
            } or (email.text.isBlank()).errorHandler {
                emailErrorMessage = "Masukkan email kamu"
                emailError = true
            }).isNotErrorHandler { emailError = false }

        val isNotPasswordValid =
            ((password.text.length <= 5).errorHandler {
                passwordErrorMessage = "Password kurang dari 6"
                passwordError = true
            } or (password.text.isBlank()).errorHandler {
                passwordErrorMessage = "Masukkan password kamu"
                passwordError = true
            }).isNotErrorHandler { passwordError = false }

        return !(isNotEmailValid or isNotPasswordValid)
    }


    fun login() = viewModelScope.launch(Dispatchers.Main) {
        useCase.userSignIn(email.text, password.text).collect {
            _userSignIn.postValue(it)
        }
    }
}
package com.jagoteori.foodrecipesapp.presentation.auth.sign_up

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jagoteori.foodrecipesapp.app.extention.isEmptyOrBlank
import com.jagoteori.foodrecipesapp.app.extention.isInvalidEmailFormat
import com.jagoteori.foodrecipesapp.app.extention.isLessThan6
import com.jagoteori.foodrecipesapp.app.extention.isNotErrorHandler
import com.jagoteori.foodrecipesapp.data.Resource
import com.jagoteori.foodrecipesapp.domain.entity.UserEntity
import com.jagoteori.foodrecipesapp.domain.usecase.RecipeUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignUpViewModel(val useCase: RecipeUseCase) : ViewModel() {
    var fullName by mutableStateOf(TextFieldValue(""))
    var fullNameError by mutableStateOf(false)
    var fullNameErrorMessage by mutableStateOf("")

    var email by mutableStateOf(TextFieldValue(""))
    var emailError by mutableStateOf(false)
    var emailErrorMessage by mutableStateOf("")

    var password by mutableStateOf(TextFieldValue(""))
    var passwordError by mutableStateOf(false)
    var passwordErrorMessage by mutableStateOf("")

    var passwordRepeat by mutableStateOf(TextFieldValue(""))
    var passwordRepeatError by mutableStateOf(false)
    var passwordRepeatErrorMessage by mutableStateOf("")

    private val _userSignUp = MutableLiveData<Resource<String>>()
    val userSignUp: LiveData<Resource<String>> get() = _userSignUp

    fun checkFormIsInvalid(): Boolean {
        val isInvalidFullName = (fullName.isEmptyOrBlank("nama lengkap") {
            fullNameErrorMessage = it
            fullNameError = true
        }).isNotErrorHandler {
            fullNameError = false
        }

        val isInvalidEmail = with(email) {
            isInvalidEmailFormat {
                emailErrorMessage = it
                emailError = true
            } or isEmptyOrBlank("email") {
                emailErrorMessage = it
                emailError = true
            }
        }.isNotErrorHandler {
            emailError = false
        }

        val isInvalidPassword = with(password) {
            isLessThan6("password") { errorMessage ->
                passwordErrorMessage = errorMessage
                passwordError = true
            } or isEmptyOrBlank("password") { errorMessage ->
                passwordErrorMessage = errorMessage
                passwordError = true
            }
        }.isNotErrorHandler { passwordError = false }


        val isInvalidPasswordRepeat = with(passwordRepeat) {
            isLessThan6("password") { errorMessage ->
                passwordRepeatErrorMessage = errorMessage
                passwordRepeatError = true
            } or isEmptyOrBlank("password") { errorMessage ->
                passwordRepeatErrorMessage = errorMessage
                passwordRepeatError = true
            }
        }.isNotErrorHandler {
            passwordRepeatError = false
        }

        val isPasswordNotMatch = if (password.text != passwordRepeat.text) {
            passwordRepeatErrorMessage = "masukkan password dengan benar"
            passwordRepeatError = true
            true
        } else false


        return (isInvalidEmail or
                isInvalidPassword or
                isInvalidFullName or
                isInvalidPasswordRepeat or
                isPasswordNotMatch)
    }

    fun createUserAccount() =
        viewModelScope.launch(Dispatchers.Main) {
            val user = UserEntity(
                userId = null,
                name = fullName.text,
                email = email.text,
                profilePicture = null
            )

            useCase.userSignUp(user, password.text).collect {
                _userSignUp.postValue(it)
            }
        }
}
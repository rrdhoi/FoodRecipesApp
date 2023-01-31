package com.jagoteori.foodrecipesapp.presentation.ui.pages.sign_up

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jagoteori.foodrecipesapp.presentation.auth.sign_up.SignUpViewModel
import com.jagoteori.foodrecipesapp.presentation.ui.components.CustomTextField

@Composable
fun FormSignUp(modifier: Modifier, viewModel: SignUpViewModel) {
    Column {
        CustomTextField(
            title = "Nama Lengkap",
            modifier = modifier,
            value = viewModel.fullName,
            onValueChange = { viewModel.fullName = it },
            isError = viewModel.fullNameError,
            errorMessage = viewModel.fullNameErrorMessage
        )
        CustomTextField(
            title = "Email Address",
            modifier = modifier,
            value = viewModel.email,
            onValueChange = { viewModel.email = it },
            isError = viewModel.emailError,
            errorMessage = viewModel.emailErrorMessage
        )
        CustomTextField(
            title = "Password",
            modifier = modifier,
            value = viewModel.password,
            onValueChange = { viewModel.password = it },
            isError = viewModel.passwordError,
            errorMessage = viewModel.passwordErrorMessage,
            isPassword = true
        )
        CustomTextField(
            title = "Ulangi Password",
            modifier = modifier,
            value = viewModel.passwordRepeat,
            onValueChange = { viewModel.passwordRepeat = it },
            isError = viewModel.passwordRepeatError,
            errorMessage = viewModel.passwordRepeatErrorMessage,
            isPassword = true
        )

    }
}

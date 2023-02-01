package com.jagoteori.foodrecipesapp.presentation.ui.pages.sign_in.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jagoteori.foodrecipesapp.presentation.ui.components.CustomTextField
import com.jagoteori.foodrecipesapp.presentation.ui.pages.sign_in.view_model.SignInViewModel

@Composable
fun FormSignIn(modifier: Modifier, viewModel: SignInViewModel) {
    Column(modifier = modifier.padding(top = 16.dp)) {
        CustomTextField(
            title = "Email",
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
    }
}
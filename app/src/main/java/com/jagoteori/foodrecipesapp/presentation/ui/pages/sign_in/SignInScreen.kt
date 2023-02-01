package com.jagoteori.foodrecipesapp.presentation.ui.pages.sign_in


import androidx.compose.foundation.layout.*
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.jagoteori.foodrecipesapp.data.Resource
import com.jagoteori.foodrecipesapp.presentation.ui.components.ButtonPrimary
import com.jagoteori.foodrecipesapp.presentation.ui.components.LoadingProgressIndicator
import com.jagoteori.foodrecipesapp.presentation.ui.pages.sign_in.components.FormSignIn
import com.jagoteori.foodrecipesapp.presentation.ui.pages.sign_in.view_model.SignInViewModel
import com.jagoteori.foodrecipesapp.presentation.ui.theme.BlackColor500
import com.jagoteori.foodrecipesapp.presentation.ui.theme.GreyColor300
import com.jagoteori.foodrecipesapp.presentation.ui.theme.GreyColor500
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    viewModel: SignInViewModel = koinViewModel(),
    scope: CoroutineScope,
    scaffoldState: ScaffoldState,
    navigateToRegister: () -> Unit,
    navigateToHome: () -> Unit
) {

    Box(modifier = modifier.fillMaxSize()) {
        when (val response = viewModel.userSignIn) {
            is Resource.Loading -> {
                LoadingProgressIndicator(modifier = modifier)
            }

            is Resource.Success -> {
                if (response.data == true) {
                    LaunchedEffect(Unit) {
                        navigateToHome()
                    }
                }
            }

            is Resource.Error -> {
                LaunchedEffect(Unit) {
                    scope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(
                            message = response.message.toString()
                        )
                    }
                }
            }
        }

        Column(modifier = modifier.padding(horizontal = 24.dp)) {
            FormSignInHeader(modifier = modifier)
            FormSignIn(modifier = modifier, viewModel = viewModel)
            ButtonPrimary(
                text = "Masuk", modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 60.dp),
                onClickButton = {
                    if (viewModel.checkFormIsValid()) {
                        viewModel.login()
                    }
                }
            )

            ButtonRegistration(modifier = modifier, onClickButton = navigateToRegister)
        }
    }
}

@Composable
fun ButtonRegistration(modifier: Modifier, onClickButton: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 30.dp)
    ) {
        Text(
            text = "Belum punya akun?",
            modifier = modifier.padding(end = 2.dp),
            style = TextStyle(color = GreyColor500, fontSize = 14.sp)
        )

        TextButton(onClick = onClickButton) {
            Text(
                text = "Daftar", style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    color = BlackColor500,
                    textDecoration = TextDecoration.Underline
                )
            )
        }
    }
}

@Composable
fun FormSignInHeader(modifier: Modifier) {
    Text(
        text = "Selamat Datang,", style = TextStyle(
            fontSize = 22.sp,
            color = BlackColor500
        ), modifier = modifier.padding(top = 24.dp, bottom = 6.dp)
    )

    Text(
        text = "Masuk untuk melanjutkan!", style = TextStyle(
            color = GreyColor300,
            fontSize = 14.sp
        )
    )
}

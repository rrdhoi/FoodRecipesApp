package com.jagoteori.foodrecipesapp.presentation.ui.pages.sign_up

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jagoteori.foodrecipesapp.R
import com.jagoteori.foodrecipesapp.data.Resource
import com.jagoteori.foodrecipesapp.presentation.ui.components.ButtonPrimary
import com.jagoteori.foodrecipesapp.presentation.ui.components.LoadingProgressIndicator
import com.jagoteori.foodrecipesapp.presentation.ui.pages.sign_up.components.FormSignUp
import com.jagoteori.foodrecipesapp.presentation.ui.pages.sign_up.view_model.SignUpViewModel
import com.jagoteori.foodrecipesapp.presentation.ui.theme.BlackColor500
import com.jagoteori.foodrecipesapp.presentation.ui.theme.GreyColor300
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import timber.log.Timber

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    viewModel: SignUpViewModel = koinViewModel(),
    scope: CoroutineScope,
    scaffoldState: ScaffoldState,
    navigateToLogin: () -> Unit,
    navigateToHome: () -> Unit
) {
    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = modifier
                .padding(horizontal = 24.dp, vertical = 24.dp)
                .verticalScroll(
                    rememberScrollState()
                )
        ) {
            FormSignUpHeader(modifier = modifier, onClickIcon = navigateToLogin)
            FormSignUp(modifier = modifier, viewModel = viewModel)
            ButtonPrimary(
                text = "Masuk", modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp, bottom = 30.dp),
                onClickButton = {
                    if (!viewModel.checkFormIsInvalid()) {
                        viewModel.createUserAccount()
                    }
                }
            )
        }

        when (val response = viewModel.userSignUp) {
            is Resource.Loading -> {
                Timber.d("kok sudah loading? : ")
                LoadingProgressIndicator(modifier = modifier)
            }
            is Resource.Success -> {
               LaunchedEffect(response) {
                   if (response.data == true) {
                       navigateToHome()
                   }
               }
            }
            is Resource.Error -> {
                LaunchedEffect(response) {
                    scope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(
                            message = response.message.toString()
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun FormSignUpHeader(modifier: Modifier, onClickIcon: () -> Unit) {
    Row(horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = onClickIcon) {
            Icon(
                modifier = modifier.size(24.dp),
                painter = painterResource(id = R.drawable.ic_arrow_back),
                contentDescription = "Icon back to login",
            )
        }
        Column(modifier = modifier.padding(start = 12.dp)) {
            Text(
                text = "Buat Akun,", style = TextStyle(
                    fontSize = 22.sp,
                    color = BlackColor500
                ), modifier = modifier.padding(bottom = 4.dp)
            )

            Text(
                text = "Daftar untuk memulai!", style = TextStyle(
                    color = GreyColor300,
                    fontSize = 14.sp
                )
            )
        }
    }
}

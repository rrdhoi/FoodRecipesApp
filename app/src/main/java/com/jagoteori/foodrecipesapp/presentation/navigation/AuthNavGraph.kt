package com.jagoteori.foodrecipesapp.presentation.navigation

import androidx.compose.material.ScaffoldState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.jagoteori.foodrecipesapp.presentation.navigation.routes.Graph
import com.jagoteori.foodrecipesapp.presentation.navigation.routes.Screen
import com.jagoteori.foodrecipesapp.presentation.ui.pages.sign_in.SignInScreen
import com.jagoteori.foodrecipesapp.presentation.ui.pages.sign_up.SignUpScreen
import kotlinx.coroutines.CoroutineScope

fun NavGraphBuilder.authNavGraph(
    navController: NavController,
    scaffoldState: ScaffoldState,
    scope: CoroutineScope
) {
    navigation(
        route = Graph.AUTHENTICATION,
        startDestination = Screen.Login.route,
    ) {
        composable(Screen.Login.route) {
            SignInScreen(
                navigateToHome = {
                    navController.navigate(Graph.MAIN)
                },
                navigateToRegister = {
                    navController.navigate(Screen.Registration.route)
                },
                scaffoldState = scaffoldState,
                scope = scope,
            )
        }

        composable(Screen.Registration.route) {
            SignUpScreen(
                navigateToHome = {
                    navController.navigate(Graph.MAIN)
                },
                navigateToLogin = {
                    navController.navigate(Screen.Login.route)
                },
                scaffoldState = scaffoldState,
                scope = scope
            )
        }
    }
}
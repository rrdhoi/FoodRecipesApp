package com.jagoteori.foodrecipesapp

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.firebase.auth.FirebaseAuth
import com.jagoteori.foodrecipesapp.domain.entity.RecipeEntity
import com.jagoteori.foodrecipesapp.presentation.navigation.Screen
import com.jagoteori.foodrecipesapp.presentation.ui.extention.fromJson
import com.jagoteori.foodrecipesapp.presentation.ui.extention.toJson
import com.jagoteori.foodrecipesapp.presentation.ui.pages.detail_recipe.DetailRecipeScreen
import com.jagoteori.foodrecipesapp.presentation.ui.pages.home.HomeScreen
import com.jagoteori.foodrecipesapp.presentation.ui.pages.sign_in.SignInScreen
import com.jagoteori.foodrecipesapp.presentation.ui.pages.sign_up.SignUpScreen

@Composable
fun FoodRecipeApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val uid = FirebaseAuth.getInstance().uid


    Scaffold(scaffoldState = scaffoldState) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination =  if (uid != null) Screen.Home.route else Screen.Login.route,
            modifier = modifier.padding(paddingValues),
        ) {
            composable(Screen.Login.route) {
                SignInScreen(
                    navigateToRegister = {
                        navController.navigate(Screen.Registration.route)
                    },
                    scaffoldState = scaffoldState,
                    scope = scope,
                    navController = navController
                )
            }

            composable(Screen.Registration.route) {
                SignUpScreen(
                    navigateToHome = {
                        navController.navigate(Screen.Home.route)
                    },
                    navigateToLogin = {
                        navController.navigate(Screen.Login.route)
                    },
                    scaffoldState = scaffoldState,
                    scope = scope
                )
            }

            composable(Screen.Home.route) {
                HomeScreen(
                    onItemRecommendationClicked = { recipeEntity ->
                        val recipe = recipeEntity.toJson()
                        navController.navigate(Screen.DetailRecipe.createRoute(recipe))
                    },
                    onItemCategoryClicked = { recipeEntity ->
                        val recipe = recipeEntity.toJson()
                        navController.navigate(Screen.DetailRecipe.createRoute(recipe))
                    },
                )
            }

            composable(
                route = Screen.DetailRecipe.route,
                arguments = listOf(navArgument("recipe") { type = NavType.StringType })
            ) {
                it.arguments?.getString("recipe")?.let { jsonString ->
                    val recipe = jsonString.fromJson(RecipeEntity::class.java)
                    DetailRecipeScreen(
                        recipeEntity = recipe,
                        onBackPressed = {
                            navController.navigateUp()
                        }
                    )
                }

            }
        }
    }
}
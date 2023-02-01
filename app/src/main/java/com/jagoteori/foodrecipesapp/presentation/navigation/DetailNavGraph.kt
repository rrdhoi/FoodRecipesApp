package com.jagoteori.foodrecipesapp.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.google.firebase.auth.FirebaseAuth
import com.jagoteori.foodrecipesapp.presentation.navigation.routes.Graph
import com.jagoteori.foodrecipesapp.presentation.navigation.routes.Screen
import com.jagoteori.foodrecipesapp.presentation.ui.pages.detail_recipe.DetailRecipeScreen
import com.jagoteori.foodrecipesapp.presentation.ui.pages.detail_recipe.view_model.DetailRecipeViewModel
import com.jagoteori.foodrecipesapp.presentation.ui.pages.profile.MyRecipeScreen
import com.jagoteori.foodrecipesapp.presentation.ui.pages.profile.ProfileScreen

fun NavGraphBuilder.detailNavGraph(
    navHostController: NavHostController,
    detailRecipeViewModel: DetailRecipeViewModel
) {
    navigation(
        route = Graph.DETAIL,
        startDestination = Screen.DetailRecipe.route
    ) {
        composable(Screen.DetailRecipe.route) {
            DetailRecipeScreen(
               detailViewModel = detailRecipeViewModel,
                onBackPressed = {
                    navHostController.popBackStack()
                }
            )
        }
    }
}
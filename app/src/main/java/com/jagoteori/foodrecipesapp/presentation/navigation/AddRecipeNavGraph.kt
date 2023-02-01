package com.jagoteori.foodrecipesapp.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.jagoteori.foodrecipesapp.presentation.navigation.routes.Graph
import com.jagoteori.foodrecipesapp.presentation.navigation.routes.Screen
import com.jagoteori.foodrecipesapp.presentation.ui.pages.add_recipe.AddRecipeScreen

fun NavGraphBuilder.addRecipeNavGraph(navHostController: NavHostController) {
    navigation(route = Graph.ADD_RECIPE, startDestination = Screen.AddNewRecipe.route) {
        composable(Screen.AddNewRecipe.route) {
            AddRecipeScreen(
                onBackPressed = {
                    navHostController.navigateUp()
                }
            )
        }
    }
}
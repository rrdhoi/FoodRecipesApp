package com.jagoteori.foodrecipesapp.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.jagoteori.foodrecipesapp.presentation.navigation.routes.Graph
import com.jagoteori.foodrecipesapp.presentation.navigation.routes.Screen
import com.jagoteori.foodrecipesapp.presentation.ui.pages.detail_recipe.view_model.DetailRecipeViewModel
import com.jagoteori.foodrecipesapp.presentation.ui.pages.profile.MyRecipeScreen

fun NavGraphBuilder.myRecipeNavGraph(
    rootNavController: NavHostController,
    detailRecipeViewModel: DetailRecipeViewModel,
) {
    navigation(
        route = Graph.MY_RECIPES,
        startDestination = Screen.MyRecipes.route
    ) {
        composable(Screen.MyRecipes.route) {
            MyRecipeScreen(
                onItemClicked = { recipeEntity ->
                    detailRecipeViewModel.setRecipe(newRecipe = recipeEntity)
                    rootNavController.navigate(Graph.DETAIL)
                },
                onBackPressed = {
                    rootNavController.popBackStack()
                }
            )
        }
    }
}
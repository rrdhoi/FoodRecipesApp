package com.jagoteori.foodrecipesapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.firebase.auth.FirebaseAuth
import com.jagoteori.foodrecipesapp.presentation.navigation.routes.Graph
import com.jagoteori.foodrecipesapp.presentation.navigation.routes.Screen
import com.jagoteori.foodrecipesapp.presentation.ui.pages.detail_recipe.view_model.DetailRecipeViewModel
import com.jagoteori.foodrecipesapp.presentation.ui.pages.home.HomeScreen
import com.jagoteori.foodrecipesapp.presentation.ui.pages.profile.ProfileScreen

@Composable
fun HomeNavGraph(
    modifier: Modifier,
    mainNavController: NavHostController,
    detailRecipeViewModel: DetailRecipeViewModel,
    onNavigateToDetailRecipe : () -> Unit,
    onNavigateToMyRecipes: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    NavHost(
        navController = mainNavController,
        route = Graph.MAIN,
        startDestination = Screen.Home.route,
        modifier = modifier,
    ) {

        composable(Screen.Home.route) {
            HomeScreen(
                onItemRecommendationClicked = { recipeEntity ->
                    detailRecipeViewModel.setRecipe(newRecipe = recipeEntity)
                    onNavigateToDetailRecipe()
                },
                onItemCategoryClicked = { recipeEntity ->
                    detailRecipeViewModel.setRecipe(newRecipe = recipeEntity)
                    onNavigateToDetailRecipe()
                },
            )
        }

        composable(Screen.Profile.route) {
            ProfileScreen(
                onClickReports = {
                    onNavigateToMyRecipes()
                },
                onSignOut = {
                    FirebaseAuth.getInstance().signOut()
                    onNavigateToLogin()
                }
            )
        }
    }
}
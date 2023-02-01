package com.jagoteori.foodrecipesapp.presentation.navigation.routes

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Login : Screen("login")
    object Registration : Screen("registration")
    object Profile : Screen("profile")
    object MyRecipes : Screen("my_recipes")
    object AddNewRecipe : Screen("add_new_recipe")
    object DetailRecipe : Screen("detail_recipe")
}
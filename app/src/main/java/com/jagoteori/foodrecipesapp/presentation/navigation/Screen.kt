package com.jagoteori.foodrecipesapp.presentation.navigation

import com.jagoteori.foodrecipesapp.domain.entity.RecipeEntity

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Login : Screen("login")
    object Registration : Screen("registration")
    object Profile : Screen("profile")
    object MyRecipes : Screen("my_recipes")
    object AddNewRecipe : Screen("add_new_recipe")
    object DetailRecipe : Screen("detail_recipe/{recipe}") {
        fun createRoute(recipe: String?) = "detail_recipe/$recipe"
    }


}
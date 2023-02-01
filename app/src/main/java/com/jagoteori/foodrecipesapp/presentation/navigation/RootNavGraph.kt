package com.jagoteori.foodrecipesapp.presentation.navigation

import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.jagoteori.foodrecipesapp.presentation.navigation.routes.Graph
import com.jagoteori.foodrecipesapp.presentation.ui.pages.detail_recipe.view_model.DetailRecipeViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun RootNavGraph(
    rootNavController: NavHostController = rememberNavController(),
) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val detailRecipeViewModel: DetailRecipeViewModel = koinViewModel()

    val uid = FirebaseAuth.getInstance().uid

    NavHost(
        route = Graph.ROOT,
        navController = rootNavController,
        startDestination = if (uid != null) Graph.MAIN else Graph.AUTHENTICATION,
    ) {

        authNavGraph(rootNavController, scaffoldState, scope)
        mainNavGraph(rootNavController, detailRecipeViewModel)

        addRecipeNavGraph(rootNavController)

        detailNavGraph(rootNavController, detailRecipeViewModel)

        myRecipeNavGraph(rootNavController, detailRecipeViewModel)
    }

}
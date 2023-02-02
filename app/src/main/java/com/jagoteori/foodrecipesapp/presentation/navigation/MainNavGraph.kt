package com.jagoteori.foodrecipesapp.presentation.navigation

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.jagoteori.foodrecipesapp.R
import com.jagoteori.foodrecipesapp.presentation.navigation.routes.Graph
import com.jagoteori.foodrecipesapp.presentation.navigation.routes.Screen
import com.jagoteori.foodrecipesapp.presentation.ui.pages.detail_recipe.view_model.DetailRecipeViewModel
import com.jagoteori.foodrecipesapp.presentation.ui.theme.BlackColor500
import com.jagoteori.foodrecipesapp.presentation.ui.theme.GreyColor300
import com.jagoteori.foodrecipesapp.presentation.ui.theme.WhiteColor

fun NavGraphBuilder.mainNavGraph(
    rootNavController: NavHostController,
    detailRecipeViewModel: DetailRecipeViewModel,
) {
    composable(route = Graph.MAIN) {
        MainScreen(
            detailRecipeViewModel = detailRecipeViewModel,
            onNavigateToDetailRecipe = {
                rootNavController.navigate(Graph.DETAIL) {
                    popUpTo(rootNavController.graph.findStartDestination().id)
                    launchSingleTop = true
                }
            },
            onNavigateToLogin = {
                detailRecipeViewModel.user = null
                rootNavController.navigate(Graph.AUTHENTICATION)
            },
            onNavigateToMyRecipes = {
                rootNavController.navigate(Graph.MY_RECIPES)
            },
            onNavigateToAddRecipe = {
                rootNavController.navigate(Graph.ADD_RECIPE) {
                    popUpTo(rootNavController.graph.findStartDestination().id)
                    launchSingleTop = true
                }
            }
        )
    }
}

@Composable
fun MainScreen(
    mainNavController: NavHostController = rememberNavController(),
    detailRecipeViewModel: DetailRecipeViewModel,
    onNavigateToDetailRecipe: () -> Unit,
    onNavigateToMyRecipes: () -> Unit,
    onNavigateToLogin: () -> Unit,
    onNavigateToAddRecipe: () -> Unit
) {
    Scaffold(
        bottomBar = {
            BottomAppBar(
                modifier = Modifier
                    .height(60.dp)
                    .clip(RoundedCornerShape(15.dp, 15.dp, 0.dp, 0.dp)),
                cutoutShape = CircleShape,
                backgroundColor = WhiteColor,
                elevation = 50.dp
            ) {
                BottomNav(mainNavController)
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,
        floatingActionButton = {
            FloatingActionButton(
                shape = CircleShape,
                onClick = onNavigateToAddRecipe,
                contentColor = WhiteColor
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_add_recipe),
                    contentDescription = "Add new recipe icon"
                )
            }
        }
    ) {
        HomeNavGraph(
            modifier = Modifier.padding(it),
            mainNavController = mainNavController,
            detailRecipeViewModel = detailRecipeViewModel,
            onNavigateToDetailRecipe = onNavigateToDetailRecipe,
            onNavigateToMyRecipes = onNavigateToMyRecipes,
            onNavigateToLogin = onNavigateToLogin
        )
    }
}

data class BottomNavigationScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
)

@Composable
fun BottomNav(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination
    val items = listOf(
        BottomNavigationScreen(
            route = Screen.Home.route,
            title = "Home",
            icon = Icons.Rounded.Home
        ),
        BottomNavigationScreen(
            route = "",
            title = "",
            icon = Icons.Default.Add
        ),
        BottomNavigationScreen(
            route = Screen.Profile.route,
            title = "Profile",
            icon = Icons.Rounded.Person
        )
    )

    BottomNavigation(
        modifier = Modifier
            .padding(12.dp, 0.dp, 12.dp, 0.dp)
            .height(100.dp),
        backgroundColor = WhiteColor,
        elevation = 0.dp
    ) {
        items.forEachIndexed { index, screen ->
            BottomNavigationItem(
                icon = {
                    if (index != 1)
                        Icon(
                            imageVector = screen.icon,
                            contentDescription = "",
                            modifier = Modifier.size(28.dp),
                        )
                },
                label = {},
                selected = currentRoute?.hierarchy?.any { screen.route == it.route } == true,
                selectedContentColor = BlackColor500,
                unselectedContentColor = GreyColor300,

                onClick = {
                    if (index != 1) screen.route.let { it1 ->
                        navController.navigate(it1) {
                            popUpTo(navController.graph.findStartDestination().id)
                            launchSingleTop = true
                        }
                    }
                }
            )
        }
    }
}

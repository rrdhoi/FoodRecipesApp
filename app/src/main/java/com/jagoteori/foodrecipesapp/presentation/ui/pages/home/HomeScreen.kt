package com.jagoteori.foodrecipesapp.presentation.ui.pages.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jagoteori.foodrecipesapp.domain.entity.RecipeEntity
import com.jagoteori.foodrecipesapp.presentation.home.HomeViewModel
import com.jagoteori.foodrecipesapp.presentation.ui.UiState
import com.jagoteori.foodrecipesapp.presentation.ui.components.LoadingProgressIndicator
import com.jagoteori.foodrecipesapp.presentation.ui.theme.BlackColor500
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = koinViewModel(),
    onItemRecommendationClicked: (recipe: RecipeEntity) -> Unit,
    onItemCategoryClicked: (recipe: RecipeEntity) -> Unit
) {
    Column(modifier = modifier.fillMaxSize()) {
        if (homeViewModel.isLoading) LoadingProgressIndicator(modifier = modifier)
        homeViewModel.uiState.collectAsState().value.let { uiState ->
            when (uiState) {
                is UiState.Loading -> {}
                is UiState.Success -> {
                    homeViewModel.isLoading = false
                    if (uiState.data == null) {
                       LoadingProgressIndicator(modifier = modifier)
                    } else {
                        ListRecommendationRecipe(
                            modifier = modifier,
                            recipes = uiState.data,
                            onItemRecommendationClicked = onItemRecommendationClicked
                        )

                        Text(
                            text = "Kategori", style = TextStyle(
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            ), modifier = modifier.padding(top = 24.dp, start = 24.dp, end = 24.dp)
                        )

                        HomeTabLayout(
                            recipes = uiState.data,
                            modifier = modifier,
                            onItemCategoryClicked
                        )
                    }
                }
                is UiState.Error -> {}
            }
        }
    }
}


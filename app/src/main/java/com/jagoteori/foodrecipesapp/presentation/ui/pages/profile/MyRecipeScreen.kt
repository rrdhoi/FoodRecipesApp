package com.jagoteori.foodrecipesapp.presentation.ui.pages.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.jagoteori.foodrecipesapp.R
import com.jagoteori.foodrecipesapp.domain.entity.RecipeEntity
import com.jagoteori.foodrecipesapp.presentation.profile.my_recipes.MyRecipesViewModel
import com.jagoteori.foodrecipesapp.presentation.ui.UiState
import com.jagoteori.foodrecipesapp.presentation.ui.components.CardCategoryItem
import com.jagoteori.foodrecipesapp.presentation.ui.components.ErrorMessage
import com.jagoteori.foodrecipesapp.presentation.ui.components.TopAppBarBlack
import com.jagoteori.foodrecipesapp.presentation.ui.theme.BlackColor500

@Composable
fun MyRecipeScreen(
    modifier: Modifier,
    viewModel: MyRecipesViewModel,
    onItemClicked: (recipe: RecipeEntity) -> Unit,
    onBackPressed: () -> Unit
) {
    Scaffold(topBar = {
        TopAppBarBlack(title = "ResepKu", icon = painterResource(
            id = R.drawable.ic_arrow_back_white
        ), onIconClick = { onBackPressed() })
    }) { paddingValues ->
        viewModel.myRecipes.collectAsState().value.let { uiState ->
            when (uiState) {
                is UiState.Loading -> {
                    Column(
                        modifier = modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(
                            color = BlackColor500,
                            modifier = modifier.size(48.dp)
                        )
                    }
                }
                is UiState.Success -> {
                    val listMyRecipe = uiState.data

                    if (listMyRecipe == null) {
                        Column(
                            modifier = modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            CircularProgressIndicator(
                                color = BlackColor500,
                                modifier = modifier.size(48.dp)
                            )
                        }
                    } else if (listMyRecipe.isEmpty()) {
                        ErrorMessage(
                            modifier = modifier.padding(paddingValues),
                            message = "Belum ada resep"
                        )
                    } else {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 24.dp),
                            contentPadding = PaddingValues(
                                top = 16.dp,
                                bottom = 30.dp,
                            ),
                            content = {
                                items(listMyRecipe.size) { index ->
                                    CardCategoryItem(
                                        modifier = modifier,
                                        title = listMyRecipe[index].title,
                                        publisher = listMyRecipe[index].publisher,
                                        imageRecipe = listMyRecipe[index].recipePicture
                                    ) {
                                        onItemClicked(listMyRecipe[index])
                                    }
                                }
                            })
                    }
                }
                is UiState.Error -> {
                    ErrorMessage(modifier = modifier, message = "Terjadi kesalahan")
                }
            }
        }
    }
}
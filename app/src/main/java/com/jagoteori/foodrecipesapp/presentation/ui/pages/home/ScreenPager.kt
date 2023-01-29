package com.jagoteori.foodrecipesapp.presentation.ui.pages.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jagoteori.foodrecipesapp.domain.entity.RecipeEntity
import com.jagoteori.foodrecipesapp.presentation.ui.components.CardCategoryItem

@Composable
fun ScreenPager(
    recipes: List<RecipeEntity>?,
    modifier: Modifier,
    itemCategoryOnClick: (recipe: RecipeEntity) -> Unit
) {
    if (recipes.isNullOrEmpty()) {
        Column(verticalArrangement = Arrangement.Center, modifier = modifier.height(250.dp)) {
            Text(text = "Belum ada resep :)")
        }
    } else {
        LazyColumn(
            modifier = modifier.fillMaxSize().padding(top = 8.dp),
            contentPadding = PaddingValues(
                bottom = 30.dp,
                start = 24.dp, end = 24.dp,
            ), content = {
                items(recipes.size) { index ->
                    CardCategoryItem(
                        modifier = modifier,
                        title = recipes[index].title,
                        publisher = recipes[index].publisher,
                        imageRecipe = recipes[index].recipePicture
                    ) {
                        itemCategoryOnClick(recipes[index])
                    }
                }
            })
    }
}
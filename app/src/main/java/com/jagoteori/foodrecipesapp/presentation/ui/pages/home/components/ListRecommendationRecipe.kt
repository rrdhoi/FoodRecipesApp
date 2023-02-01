package com.jagoteori.foodrecipesapp.presentation.ui.pages.home.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jagoteori.foodrecipesapp.domain.entity.RecipeEntity
import com.jagoteori.foodrecipesapp.presentation.ui.components.CardRecommendationItem
import com.jagoteori.foodrecipesapp.presentation.ui.components.ErrorMessage


@Composable
fun ListRecommendationRecipe(
    modifier: Modifier,
    recipes: List<RecipeEntity>,
    onItemRecommendationClicked: (recipe: RecipeEntity) -> Unit
) {
    Text(
        text = "Rekomendasi", style = TextStyle(
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        ), modifier = modifier.padding(top = 24.dp, start = 24.dp, end = 24.dp)
    )

    if (recipes.isEmpty()) {
        ErrorMessage(
            modifier = modifier
                .fillMaxSize()
                .height(0.dp),
            message = "Terjadi kesalahan"
        )
    } else {
        LazyRow(
            modifier = modifier.padding(top = 16.dp),
            contentPadding = PaddingValues(
                bottom = 6.dp, top = 4.dp,
                start = 24.dp, end = 24.dp,
            ),
            content = {
                items(recipes.size) { index ->
                    CardRecommendationItem(
                        modifier = modifier,
                        title = recipes[index].title,
                        publisher = recipes[index].publisher,
                        imageRecipe = recipes[index].recipePicture
                    ) {
                        onItemRecommendationClicked(recipes[index])
                    }
                }
            },
        )
    }
}
package com.jagoteori.foodrecipesapp.presentation.ui.pages.detail_recipe.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import com.jagoteori.foodrecipesapp.domain.entity.RecipeEntity
import com.jagoteori.foodrecipesapp.presentation.ui.components.DottedLineDivider
import com.jagoteori.foodrecipesapp.presentation.ui.components.IngredientItem

@Composable
fun IngredientsList(modifier: Modifier, recipeEntity: RecipeEntity) {
    LazyColumn(
        modifier = modifier
            .height(150.dp)
            .nestedScroll(object : NestedScrollConnection {}),
        verticalArrangement = Arrangement.spacedBy(6.dp),
        contentPadding = PaddingValues(bottom = 30.dp, start = 24.dp, end = 24.dp),
        content = {
            items(recipeEntity.listIngredients?.size!!) { index ->
                IngredientItem(
                    modifier = modifier,
                    ingredient = recipeEntity.listIngredients[index]
                )
                DottedLineDivider(modifier = modifier.padding(top = 6.dp))
            }
        })
}
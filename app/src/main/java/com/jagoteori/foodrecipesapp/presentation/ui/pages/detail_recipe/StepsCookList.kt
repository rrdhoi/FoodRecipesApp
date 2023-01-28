package com.jagoteori.foodrecipesapp.presentation.ui.pages.detail_recipe

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jagoteori.foodrecipesapp.domain.entity.RecipeEntity
import com.jagoteori.foodrecipesapp.presentation.ui.components.ItemStepCook

@Composable
fun StepsCookList(modifier: Modifier, recipeEntity: RecipeEntity) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(24.dp),
        contentPadding = PaddingValues(bottom = 30.dp, start = 24.dp, end = 24.dp),
        content = {
            items(recipeEntity.listStepCooking?.size!!) { index ->
                ItemStepCook(
                    modifier = modifier,
                    steps = recipeEntity.listStepCooking[index]
                )
            }
        })
}
package com.jagoteori.foodrecipesapp.presentation.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.jagoteori.foodrecipesapp.domain.entity.IngredientEntity
import com.jagoteori.foodrecipesapp.presentation.ui.theme.BlackColor500


@Composable
fun IngredientItem(modifier: Modifier, ingredient: IngredientEntity) {
    Row(modifier = modifier.fillMaxWidth()) {
        Text(
            text = ingredient.ingredient!!,
            modifier = modifier.weight(2f),
            style = TextStyle(
                color = BlackColor500,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start
            )
        )

        Text(
            text = ingredient.quantity!!,
            modifier = modifier.weight(0.4f),
            style = TextStyle(
                color = BlackColor500,
                textAlign = TextAlign.Start
            )
        )

        Text(
            text = ingredient.typeQuantity!!,
            modifier = modifier.weight(1.6f),
            style = TextStyle(
                color = BlackColor500,
                textAlign = TextAlign.Start
            )
        )
    }
}
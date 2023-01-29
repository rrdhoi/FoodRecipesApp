package com.jagoteori.foodrecipesapp.presentation.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jagoteori.foodrecipesapp.presentation.ui.theme.BlackColor500

@Composable
fun ErrorMessage(modifier: Modifier, message: String) {
    Column(
        modifier = modifier
            .height(300.dp)
    ) {
        Text(
            message, style = TextStyle(
                color = BlackColor500,
                fontSize = 18.sp
            )
        )
    }
}
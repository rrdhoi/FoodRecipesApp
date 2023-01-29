package com.jagoteori.foodrecipesapp.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.jagoteori.foodrecipesapp.presentation.ui.theme.BlackColor500
import com.jagoteori.foodrecipesapp.presentation.ui.theme.WhiteColor

@Composable
fun TopAppBarBlack(title: String, icon: Painter, onIconClick: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = WhiteColor
                )
            )
        },
        backgroundColor = BlackColor500,
        contentColor = WhiteColor,
        navigationIcon = {
            IconButton(onClick = { onIconClick() }) {
                Image(
                    painter = icon,
                    contentDescription = "Icon Back"
                )
            }
        }
    )
}
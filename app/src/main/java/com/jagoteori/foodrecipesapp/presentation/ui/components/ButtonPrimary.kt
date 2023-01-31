package com.jagoteori.foodrecipesapp.presentation.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jagoteori.foodrecipesapp.presentation.ui.theme.BlackColor500
import com.jagoteori.foodrecipesapp.presentation.ui.theme.WhiteColor

@Composable
fun ButtonPrimary(text: String, modifier: Modifier, onClickButton: () -> Unit) {
    Button(
        modifier = modifier.height(54.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = BlackColor500,
            contentColor = WhiteColor
        ),
        onClick = onClickButton
    ) {

        Text(
            text = text, style = TextStyle(
                fontSize = 16.sp
            )
        )
    }
}
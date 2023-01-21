package com.jagoteori.foodrecipesapp.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.jagoteori.foodrecipesapp.domain.entity.StepCookEntity
import com.jagoteori.foodrecipesapp.presentation.ui.theme.BackgroundColor
import com.jagoteori.foodrecipesapp.presentation.ui.theme.BlackColor500

@Composable
fun ItemStepCook(modifier: Modifier, steps: StepCookEntity) {
    Column(
        modifier = modifier
            .clip(shape = RoundedCornerShape(12.dp))
            .background(color = BackgroundColor)
    ) {

        Row(modifier = modifier.padding(start = 12.dp, top = 12.dp)) {
            Surface(
                contentColor = Color(0xffdedede),
                shape = CircleShape,
                modifier = modifier
                    .size(24.dp)
            ) {
                Text(
                    text = (steps.stepNumber!! + 1).toString(),
                    color = BlackColor500,
                    textAlign = TextAlign.Center,
                    style = TextStyle(fontSize = 14.sp),
                    modifier = modifier.padding(top = 2.dp)
                )
            }
            Text(
                text = steps.stepDescription.toString(),
                color = BlackColor500,
                modifier = modifier.padding(start = 12.dp, end = 12.dp, bottom = 12.dp)
            )
        }

        if (!steps.stepImages.isNullOrEmpty()) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = modifier.padding(top = 12.dp, start = 12.dp, end = 12.dp, bottom = 12.dp).height(120.dp)
            ) {

                for (image in steps.stepImages) {
                    AsyncImage(
                        model = image,
                        contentDescription = "Image Step Cook",
                        contentScale = ContentScale.Crop,
                        modifier = modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(8.dp))

                    )
                }

            }
        }

    }
}
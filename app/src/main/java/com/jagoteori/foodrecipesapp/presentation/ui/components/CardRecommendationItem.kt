package com.jagoteori.foodrecipesapp.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import com.jagoteori.foodrecipesapp.presentation.ui.theme.BlackColor500
import com.jagoteori.foodrecipesapp.presentation.ui.theme.GreyColor300

@Composable
fun CardRecommendationItem(
    modifier: Modifier,
    title: String?,
    publisher: String?,
    imageRecipe: String?,
    itemOnClick: () -> Unit
) {
    Card(modifier = modifier.padding(end = 16.dp), shape = RoundedCornerShape(12.dp), elevation = 5.dp) {
        ConstraintLayout(modifier = modifier.clickable { itemOnClick() }) {
            val (idImageRecipe, idTitle, idPublisher) = createRefs()

            Box(modifier = modifier
                .clip(RoundedCornerShape(12))
                .constrainAs(idImageRecipe) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }) {
                AsyncImage(
                    model = imageRecipe,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = modifier
                        .size(220.dp, 120.dp)
                )
                Surface(
                    shape = RoundedCornerShape(bottomStart = 10.dp, topEnd = 22.dp),
                    color = BlackColor500,
                    modifier = modifier
                        .width(220.dp)
                        .height(120.dp)
                        .alpha(0.15f)
                ) {}
            }

            Text(
                text = title ?: "",
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Start,
                maxLines = 2,
                fontSize = 16.sp,
                color = BlackColor500,
                modifier = modifier.constrainAs(idTitle) {
                    top.linkTo(idImageRecipe.bottom, margin = 8.dp)
                    start.linkTo(parent.start, margin = 10.dp)
                    end.linkTo(parent.end, margin = 8.dp)
                    width = Dimension.fillToConstraints
                }
            )

            Text(
                text = publisher ?: "",
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Start,
                maxLines = 2,
                fontSize = 14.sp,
                color = GreyColor300,
                modifier = modifier.constrainAs(idPublisher) {
                    top.linkTo(idTitle.bottom, margin = 4.dp)
                    start.linkTo(idTitle.start)
                    bottom.linkTo(parent.bottom, margin = 10.dp)
                }
            )
        }
    }
}
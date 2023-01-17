package com.jagoteori.foodrecipesapp.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import com.jagoteori.foodrecipesapp.presentation.ui.theme.BlackColor500
import com.jagoteori.foodrecipesapp.presentation.ui.theme.GreyColor300

@Composable
fun ItemRecommendation(
    title: String?,
    publisher: String?,
    imageRecipe: String?,
    itemOnClick: () -> Unit
) {
    ConstraintLayout(modifier = Modifier.clickable { itemOnClick() }) {
        val (idImageRecipe, idTitle, idPublisher) = createRefs()

        Box(modifier = Modifier
            .clip(RoundedCornerShape(12))
            .constrainAs(idImageRecipe) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
            }) {
            AsyncImage(
                model = imageRecipe,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(220.dp, 120.dp)
            )
            Surface(
                shape = RoundedCornerShape(bottomStart = 10.dp, topEnd = 22.dp),
                color = BlackColor500,
                modifier = Modifier
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
            modifier = Modifier.constrainAs(idTitle) {
                top.linkTo(idImageRecipe.bottom, margin = 8.dp)
                start.linkTo(parent.start, margin = 8.dp)
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
            modifier = Modifier.constrainAs(idPublisher) {
                top.linkTo(idTitle.bottom, margin = 4.dp)
                start.linkTo(idTitle.start)
                bottom.linkTo(parent.bottom, margin = 8.dp)
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NewsItemPreview() {
    MaterialTheme {
        ItemRecommendation("asdas", "New News", "2022-02-22") {}
    }
}
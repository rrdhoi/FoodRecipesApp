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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.jagoteori.foodrecipesapp.domain.entity.CommentEntity
import com.jagoteori.foodrecipesapp.presentation.ui.theme.BackgroundColor
import com.jagoteori.foodrecipesapp.presentation.ui.theme.BlackColor500
import com.jagoteori.foodrecipesapp.presentation.ui.theme.GreyColor500

@Composable
fun CommentItem(modifier: Modifier, comments: CommentEntity) {
    Row(
        modifier = modifier
            .clip(shape = RoundedCornerShape(12.dp))
            .background(color = BackgroundColor)
    ) {
        if (comments.profilePicture.isNullOrEmpty())
            Surface(
                modifier = modifier
                    .size(40.dp)
                    .padding(start = 12.dp, top = 12.dp),
                shape = CircleShape,
                color = Color(0xffdedede)
            ) {}
        else AsyncImage(
            model = comments.profilePicture,
            contentDescription = "Image Step Cook",
            contentScale = ContentScale.Crop,
            modifier = modifier
                .size(40.dp)
                .padding(start = 12.dp, top = 12.dp)
                .clip(CircleShape),
        )

        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 12.dp, start = 16.dp, end = 12.dp, bottom = 12.dp)
        ) {
            Text(
                text = comments.name ?: "",
                style = TextStyle(fontWeight = FontWeight.Bold, color = BlackColor500)
            )
            Text(
                text = comments.message ?: "",
                style = TextStyle(color = GreyColor500),
                modifier = modifier.padding(top = 8.dp)
            )
        }
    }
}
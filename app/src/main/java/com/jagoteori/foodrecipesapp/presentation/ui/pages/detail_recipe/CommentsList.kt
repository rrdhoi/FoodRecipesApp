package com.jagoteori.foodrecipesapp.presentation.ui.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jagoteori.foodrecipesapp.domain.entity.RecipeEntity
import com.jagoteori.foodrecipesapp.presentation.detail_recipe.DetailRecipeViewModel
import com.jagoteori.foodrecipesapp.presentation.ui.components.CommentItem
import com.jagoteori.foodrecipesapp.presentation.ui.components.CommentTextField
import com.jagoteori.foodrecipesapp.presentation.ui.components.CustomOutlineTextField
import com.jagoteori.foodrecipesapp.presentation.ui.theme.BlackColor500


@Composable
fun CommentsList(
    modifier: Modifier,
    recipeEntity: RecipeEntity,
    detailViewModel: DetailRecipeViewModel
) {
    if (recipeEntity.listComments.isNullOrEmpty()) {
        Column(
            modifier = modifier.height(150.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Belum ada komentar",
                color = BlackColor500,
                style = TextStyle(fontSize = 16.sp)
            )
        }
    } else {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 30.dp, start = 24.dp, end = 24.dp),
            content = {
                items(recipeEntity.listComments.size) { index ->
                    CommentItem(
                        modifier = modifier,
                        comments = recipeEntity.listComments[index]
                    )
                }
                item {
                    CommentTextField(
                        modifier = modifier,
                        value = detailViewModel.addComment,
                        onValueChange = {
                            detailViewModel.addComment = it
                        },
                        onSendClick = {
                            if (!detailViewModel.checkCommentFormIsInvalid()) {
                                detailViewModel.addComment(recipeEntity.id!!).invokeOnCompletion {
                                    detailViewModel.addComment = TextFieldValue("")
                                    // TODO:: Snack bar error
                                }
                            }
                        }
                    )
                }
            })
    }
}

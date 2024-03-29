package com.jagoteori.foodrecipesapp.presentation.ui.pages.detail_recipe.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jagoteori.foodrecipesapp.domain.entity.RecipeEntity
import com.jagoteori.foodrecipesapp.presentation.ui.components.CommentItem
import com.jagoteori.foodrecipesapp.presentation.ui.components.CommentTextField
import com.jagoteori.foodrecipesapp.presentation.ui.pages.detail_recipe.view_model.DetailRecipeViewModel
import com.jagoteori.foodrecipesapp.presentation.ui.theme.BlackColor500


@Composable
fun CommentsList(
    modifier: Modifier,
    recipeEntity: RecipeEntity,
    detailViewModel: DetailRecipeViewModel
) {

    LazyColumn(
        modifier = modifier
            .height(350.dp)
            .nestedScroll(object : NestedScrollConnection {}),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(bottom = 30.dp, start = 24.dp, end = 24.dp),
        content = {
            if (recipeEntity.listComments.isNullOrEmpty()) {
                item {
                    Column(
                        modifier = modifier.height(50.dp).fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Belum ada komentar",
                            color = BlackColor500,
                            style = TextStyle(fontSize = 16.sp)
                        )
                    }
                }
            } else {
                items(recipeEntity.listComments.size) { index ->
                    CommentItem(
                        modifier = modifier,
                        comments = recipeEntity.listComments[index]
                    )
                }
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
                            }
                        }
                    }
                )
            }
        })


}

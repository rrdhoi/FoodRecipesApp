package com.jagoteori.foodrecipesapp.presentation.ui.pages

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jagoteori.foodrecipesapp.R
import com.jagoteori.foodrecipesapp.presentation.add_recipe.AddRecipeViewModel
import com.jagoteori.foodrecipesapp.presentation.ui.components.CustomOutlineTextField
import com.jagoteori.foodrecipesapp.presentation.ui.theme.GreyColorTextInput

@Composable
fun AddRecipeScreen(modifier: Modifier, viewModel: AddRecipeViewModel) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .padding(top = 16.dp, start = 24.dp, end = 24.dp)
            .verticalScroll(scrollState)
    ) {
        Card(
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(width = 1.dp, color = GreyColorTextInput),
            modifier = modifier
                .fillMaxWidth()
                .height(300.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_baseline_add_photo_alternate_24),
                contentDescription = "Add Recipe Picture",
                modifier = modifier.padding(50.dp)
            )
        }

        CustomOutlineTextField(
            "Masukkan judul resep kamu",
            modifier = modifier,
            value = viewModel.title,
            errorMessage = viewModel.titleErrorMessage,
            isError = viewModel.titleError,
        ) { newValue ->
            viewModel.title = newValue
        }

        CustomOutlineTextField(
            "Masukkan kategori resep kamu",
            modifier = modifier,
            value = viewModel.category,
            errorMessage = viewModel.categoryErrorMessage,
            isError = viewModel.categoryError,
        ) { newValue ->
            viewModel.category = newValue
        }

        CustomOutlineTextField(
            "Masukkan deskripsi resep kamu",
            modifier = modifier,
            value = viewModel.description,
            errorMessage = viewModel.descriptionErrorMessage,
            isError = viewModel.descriptionError,
        ) { newValue ->
            viewModel.description = newValue
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddRecipeScreenPreview() {
    MaterialTheme() {
//        AddRecipeScreen(modifier = Modifier, viewModel = addRecipeViewModel)
    }
}
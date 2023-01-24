package com.jagoteori.foodrecipesapp.presentation.ui.pages

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.jagoteori.foodrecipesapp.R
import com.jagoteori.foodrecipesapp.presentation.add_recipe.AddRecipeViewModel
import com.jagoteori.foodrecipesapp.presentation.ui.components.CustomOutlineTextField
import com.jagoteori.foodrecipesapp.presentation.ui.pages.add_recipe.ListIngredientsForm
import com.jagoteori.foodrecipesapp.presentation.ui.pages.add_recipe.ListStepsCookForm
import com.jagoteori.foodrecipesapp.presentation.ui.theme.GreyColorTextInput


@Composable
fun AddRecipeScreen(
    modifier: Modifier,
    viewModel: AddRecipeViewModel,
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    var hasImage by remember { mutableStateOf(false) }
    var imageUri by remember { mutableStateOf<Uri?>(Uri.EMPTY) }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            hasImage = success
        }
    )

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
                .clickable {
                    val uri = ComposeFileProvider.getImageUri(context)
                    imageUri = uri

                    hasImage = false

                    cameraLauncher.launch(uri)

                }
        ) {
            Image(
                painter = if (hasImage and (imageUri != null)) rememberAsyncImagePainter(imageUri)
                else painterResource(
                    id = R.drawable.ic_baseline_add_photo_alternate_24
                ),
                contentScale = ContentScale.Crop,
                contentDescription = "Add Recipe Picture",
                modifier = modifier.fillMaxSize()
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

        ListIngredientsForm(modifier = modifier)
        ListStepsCookForm(modifier = modifier)
    }
}

@Preview(showBackground = true)
@Composable
fun AddRecipeScreenPreview() {
    MaterialTheme() {
//        AddRecipeScreen(modifier = Modifier, viewModel = addRecipeViewModel)
    }
}
package com.jagoteori.foodrecipesapp.presentation.ui.pages.add_recipe

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.jagoteori.foodrecipesapp.R
import com.jagoteori.foodrecipesapp.presentation.ui.components.CustomOutlineTextField
import com.jagoteori.foodrecipesapp.presentation.ui.components.ImagePickerDialog
import com.jagoteori.foodrecipesapp.presentation.ui.components.TopAppBarBlack
import com.jagoteori.foodrecipesapp.presentation.extention.noRippleClickable
import com.jagoteori.foodrecipesapp.presentation.ui.pages.add_recipe.components.ListIngredientsForm
import com.jagoteori.foodrecipesapp.presentation.ui.pages.add_recipe.components.ListStepsCookForm
import com.jagoteori.foodrecipesapp.presentation.ui.pages.add_recipe.view_model.AddRecipeViewModel
import com.jagoteori.foodrecipesapp.presentation.ui.theme.BlackColor500
import com.jagoteori.foodrecipesapp.presentation.ui.theme.GreyColor100
import com.jagoteori.foodrecipesapp.presentation.ui.theme.GreyColorTextInput
import com.jagoteori.foodrecipesapp.presentation.ui.theme.WhiteColor
import org.koin.androidx.compose.koinViewModel


@Composable
fun AddRecipeScreen(
    modifier: Modifier = Modifier,
    viewModel: AddRecipeViewModel = koinViewModel(),
    onBackPressed: () -> Unit,
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    var hasImage by remember { mutableStateOf(false) }
    var imageUri by remember { mutableStateOf<Uri?>(Uri.EMPTY) }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            hasImage = success
            viewModel.imageRecipe = imageUri.toString()
            viewModel.imagePickerDialogState = false
        }
    )

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            hasImage = uri != null
            imageUri = uri
            viewModel.imagePickerDialogState = false
        }
    )

    Scaffold(topBar = {
        TopAppBarBlack(
            title = "Tambar Resep",
            icon = painterResource(id = R.drawable.ic_arrow_back_white)
        ) {
            onBackPressed()
        }
    }) { paddingValues ->
        Box(contentAlignment = Alignment.Center, modifier = modifier.padding(paddingValues)) {
            Column(
                modifier = modifier
                    .padding(start = 24.dp, end = 24.dp)
                    .verticalScroll(scrollState)
            ) {
                Card(
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(width = 1.dp, color = GreyColorTextInput),
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                        .height(300.dp)
                        .noRippleClickable { viewModel.imagePickerDialogState = true }
                ) {
                    Image(
                        painter = if (hasImage and (imageUri != null)) rememberAsyncImagePainter(
                            imageUri
                        )
                        else painterResource(
                            id = R.drawable.ic_baseline_add_photo_alternate_24
                        ),
                        contentScale = ContentScale.Crop,
                        contentDescription = "Add Recipe Picture",
                        modifier = modifier
                            .fillMaxSize()
                            .padding(if (hasImage and (imageUri != null)) 0.dp else 24.dp)
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

                Text(
                    "Bahan-Bahan", modifier = modifier.padding(top = 24.dp), style = TextStyle(
                        color = BlackColor500,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                )

                ListIngredientsForm(
                    modifier = modifier,
                    listSize = viewModel.listIngredientFormSize,
                    listItemForm = remember { viewModel.listItemIngredientForm },
                    onAddIngredientClick = {
                        viewModel.listIngredientFormSize++
                    },
                )

                Text(
                    "Langkah memasak", modifier = modifier.padding(top = 24.dp), style = TextStyle(
                        color = BlackColor500,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                )

                ListStepsCookForm(
                    modifier = modifier,
                    formSize = viewModel.listStepCookFormSize,
                    listItemForm = remember { viewModel.listItemStepCookForm },
                    onAddStepCookForm = {
                        viewModel.listStepCookFormSize++
                    }
                )

                Button(
                    modifier = modifier
                        .padding(top = 30.dp, bottom = 30.dp)
                        .height(54.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = BlackColor500,
                        contentColor = WhiteColor
                    ),
                    onClick = { viewModel.onSubmitRecipe() }
                ) {

                    Text(
                        text = "Submit step cook", style = TextStyle(
                            fontSize = 16.sp
                        )
                    )
                }

            }

            if (viewModel.isLoading) {
                Surface(
                    modifier = modifier
                        .fillMaxSize()
                        .alpha(0.3f),
                    color = GreyColor100,
                ) {}

                CircularProgressIndicator(color = BlackColor500)
            }

            ImagePickerDialog(
                modifier = modifier,
                dialogState = viewModel.imagePickerDialogState,
                context = context,
                cameraLauncher = cameraLauncher,
                imagePicker = imagePicker,
                onGetImage = { uri ->
                    imageUri = uri
                    hasImage = false
                },
                onDismissRequest = {
                    viewModel.imagePickerDialogState = false
                })
        }
    }
}

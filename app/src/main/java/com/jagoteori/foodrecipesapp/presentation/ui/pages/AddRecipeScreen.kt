package com.jagoteori.foodrecipesapp.presentation.ui.pages

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jagoteori.foodrecipesapp.R
import com.jagoteori.foodrecipesapp.domain.entity.IngredientEntity
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

        ListIngredientsForm(modifier = modifier)
    }
}


@Composable
fun ListIngredientsForm(modifier: Modifier) {
    val listSize = remember { mutableStateOf(1) }
    val listIngredientItem = remember { mutableStateListOf<List<MutableState<TextFieldValue>>>() }

    listIngredientItem.clear()

    repeat(listSize.value) {
        val listItem = remember { mutableStateListOf<MutableState<TextFieldValue>>() }

        RowItemIngredient(modifier) { ingredient, quantity, typeQuantity ->
            listItem.add(ingredient)
            listItem.add(quantity)
            listItem.add(typeQuantity)
        }

        listIngredientItem.add(listItem)
    }

    Button(onClick = {
        listSize.value++
    }) {
        Text(text = "Tambah form")
    }

    Button(onClick = {
        val listIngredient = mutableListOf<IngredientEntity>()
        listIngredientItem.forEach { listItem ->
            val ingredientEntity = IngredientEntity(
                ingredient = listItem[0].value.text,
                quantity = listItem[1].value.text,
                typeQuantity = listItem[2].value.text,
            )

            listIngredient.add(ingredientEntity)
        }
        Log.d("isi listnya", "size: ${listIngredient.size}, data: ${listIngredient}")
    }) {
        Text(text = "Submit")
    }
}

@Composable
fun RowItemIngredient(
    modifier: Modifier,
    onAddToList: (ingredient: MutableState<TextFieldValue>, quantity: MutableState<TextFieldValue>, typeQuantity: MutableState<TextFieldValue>) -> Unit
) {
    val ingredient = remember { mutableStateOf(TextFieldValue("")) }
    val quantity = remember { mutableStateOf(TextFieldValue("")) }
    val typeQuantity = remember { mutableStateOf(TextFieldValue("")) }
//        var typeQuantity by mutableStateOf("")

    Column() {
        CustomOutlineTextField(
            "Masukkan bahan kamu",
            modifier = modifier,
            value = ingredient.value,
            errorMessage = "",
            isError = false,
        ) { newValue ->
            ingredient.value = newValue
        }

        Row {
            OutlinedTextField(
                value = quantity.value,
                onValueChange = { quantity.value = it },
                modifier = modifier.weight(1f)
            )

            OutlinedTextField(
                value = typeQuantity.value,
                onValueChange = { typeQuantity.value = it },
                modifier = modifier.weight(1f)
            )
        }
    }

    onAddToList(ingredient, quantity, typeQuantity)
}

@Preview(showBackground = true)
@Composable
fun AddRecipeScreenPreview() {
    MaterialTheme() {
//        AddRecipeScreen(modifier = Modifier, viewModel = addRecipeViewModel)
    }
}
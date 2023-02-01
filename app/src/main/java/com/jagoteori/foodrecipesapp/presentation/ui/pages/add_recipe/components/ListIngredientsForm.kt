package com.jagoteori.foodrecipesapp.presentation.ui.pages.add_recipe.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jagoteori.foodrecipesapp.R
import com.jagoteori.foodrecipesapp.presentation.ui.components.HintPlaceholder
import com.jagoteori.foodrecipesapp.presentation.ui.components.outlineTextFieldColor
import com.jagoteori.foodrecipesapp.presentation.extention.noRippleClickable
import com.jagoteori.foodrecipesapp.presentation.ui.theme.BackgroundColor
import com.jagoteori.foodrecipesapp.presentation.ui.theme.BlackColor500
import com.jagoteori.foodrecipesapp.presentation.ui.theme.GreyColorTextInput
import com.jagoteori.foodrecipesapp.presentation.ui.theme.WhiteColor

data class ItemIngredient(
    var ingredient: MutableState<TextFieldValue>?,
    var quantity: MutableState<TextFieldValue>?,
    var typeQuantity: MutableState<String>?
)

@Composable
fun ListIngredientsForm(
    modifier: Modifier,
    listSize: Int,
    listItemForm : SnapshotStateList<MutableState<ItemIngredient>>,
    onAddIngredientClick: () -> Unit,
) {
    listItemForm.clear()

    repeat(listSize) {
        val itemIngredient = remember {
            mutableStateOf(
                ItemIngredient(
                    ingredient = null,
                    quantity = null,
                    typeQuantity = null
                )
            )
        }

        RowItemIngredient(modifier) { ingredient, quantity, typeQuantity ->
            itemIngredient.value.ingredient = ingredient
            itemIngredient.value.quantity = quantity
            itemIngredient.value.typeQuantity = typeQuantity
        }

        listItemForm.add(itemIngredient)
    }

    Button(
        modifier = modifier
            .padding(top = 16.dp, bottom = 16.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = BlackColor500,
            contentColor = WhiteColor
        ), onClick = onAddIngredientClick
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_add_recipe),
            contentDescription = "Icon Add Ingredient",
            modifier = modifier.padding(end = 8.dp)
        )
        Text(text = "Tambah bahan untuk resep")
    }
}

@Composable
fun RowItemIngredient(
    modifier: Modifier,
    onAddToList: (ingredient: MutableState<TextFieldValue>, quantity: MutableState<TextFieldValue>, typeQuantity: MutableState<String>) -> Unit
) {
    val ingredient = remember { mutableStateOf(TextFieldValue("")) }
    val quantity = remember { mutableStateOf(TextFieldValue("")) }
    val typeQuantity = remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .padding(top = 16.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(BackgroundColor)
    ) {

        OutlinedTextField(
            value = ingredient.value,
            onValueChange = { newValue ->
                ingredient.value = newValue
            },
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 12.dp, start = 12.dp, end = 12.dp),
            shape = RoundedCornerShape(size = 12.dp),
            textStyle = TextStyle(color = BlackColor500, fontSize = 16.sp),
            colors = outlineTextFieldColor(),
            placeholder = { HintPlaceholder(hint = "Masukkan bahan kamu") }
        )

        RowQuantity(modifier = modifier, quantity = quantity) { _typeQuantity ->
            typeQuantity.value = _typeQuantity
        }
    }

    onAddToList(ingredient, quantity, typeQuantity)
}

@Composable
fun RowQuantity(
    modifier: Modifier,
    quantity: MutableState<TextFieldValue>,
    typeQuantity: (typeQuantity: String) -> Unit
) {
    val items = stringArrayResource(id = R.array.list_quantity)
    var expanded by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf(0) }

    typeQuantity(items[selectedIndex])

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        Box(modifier = modifier.weight(1f)) {
            OutlinedTextField(
                value = quantity.value,
                onValueChange = { newValue ->
                    quantity.value = newValue
                },
                modifier = modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(size = 12.dp),
                textStyle = TextStyle(color = BlackColor500, fontSize = 16.sp),
                colors = outlineTextFieldColor(),
                placeholder = { HintPlaceholder(hint = "Kuantitas") }
            )
        }

        Box(contentAlignment = Alignment.CenterStart,
            modifier = modifier
                .padding(start = 16.dp)
                .weight(1f)
                .height(56.dp)
                .border(
                    border = BorderStroke(width = 1.dp, color = GreyColorTextInput),
                    shape = RoundedCornerShape(12.dp)
                )
                .noRippleClickable { expanded = true }

        ) {
            Text(
                items[selectedIndex],
                color = BlackColor500,
                overflow = TextOverflow.Ellipsis,
                style = TextStyle(fontSize = 16.sp),
                modifier = Modifier
                    .padding(start = 14.dp, end = 24.dp),
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_arrow_drop_down_24),
                contentDescription = "Icon Drop Down",
                modifier = modifier
                    .padding(end = 8.dp)
                    .size(18.dp)
                    .align(Alignment.CenterEnd)

            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                items.forEachIndexed { index, typeQuantity ->
                    DropdownMenuItem(onClick = {
                        selectedIndex = index
                        expanded = false
                    }) {
                        Text(
                            typeQuantity,
                            color = BlackColor500,
                            style = TextStyle(fontSize = 16.sp),
                        )
                    }
                }
            }
        }
    }
}

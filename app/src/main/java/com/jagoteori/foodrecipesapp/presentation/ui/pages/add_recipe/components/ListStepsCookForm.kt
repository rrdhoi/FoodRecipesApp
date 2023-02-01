package com.jagoteori.foodrecipesapp.presentation.ui.pages.add_recipe.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.jagoteori.foodrecipesapp.R
import com.jagoteori.foodrecipesapp.presentation.extention.noRippleClickable
import com.jagoteori.foodrecipesapp.presentation.ui.components.HintPlaceholder
import com.jagoteori.foodrecipesapp.presentation.ui.components.outlineTextFieldColor
import com.jagoteori.foodrecipesapp.presentation.ui.theme.BackgroundColor
import com.jagoteori.foodrecipesapp.presentation.ui.theme.BlackColor500
import com.jagoteori.foodrecipesapp.presentation.ui.theme.GreyColor100
import com.jagoteori.foodrecipesapp.presentation.ui.theme.WhiteColor

data class ItemStepCook(
    var description: MutableState<TextFieldValue>?,
    var listImageUri: SnapshotStateList<Uri?>?,
)

@Composable
fun ListStepsCookForm(
    modifier: Modifier,
    formSize: Int,
    listItemForm: SnapshotStateList<MutableState<ItemStepCook>>,
    onAddStepCookForm: () -> Unit
) {

    listItemForm.clear()

    repeat(formSize) {
        val itemStepCook =
            remember { mutableStateOf(ItemStepCook(description = null, listImageUri = null)) }

        RowItemStepCook(modifier) { description, listImageUri ->
            itemStepCook.value.description = description
            itemStepCook.value.listImageUri = listImageUri
        }

        listItemForm.add(itemStepCook)
    }

    Button(
        modifier = modifier
            .padding(top = 16.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = BlackColor500,
            contentColor = WhiteColor
        ),
        onClick = onAddStepCookForm
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_add_recipe),
            contentDescription = "Icon Add Ingredient",
            modifier = modifier.padding(end = 8.dp)
        )
        Text(text = "Tambah langkah memasak")
    }
}


@Composable
fun RowItemStepCook(
    modifier: Modifier,
    onAddToList: (
        description: MutableState<TextFieldValue>,
        listImageUri: SnapshotStateList<Uri?>,
    ) -> Unit
) {
    val description = remember { mutableStateOf(TextFieldValue("")) }

    val listImagesUri = remember { mutableStateListOf<Uri?>() }

    var imagesSize by remember { mutableStateOf(0) }

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            if (uri != null) {
                when (imagesSize) {
                    0 -> listImagesUri.add(0, uri)
                    1 -> listImagesUri.add(1, uri)
                    2 -> listImagesUri.add(2, uri)
                }

                imagesSize++
            }
        }
    )

    Column(
        modifier = modifier
            .padding(top = 16.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(BackgroundColor)
    ) {
        OutlinedTextField(
            value = description.value,
            onValueChange = { newValue ->
                description.value = newValue
            },
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 12.dp, start = 12.dp, end = 12.dp),
            shape = RoundedCornerShape(size = 12.dp),
            textStyle = TextStyle(color = BlackColor500, fontSize = 16.sp),
            colors = outlineTextFieldColor(),
            placeholder = { HintPlaceholder(hint = "Masukkan deskripsi langkah memasak kamu") }
        )

        Row {
            if (imagesSize != 0) {
                repeat(imagesSize) { index ->
                    AsyncImage(
                        model = listImagesUri[index],
                        contentDescription = "Image Step Cook",
                        contentScale = ContentScale.Crop,
                        modifier = modifier
                            .padding(12.dp)
                            .width(80.dp)
                            .height(80.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .fillMaxSize()

                    )
                }
            }

            if (imagesSize <= 2) {
                Card(
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(width = 1.dp, color = GreyColor100),
                    modifier = modifier
                        .width(100.dp)
                        .height(100.dp)
                        .padding(12.dp)
                        .background(color = WhiteColor)
                        .noRippleClickable {
                            imagePicker.launch("image/*")
                        }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_baseline_photo_camera_24),
                        contentScale = ContentScale.Crop,
                        contentDescription = "Add Recipe Picture",
                        modifier = modifier
                            .padding(14.dp)
                            .fillMaxSize()
                    )
                }
            }
        }
    }

    onAddToList(description, listImagesUri)
}

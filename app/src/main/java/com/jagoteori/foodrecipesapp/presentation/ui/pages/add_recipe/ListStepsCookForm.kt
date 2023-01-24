package com.jagoteori.foodrecipesapp.presentation.ui.pages.add_recipe

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.jagoteori.foodrecipesapp.domain.entity.StepCookEntity
import com.jagoteori.foodrecipesapp.presentation.ui.components.CustomOutlineTextField
import com.jagoteori.foodrecipesapp.presentation.ui.pages.ComposeFileProvider
import com.jagoteori.foodrecipesapp.presentation.ui.theme.GreyColorTextInput

data class ItemStepCook(
    var description: MutableState<TextFieldValue>?,
    var listImageUri: SnapshotStateList<Uri?>?,
)

@Composable
fun ListStepsCookForm(modifier: Modifier) {
    val listSize = remember { mutableStateOf(1) }
    val listStepCookItem = remember { mutableStateListOf<MutableState<ItemStepCook>>() }

    listStepCookItem.clear()

    repeat(listSize.value) {
        val listItem =
            remember { mutableStateOf(ItemStepCook(description = null, listImageUri = null)) }

        RowItemStepCook(modifier) { description, listImageUri ->
            listItem.value.description = description
            listItem.value.listImageUri = listImageUri
        }

        listStepCookItem.add(listItem)
    }

    Button(onClick = {
        listSize.value++
    }) {
        Text(text = "Tambah form")
    }

    Button(onClick = {
        val listStepCook = mutableListOf<StepCookEntity>()
        listStepCookItem.forEachIndexed { index, listItem ->
            val itemStepCook = StepCookEntity(
                stepNumber = index + 1,
                stepDescription = listItem.value.description?.value?.text,
                stepImages = listItem.value.listImageUri?.map { uri: Uri? -> uri.toString() }
                    ?.toList()
            )

            listStepCook.add(itemStepCook)
        }
        Log.d("step cook::", "size: ${listStepCook.size}, data: ${listStepCook}")
    }) {
        Text(text = "Submit step cook")
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
    val localContext = LocalContext.current
    val description = remember { mutableStateOf(TextFieldValue("")) }

    val listImagesUri = remember { mutableStateListOf<Uri?>() }

    var imagesSize by remember { mutableStateOf(0) }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            if(success) {
                imagesSize++
            }
        }
    )

    Column() {
        CustomOutlineTextField(
            "Masukkan deskripsi langkah memasak kamu",
            modifier = modifier,
            value = description.value,
            errorMessage = "",
            isError = false,
        ) { newValue ->
            description.value = newValue
        }

        Row {
            if (imagesSize != 0) {
                repeat(imagesSize) { index ->
                    AsyncImage(
                        model = listImagesUri[index],
                        contentDescription = "Image Step Cook",
                        contentScale = ContentScale.Crop,
                        modifier = modifier
                            .clip(RoundedCornerShape(12.dp))
                            .width(80.dp)
                            .height(80.dp)

                    )
                }
            }

            if (imagesSize <= 2) {
                Card(
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(width = 1.dp, color = GreyColorTextInput),
                    modifier = modifier
                        .width(80.dp)
                        .height(80.dp)
                        .clickable {
                            val uri = ComposeFileProvider.getImageUri(localContext)

                            when (imagesSize) {
                                0 -> listImagesUri.add(0, uri)
                                1 -> listImagesUri.add(1, uri)
                                2 -> listImagesUri.add(2, uri)
                            }

                            cameraLauncher.launch(uri)
                        }
                ) {
                    Image(
                        painter = painterResource(id = com.jagoteori.foodrecipesapp.R.drawable.ic_baseline_add_photo_alternate_24),
                        contentScale = ContentScale.Crop,
                        contentDescription = "Add Recipe Picture",
                        modifier = modifier.fillMaxSize()
                    )
                }
            }
        }
    }

    onAddToList(description, listImagesUri)
}

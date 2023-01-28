package com.jagoteori.foodrecipesapp.presentation.ui.components

import android.content.Context
import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.jagoteori.foodrecipesapp.R
import com.jagoteori.foodrecipesapp.app.service.ComposeFileProvider
import com.jagoteori.foodrecipesapp.presentation.ui.extention.noRippleClickable
import com.jagoteori.foodrecipesapp.presentation.ui.theme.GreyColor300


@Composable
fun ImagePickerDialog(
    modifier: Modifier,
    context: Context,
    cameraLauncher: ManagedActivityResultLauncher<Uri, Boolean>,
    imagePicker: ManagedActivityResultLauncher<String, Uri?>,
    onGetImage: (imageUri: Uri?) -> Unit,
    dialogState: Boolean,
    onDismissRequest: () -> Unit
) {
    if (dialogState) {
        Dialog(
            onDismissRequest = onDismissRequest,
            content = {
                Card(
                    elevation = 8.dp,
                    modifier = modifier
                        .width(300.dp)
                        .height(180.dp),

                    shape = RoundedCornerShape(18.dp)
                ) {
                    Column(
                        modifier = modifier
                            .fillMaxSize()
                            .padding(horizontal = 24.dp),
                    ) {
                        Text(
                            text = "Pilih gambar",
                            fontSize = 18.sp,
                            textAlign = TextAlign.Start,
                            modifier = modifier.padding(top = 16.dp)
                        )
                        Spacer(modifier = modifier.height(12.dp))
                        Row(
                            modifier = modifier
                                .fillMaxSize(), verticalAlignment = Alignment.CenterVertically
                        ) {
                            Card(
                                modifier = modifier
                                    .weight(1f)
                                    .height(100.dp)
                                    .noRippleClickable {
                                        imagePicker.launch("image/*")
                                    },
                                border = BorderStroke(1.dp, color = GreyColor300),
                                shape = RoundedCornerShape(12.dp),
                            ) {
                                Image(
                                    modifier = modifier.padding(12.dp),
                                    painter = painterResource(id = R.drawable.ic_baseline_add_photo_alternate_24),
                                    contentDescription = "Add Photo"
                                )
                            }
                            Spacer(modifier = modifier.padding(start = 16.dp))
                            Card(
                                modifier = modifier
                                    .weight(1f)
                                    .height(100.dp)
                                    .noRippleClickable {
                                        val uri = ComposeFileProvider.getImageUri(context)
                                        cameraLauncher.launch(uri)
                                        onGetImage(uri)
                                    },
                                border = BorderStroke(1.dp, color = GreyColor300),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Image(
                                    modifier = modifier.padding(12.dp),
                                    painter = painterResource(id = R.drawable.ic_baseline_photo_camera_24),
                                    contentDescription = "Add Photo"
                                )
                            }
                        }
                    }
                }
            },
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false
            )
        )
    }
}
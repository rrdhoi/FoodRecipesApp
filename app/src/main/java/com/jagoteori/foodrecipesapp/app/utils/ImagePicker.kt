package com.jagoteori.foodrecipesapp.app.utils

import android.app.Activity
import android.util.Size
import com.github.dhaval2404.imagepicker.ImagePicker
import kotlinx.coroutines.NonCancellable.isCancelled

fun imagePicker(
    activity: Activity,
    requestCode: Int,
    compress: Int = 1024,
    size: Size = Size(620, 620),
    isCameraOnly: Boolean = false,
    isGalleryOnly: Boolean = false
): Boolean {
    var isCaptured = true
    val imagePicker = ImagePicker.with(activity)
        .compress(compress)
        .maxResultSize(size.width, size.height)

    if (isCameraOnly) imagePicker.cameraOnly()
    if (isGalleryOnly) imagePicker.galleryOnly()

    imagePicker
        .setDismissListener {
            isCaptured = false
        }
        .start(requestCode)

    return isCaptured
}
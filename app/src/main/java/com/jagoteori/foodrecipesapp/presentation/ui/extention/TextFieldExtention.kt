package com.jagoteori.foodrecipesapp.presentation.ui.extention

import androidx.compose.ui.text.input.TextFieldValue
import java.util.*


fun TextFieldValue.isEmptyOrBlank(
    title: String,
    validateHandler: (errorMessage: String) -> Unit
): Boolean =
    if (this.text.isBlank()) {
        validateHandler("Masukkan $title kamu")
        true
    } else false

fun TextFieldValue.isLessThan6(
    title: String,
    validateHandler: (errorMessage: String) -> Unit
): Boolean =
    if (this.text.length <= 5) {
        validateHandler("${title.capitalize(Locale.ROOT)} kurang dari 6")
        true
    } else {
        false
    }

fun TextFieldValue.isInvalidEmailFormat(validateHandler: (errorMessage: String) -> Unit): Boolean =
    if (!android.util.Patterns.EMAIL_ADDRESS.matcher(this.text).matches()) {
        validateHandler("Masukkan email valid")
        true
    } else false
package com.jagoteori.foodrecipesapp.app.extention

import android.widget.EditText
import androidx.compose.ui.text.input.TextFieldValue
import java.util.*

fun EditText.isNotNullOrEmpty(errorString: String): Boolean {
    return if (this.text.toString().trim().isBlank()) {
        this.error = errorString
        false
    } else {
        true
    }
}

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

fun Boolean.errorHandler(
    whenIsError: () -> Unit,
): Boolean = if (this) {
    whenIsError()
    true
} else {
    false
}

fun Boolean.isNotErrorHandler(
    whenIsNotError: () -> Unit,
): Boolean = if (!this) {
    whenIsNotError()
    false
} else {
    true
}


fun EditText.isNotNullOrEmpty(): Boolean {
    return this.text.toString().trim().isNotBlank()
}

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

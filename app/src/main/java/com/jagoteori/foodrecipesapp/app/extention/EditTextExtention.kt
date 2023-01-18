package com.jagoteori.foodrecipesapp.app.extention

import android.widget.EditText

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

fun Boolean.successHandler(
    whenIsSuccess: () -> Unit,
): Boolean = if (!this) {
    whenIsSuccess()
    false
} else {
    true
}

fun EditText.isEmailFormat(errorString: String): Boolean {
    return if (!this.text.toString().contains("@")) {
        this.error = errorString
        false
    } else {
        true
    }
}

fun EditText.isPasswordEquals(secondPassword: EditText, errorString: String): Boolean {
    return if (this.text.toString() != secondPassword.text.toString()) {
        this.error = errorString
        secondPassword.error = errorString
        false
    } else {
        true
    }
}

fun EditText.isNotNullOrEmpty(): Boolean {
    return this.text.toString().trim().isNotBlank()
}

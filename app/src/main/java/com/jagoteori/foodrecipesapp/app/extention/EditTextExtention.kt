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

fun EditText.isNotNullOrEmpty(): Boolean {
    return this.text.toString().trim().isNotBlank()
}

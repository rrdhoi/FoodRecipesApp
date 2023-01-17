package com.jagoteori.foodrecipesapp.app.utils

import android.content.Context
import android.widget.Toast

fun firebaseAuthHandler(context: Context?, message: String?) {
    when (message) {
        "The password is invalid or the user does not have a password." -> {
            Toast.makeText(context, "Masukkan password dengan benar!", Toast.LENGTH_LONG).show()
        }
        "The email address is badly formatted." -> {
            Toast.makeText(context, "Masukkan email dengan benar!", Toast.LENGTH_LONG).show()
        }
    }
}
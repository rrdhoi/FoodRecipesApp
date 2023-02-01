package com.jagoteori.foodrecipesapp.presentation.extention

import android.os.Bundle
import androidx.navigation.NavType
import com.google.gson.Gson

fun <A> A.toJson(): String {
    return Gson().toJson(this).toString()
        .replace("/", "$$$").replace(".a", "$$$$")
}

fun <A> String.fromJson(type: Class<A>): A {
    return Gson().fromJson(this.replace("\\", "$$$$").replace("$$$", "/"), type)
}

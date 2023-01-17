package com.jagoteori.foodrecipesapp.app.extention

import java.text.DecimalFormat

fun Int.length() = when (this) {
    0 -> 1
    else -> kotlin.math.log10(kotlin.math.abs(toDouble())).toInt() + 1
}

fun Int.twoDigitsFormat(): String = DecimalFormat("00").format(this)
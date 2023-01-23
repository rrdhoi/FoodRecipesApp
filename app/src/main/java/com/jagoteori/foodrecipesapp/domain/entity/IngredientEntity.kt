package com.jagoteori.foodrecipesapp.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class IngredientEntity(
    var ingredient: String?,
    var quantity: String?,
    var typeQuantity: String?
) : Parcelable
package com.jagoteori.foodrecipesapp.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class IngredientEntity(
    val ingredient: String?,
    val quantity: String?,
    val typeQuantity: String?
) : Parcelable
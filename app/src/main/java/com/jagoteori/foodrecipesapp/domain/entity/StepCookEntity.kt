package com.jagoteori.foodrecipesapp.domain.entity
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class StepCookEntity(
    val stepDescription: String?,
    val stepNumber: Int?,
    val stepImages: List<String>?
): Parcelable

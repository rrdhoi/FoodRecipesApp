package com.jagoteori.foodrecipesapp.domain.entity

import android.net.Uri

data class StepCookEntity(
    val stepDescription: String,
    val stepNumber: Int,
    val stepImages: List<Uri>
)

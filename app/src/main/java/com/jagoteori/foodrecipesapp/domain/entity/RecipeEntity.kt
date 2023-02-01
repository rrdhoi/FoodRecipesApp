package com.jagoteori.foodrecipesapp.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RecipeEntity(
    val id: String? = null,
    val title: String? = null,
    val category: String? = null,
    val description: String? = null,
    val publisherId: String? = null,
    val publisher: String? = null,
    val recipePicture: String? = null,
    val listIngredients: List<IngredientEntity>? = null,
    val listStepCooking: List<StepCookEntity>? = null,
    val listComments : List<CommentEntity>? = null
) : Parcelable
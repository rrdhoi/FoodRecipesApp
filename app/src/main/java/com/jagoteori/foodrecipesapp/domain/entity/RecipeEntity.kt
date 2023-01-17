package com.jagoteori.foodrecipesapp.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RecipeEntity(
    val id: String?,
    val title: String?,
    val category: String?,
    val description: String?,
    val publisherId: String?,
    val publisher: String?,
    val recipePicture: String?,
    val listIngredients: List<IngredientEntity>?,
    val listStepCooking: List<StepCookEntity>?,
    val listComments : List<CommentEntity>?
) : Parcelable
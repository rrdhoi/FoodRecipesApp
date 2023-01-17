package com.jagoteori.foodrecipesapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CommentModel(
    val id: String? = null,
    val name: String? = null,
    val message: String? = null,
    val profilePicture: String? = null,
)  : Parcelable
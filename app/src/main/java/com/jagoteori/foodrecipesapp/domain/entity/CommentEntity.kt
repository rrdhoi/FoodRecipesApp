package com.jagoteori.foodrecipesapp.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CommentEntity(
    val id: String?,
    val name: String?,
    val message: String?,
    val profilePicture: String?,
)  : Parcelable
package com.jagoteori.foodrecipesapp.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserEntity(
    val userId : String?,
    val name: String?,
    val email: String?,
    val profilePicture: String?,
) : Parcelable

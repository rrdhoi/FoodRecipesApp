package com.jagoteori.foodrecipesapp.presentation.add_recipe

import android.app.Activity
import android.view.ViewGroup
import android.widget.ImageView
import com.jagoteori.foodrecipesapp.app.utils.imagePicker

fun imageViewGenerated(activity: Activity, id: Int) : ImageView {
    val imageView = ImageView(activity)
    val imageParams = ViewGroup.LayoutParams(
        ViewGroup.LayoutParams.WRAP_CONTENT,
        ViewGroup.LayoutParams.MATCH_PARENT
    )

    imageView.tag = id
    imageView.setImageResource(com.github.dhaval2404.imagepicker.R.drawable.ic_photo_camera_black_48dp)
    imageView.layoutParams = imageParams

    imageView.setOnClickListener {
        imagePicker(activity, id)
    }

    return imageView
}
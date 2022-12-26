package com.jagoteori.foodrecipesapp.presentation.add_recipe

import android.app.Activity
import android.net.Uri
import android.util.Log
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.google.android.flexbox.FlexboxLayout
import com.jagoteori.foodrecipesapp.R
import com.jagoteori.foodrecipesapp.app.utils.imagePicker

object StepCookFunction {
    fun stepImageViewGenerated(activity: Activity, id: Int) : ImageView {
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

    fun addingImageIntoStepCookView(activity: Activity, parentView: LinearLayout, requestCode: Int, uri: Uri) {
        for (indexParentView in 0 until parentView.childCount) {
            val rowAddStepCookingView = parentView.getChildAt(indexParentView)
            val flexibleListStepCook = rowAddStepCookingView.findViewById<FlexboxLayout>(R.id.list_image_step_cook)

            for (indexFlexibleView in 0 until flexibleListStepCook.childCount) {
                val concatTwoInt = "${indexParentView+1}${indexFlexibleView+1}".toInt()
                Log.d("addrecipeActivity", "indexParent(${indexParentView+1}) & indexFlexible(${indexFlexibleView+1})")
                if (requestCode == concatTwoInt) {
                    val getChildFlexible = flexibleListStepCook.getChildAt(indexFlexibleView+1)
                    Log.d("on Activity Result:", "concat : $concatTwoInt || index flexiblenya :$indexFlexibleView ||| view Tag: ${getChildFlexible.tag}")
                    val imageView = getChildFlexible.findViewWithTag<ImageView>(concatTwoInt)

                    Log.d("on Activity Result:", "imageView : ${imageView.tag}")
                    Glide.with(activity)
                        .asBitmap()
                        .load(uri)
                        .into(imageView)
                }
            }
        }
    }
}
package com.jagoteori.foodrecipesapp.presentation.add_recipe

import android.annotation.SuppressLint
import android.app.Activity
import android.net.Uri
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import androidx.core.view.get
import com.bumptech.glide.Glide
import com.google.android.flexbox.FlexboxLayout
import com.jagoteori.foodrecipesapp.R
import com.jagoteori.foodrecipesapp.app.Constants
import com.jagoteori.foodrecipesapp.app.extention.margin
import com.jagoteori.foodrecipesapp.app.extention.twoDigitsFormat

object StepCookFunction {
    private var setListStepCookImages = ArrayList<Map<String, String>>()
    val listStepCookImages get() = setListStepCookImages

    fun onActivityCreate() {
        setListStepCookImages.clear()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun stepImageViewGenerated(activity: Activity, id: Int): ImageView {
        val imageView = ImageView(activity)
        imageView.layoutParams =
            LinearLayout.LayoutParams(180, 180)

        imageView.margin(46, 32,0,24)
        imageView.background = activity.resources.getDrawable(R.drawable.rounded_image_step_cook)
        imageView.clipToOutline = true
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        imageView.id = id

        imageView.setOnClickListener {}

        return imageView
    }

    fun addingImageIntoStepCookView(
        activity: Activity,
        parentView: LinearLayout,
        requestCode: Int,
        uri: Uri
    ) {
        val newImageView =
            stepImageViewGenerated(activity, requestCode)

        val getIndexRowAddStep = requestCode.toString().substring(3, 5)

        for (indexRowAddStep in 0 until parentView.childCount) {

            val rowAddStepCookingView = parentView.getChildAt(indexRowAddStep)
            val flexibleListStepCook =
                rowAddStepCookingView.findViewById<FlexboxLayout>(R.id.list_image_step_cook)

            val btnAddImageStepCook =
                flexibleListStepCook.findViewById<CardView>(R.id.card_add_image_step_cook)

            if (getIndexRowAddStep == indexRowAddStep.twoDigitsFormat()) {
                flexibleListStepCook.addView(newImageView, flexibleListStepCook.childCount - 1)
                Log.e("count", "${flexibleListStepCook.childCount}")
            }


            for (indexFlexibleView in 0 until flexibleListStepCook.childCount) {
                val getIdStepImageView =
                    "${Constants.REQUEST_CODE_ADD_STEP_COOK}${indexRowAddStep.twoDigitsFormat()}${indexFlexibleView}".toInt()

                if (requestCode == getIdStepImageView) {
                    val getChildFlexible = flexibleListStepCook.getChildAt(indexFlexibleView - 1)
                    val imageView = getChildFlexible.findViewById<ImageView>(getIdStepImageView)

                    Glide.with(activity)
                        .asBitmap()
                        .load(uri)
                        .into(imageView)

                    if (flexibleListStepCook.childCount == 4) {
                        flexibleListStepCook.removeView(btnAddImageStepCook)
                        Log.e("count after", "${flexibleListStepCook.childCount}")
                    }

                    setListStepCookImages.add(
                        mapOf(
                            "id" to getIdStepImageView.toString(),
                            "uri" to uri.toString()
                        )
                    )
                }
            }
        }
    }
}
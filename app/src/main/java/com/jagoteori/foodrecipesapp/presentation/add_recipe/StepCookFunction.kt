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
import com.jagoteori.foodrecipesapp.app.Constants
import com.jagoteori.foodrecipesapp.app.extention.twoDigitsFormat

object StepCookFunction {
    private var setListStepCookImages = ArrayList<Map<String, String>>()
    val listStepCookImages get() = setListStepCookImages

    fun onActivityCreate() {
        setListStepCookImages.clear()
    }

    private fun stepImageViewGenerated(activity: Activity, id: Int): ImageView {
        val imageView = ImageView(activity)
        val imageParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        imageView.id = id
        imageView.setImageResource(com.github.dhaval2404.imagepicker.R.drawable.ic_photo_camera_black_48dp)
        imageView.layoutParams = imageParams

        imageView.setOnClickListener {

        }

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

        Log.d(
            "Adding Image Step",
            "Uri : $uri"
        )

        val getIndexRowAddStep = requestCode.toString().substring(3, 5)

        for (indexRowAddStep in 0 until parentView.childCount) {

            val rowAddStepCookingView = parentView.getChildAt(indexRowAddStep)
            val flexibleListStepCook =
                rowAddStepCookingView.findViewById<FlexboxLayout>(R.id.list_image_step_cook)

            Log.d(
                "Fixing Image nih boss",
                "addIndexRowAddStep: $getIndexRowAddStep || indexRowAddStep: $indexRowAddStep"
            )
            if (getIndexRowAddStep == indexRowAddStep.twoDigitsFormat()) {
                flexibleListStepCook.addView(newImageView, flexibleListStepCook.childCount - 1)
            }

            Log.d(
                "btn add step",
                "concat : ${requestCode}|| isi flexiblenya : ${flexibleListStepCook.childCount} || index parentView : $indexRowAddStep"
            )

            for (indexFlexibleView in 0 until flexibleListStepCook.childCount) {
                val getIdStepImageView =
                    "${Constants.REQUEST_CODE_ADD_STEP_COOK}${indexRowAddStep.twoDigitsFormat()}${indexFlexibleView}".toInt()
                Log.d(
                    "addrecipeActivity",
                    "indexParent(${indexRowAddStep}) & indexFlexible(${indexFlexibleView})"
                )
                if (requestCode == getIdStepImageView) {
                    val getChildFlexible = flexibleListStepCook.getChildAt(indexFlexibleView - 1)
                    Log.d(
                        "on Activity Result:",
                        "concat : $getIdStepImageView && requestCode: $requestCode"
                    )
                    val imageView = getChildFlexible.findViewById<ImageView>(getIdStepImageView)

                    Log.d("on Activity Result:", "imageView : ${imageView.id}")
                    Glide.with(activity)
                        .asBitmap()
                        .load(uri)
                        .into(imageView)

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
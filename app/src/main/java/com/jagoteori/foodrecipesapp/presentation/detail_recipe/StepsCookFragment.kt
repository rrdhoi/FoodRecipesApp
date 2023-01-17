package com.jagoteori.foodrecipesapp.presentation.detail_recipe

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.flexbox.FlexboxLayout
import com.jagoteori.foodrecipesapp.R
import com.jagoteori.foodrecipesapp.databinding.FragmentStepsCookBinding
import com.jagoteori.foodrecipesapp.domain.entity.RecipeEntity

@SuppressLint("UseCompatLoadingForDrawables")
class StepsCookFragment(val recipeEntity: RecipeEntity) : Fragment() {
    private lateinit var binding: FragmentStepsCookBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStepsCookBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        // request layout for root to main container like Constraint/LinearLayout for wrap height fragment
        binding.root.requestLayout()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpLayout()
    }


    private fun setUpLayout() {
        if (recipeEntity.listStepCooking.isNullOrEmpty()) return
        for (stepCook in recipeEntity.listStepCooking) {
            val itemStepCook = layoutInflater.inflate(R.layout.item_step_cook, binding.root, false)

            val tvIndex = itemStepCook.findViewById<TextView>(R.id.tv_index_number)
            val tvDescription = itemStepCook.findViewById<TextView>(R.id.tv_description)
            val flexboxStepCook =
                itemStepCook.findViewById<FlexboxLayout>(R.id.flexbox_image_step_cook)

            val indexNumber = (stepCook.stepNumber!!.toInt() + 1).toString()
            tvIndex.text = indexNumber
            tvDescription.text = stepCook.stepDescription

            if (!stepCook.stepImages.isNullOrEmpty()) {
                for (imageCook in stepCook.stepImages) {
                    val imageView = ImageView(context)
                    imageView.layoutParams =
                        LinearLayout.LayoutParams(180, 180)

                    imageView.background = resources.getDrawable(R.drawable.rounded_image_step_cook)
                    imageView.clipToOutline = true
                    imageView.scaleType = ImageView.ScaleType.CENTER_CROP

                    Glide.with(this)
                        .load(imageCook)
                        .into(imageView)

                    flexboxStepCook.addView(imageView)
                }
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) binding.listStepCook.onViewRemoved(
                    flexboxStepCook
                )
            }

            binding.listStepCook.addView(itemStepCook)
        }
    }

}
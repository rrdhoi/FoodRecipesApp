package com.jagoteori.foodrecipesapp.presentation.add_recipe

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatSpinner
import androidx.core.content.res.ResourcesCompat
import com.bumptech.glide.Glide
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.flexbox.FlexboxLayout
import com.jagoteori.foodrecipesapp.R
import com.jagoteori.foodrecipesapp.app.extention.isNotNullOrEmpty
import com.jagoteori.foodrecipesapp.app.utils.imagePicker
import com.jagoteori.foodrecipesapp.databinding.ActivityAddRecipeBinding
import com.jagoteori.foodrecipesapp.domain.entity.ingredient.IngredientEntity

class AddRecipeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddRecipeBinding
    private val listIngredients: ArrayList<IngredientEntity> = ArrayList()
    private lateinit var spinnerListIngredient: Array<out String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        spinnerListIngredient = resources.getStringArray(R.array.list_quantity)
        toolbarSetUp()

        binding.btnAddIngredient.setOnClickListener { addIngredient() }
        binding.imgRecipe.setOnClickListener { imagePicker(this, IMAGE_PICKER_ADD_RECIPE_CODE) }
        binding.btnAddStepCook.setOnClickListener { addStepCook() }
        binding.btnSubmit.setOnClickListener {
            if (checkIngredientValidAndSubmit()) {
                Log.d("List Ingredient ::: ", "$listIngredients")
            }
        }
    }



    private fun addStepCook() {
        val rowAddStepCookingView =
            layoutInflater.inflate(R.layout.row_add_step_cooking, null, false)
        binding.llListStepCook.addView(rowAddStepCookingView)

        val flexibleListStepCook =
            rowAddStepCookingView.findViewById<FlexboxLayout>(R.id.list_image_step_cook)

        val btnAddImageStepCook =
            rowAddStepCookingView.findViewById<ImageView>(R.id.iv_add_image_step_cook)


        val indexRowAddStep = binding.llListStepCook.indexOfChild(rowAddStepCookingView)
//        val indexFlexibleStepCook = flexibleListStepCook.indexOfChild()
        Log.d("addStepCook", "Index row Add Step: $indexRowAddStep")
        btnAddImageStepCook.setOnClickListener {
            val concatTwoInt = "${indexRowAddStep+1}${flexibleListStepCook.childCount}".toInt()
            val imageView = imageViewGenerated(this@AddRecipeActivity, concatTwoInt)
            flexibleListStepCook.addView(imageView)
            Log.d("btn add step", "concat : ${concatTwoInt}|| isi flexiblenya : ${flexibleListStepCook.childCount} || index parentView : ${indexRowAddStep}")
        }
    }

    private fun checkIngredientValidAndSubmit(): Boolean {
        listIngredients.clear()
        var isNotEmpty = true

        for (index in 0 until binding.listIngredients.childCount) {
            val ingredientView = binding.listIngredients.getChildAt(index)

            val etTitleIngredient = ingredientView.findViewById<EditText>(R.id.et_title_ingredient)
            val etQuantity = ingredientView.findViewById<EditText>(R.id.et_quantity)
            val spinnerQuantity: AppCompatSpinner =
                ingredientView.findViewById(R.id.spinner_quantity)

            isNotEmpty = if (
                etTitleIngredient.isNotNullOrEmpty()
                and etQuantity.isNotNullOrEmpty()
                and (spinnerQuantity.selectedItemPosition != 0)
            ) {
                val ingredientEntity = IngredientEntity(
                    ingredient = etTitleIngredient.text.toString(),
                    quantity = etQuantity.text.toString(),
                    typeQuantity = spinnerListIngredient[spinnerQuantity.selectedItemPosition]
                )
                listIngredients.add(ingredientEntity)
                true
            } else {
                false
            }
        }

        if (binding.listIngredients.childCount == 0) {
            Toast.makeText(this, "Tambahkan bahan resep anda!", Toast.LENGTH_LONG).show()
        } else if (!isNotEmpty) {
            Toast.makeText(this, "Lengkapi bahan resep anda!", Toast.LENGTH_LONG).show()
        }

        return isNotEmpty
    }

    private fun addIngredient() {
        val rowAddIngredientView = layoutInflater.inflate(R.layout.row_add_ingredient, null, false)
        binding.listIngredients.addView(rowAddIngredientView)

        val spinnerQuantity: AppCompatSpinner =
            rowAddIngredientView.findViewById(R.id.spinner_quantity)
        val btnDeleteIngredient =
            rowAddIngredientView.findViewById<View>(R.id.btn_delete_ingredient)

        val arrayAdapterIngredient = ArrayAdapter(
            this,
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
            spinnerListIngredient
        )

        spinnerQuantity.adapter = arrayAdapterIngredient

        btnDeleteIngredient.setOnClickListener {
            binding.listIngredients.removeView(rowAddIngredientView)
        }
    }

    private fun toolbarSetUp() {
        binding.toolbarAddRecipe.apply {
            navigationIcon =
                ResourcesCompat.getDrawable(
                    resources,
                    com.google.android.material.R.drawable.material_ic_clear_black_24dp,
                    null
                )
            setNavigationOnClickListener { onBackPressed() }
        }
    }



    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            val uri: Uri = data?.data!!

            if (requestCode == IMAGE_PICKER_ADD_RECIPE_CODE)
                Glide.with(this)
                    .asBitmap()
                    .load(uri)
                    .into(binding.imgRecipe)

            Log.d("addrecipeActivity", "Request Codenya ::: $requestCode")
            for (indexParentView in 0 until binding.llListStepCook.childCount) {
                val rowAddStepCookingView = binding.llListStepCook.getChildAt(indexParentView)
                val flexibleListStepCook = rowAddStepCookingView.findViewById<FlexboxLayout>(R.id.list_image_step_cook)

                for (indexFlexibleView in 0 until flexibleListStepCook.childCount) {
                    val concatTwoInt = "${indexParentView+1}${indexFlexibleView+1}".toInt()
                    Log.d("addrecipeActivity", "indexParent(${indexParentView+1}) & indexFlexible(${indexFlexibleView+1})")
                    if (requestCode == concatTwoInt) {
                        val getChildFlexible = flexibleListStepCook.getChildAt(indexFlexibleView+1)
                        Log.d("on Activity Result:", "concat : $concatTwoInt || index flexiblenya :$indexFlexibleView ||| view Tag: ${getChildFlexible.tag}")
                        val imageView = getChildFlexible.findViewWithTag<ImageView>(concatTwoInt)

                        Log.d("on Activity Result:", "imageView : ${imageView.tag}")
                        Glide.with(this)
                            .asBitmap()
                            .load(uri)
                            .into(imageView)
                    }
                }
            }

        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }


    companion object {
        const val IMAGE_PICKER_ADD_RECIPE_CODE = 101
        const val IMAGE_PICKER_ADD_STEP_COOK_CODE = 102
    }
}
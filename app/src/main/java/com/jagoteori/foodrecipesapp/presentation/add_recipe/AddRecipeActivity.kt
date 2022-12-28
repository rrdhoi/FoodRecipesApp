package com.jagoteori.foodrecipesapp.presentation.add_recipe

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatSpinner
import androidx.core.content.res.ResourcesCompat
import com.bumptech.glide.Glide
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.flexbox.FlexboxLayout
import com.jagoteori.foodrecipesapp.R
import com.jagoteori.foodrecipesapp.app.Constants
import com.jagoteori.foodrecipesapp.app.extention.isNotNullOrEmpty
import com.jagoteori.foodrecipesapp.app.extention.length
import com.jagoteori.foodrecipesapp.app.extention.twoDigitsFormat
import com.jagoteori.foodrecipesapp.app.utils.imagePicker
import com.jagoteori.foodrecipesapp.databinding.ActivityAddRecipeBinding
import com.jagoteori.foodrecipesapp.domain.entity.StepCookEntity
import com.jagoteori.foodrecipesapp.domain.entity.ingredient.IngredientEntity


class AddRecipeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddRecipeBinding
    private lateinit var spinnerListIngredient: Array<out String>

    private val listIngredients: ArrayList<IngredientEntity> = ArrayList()
    private val listStepCook: ArrayList<StepCookEntity> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        spinnerListIngredient = resources.getStringArray(R.array.list_quantity)
        toolbarSetUp()

        StepCookFunction.onActivityCreate()

        binding.btnAddIngredient.setOnClickListener { addIngredientView(binding.listIngredients) }
        binding.imgRecipe.setOnClickListener { imagePicker(this, IMAGE_PICKER_ADD_RECIPE_CODE) }
        binding.btnAddStepCook.setOnClickListener { addStepCookView() }

        binding.btnSubmit.setOnClickListener {
            if (checkIngredientValid(binding.listIngredients) || checkStepCookValid(binding.llListStepCook)) {
                Log.d("List Step Cook ::: ", "$listStepCook")
                Log.d("List Ingredient ::: ", "$listIngredients")
            }

        }
    }

    private fun addStepCookView() {
        val rowAddStepCookingView =
            layoutInflater.inflate(R.layout.row_add_step_cooking, binding.root, false)
        binding.llListStepCook.addView(rowAddStepCookingView)

        val flexibleListStepCook =
            rowAddStepCookingView.findViewById<FlexboxLayout>(R.id.list_image_step_cook)

        val btnAddImageStepCook =
            rowAddStepCookingView.findViewById<ImageView>(R.id.iv_add_image_step_cook)


        val indexRowAddStep = binding.llListStepCook.indexOfChild(rowAddStepCookingView)
        Log.d("addStepCook", "Index row Add Step: $indexRowAddStep")
        btnAddImageStepCook.setOnClickListener {
            val setIdImageView =
                "${Constants.REQUEST_CODE_ADD_STEP_COOK}${indexRowAddStep.twoDigitsFormat()}${flexibleListStepCook.childCount}".toInt()

            imagePicker(activity = this, requestCode = setIdImageView, isGalleryOnly = true)
        }
    }

    private fun addIngredientView(
        listIngredientLayout: LinearLayout
    ) {
        val rowAddIngredientView =
            layoutInflater.inflate(R.layout.row_add_ingredient, binding.root, false)
        listIngredientLayout.addView(rowAddIngredientView)

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
            listIngredientLayout.removeView(rowAddIngredientView)
        }
    }

    // validasi form resep dan masukkan setiap value resep kedalam list
    private fun checkIngredientValid(listIngredientLayout: LinearLayout): Boolean {
        var isNotEmpty = false
        listIngredients.clear()

        for (index in 0 until listIngredientLayout.childCount) {
            val ingredientView = listIngredientLayout.getChildAt(index)

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

        if (listIngredientLayout.childCount == 0) {
            Toast.makeText(this, "Tambahkan bahan resep anda!", Toast.LENGTH_LONG).show()
        } else if (!isNotEmpty) {
            Toast.makeText(this, "Lengkapi bahan resep anda!", Toast.LENGTH_LONG).show()
        }

        return isNotEmpty
    }

    // validasi form langkah memasak dan masukkan setiap value langkah memasak
    // dengan gambar step kedalam list
    private fun checkStepCookValid(listStepCookLayout: LinearLayout): Boolean {
        var isNotEmpty = false
        listStepCook.clear()

        for (indexStepLayout in 0 until listStepCookLayout.childCount) {
            val stepCookingView = listStepCookLayout.getChildAt(indexStepLayout)
            val stepDescriptionView =
                stepCookingView.findViewById<EditText>(R.id.et_description_step_cook)
            val listStepImagesView =
                stepCookingView.findViewById<FlexboxLayout>(R.id.list_image_step_cook)

            isNotEmpty = if (stepDescriptionView.isNotNullOrEmpty()) {
                val stepImages = mutableListOf<Uri>()
                for (indexImageView in 0 until listStepImagesView.childCount) {
                    for (image in StepCookFunction.listStepCookImages) {
                        val imageView = listStepImagesView.getChildAt(indexImageView)

                        if (imageView.id.toString() == image.values.first().toString())
                            stepImages.add(Uri.parse(image.values.last()))
                    }
                }

                val stepCookEntity = StepCookEntity(
                    stepNumber = indexStepLayout,
                    stepDescription = stepDescriptionView.text.toString(),
                    stepImages = stepImages
                )

                listStepCook.add(stepCookEntity)

                true
            } else {
                false
            }
        }

        if (listStepCookLayout.childCount == 0) {
            Toast.makeText(this, "Tambahkan langkah memasak resep anda!", Toast.LENGTH_LONG).show()
        } else if (!isNotEmpty) {
            Toast.makeText(this, "Lengkapi langkah memasak anda!", Toast.LENGTH_LONG).show()
        }

        return isNotEmpty
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

            Log.d(
                "Fixing Image nih boss",
                "request Code: $requestCode"
            )

            if (requestCode.length() > 3 || Integer.parseInt(
                    resultCode.toString().substring(0, 2)
                ) == Constants.REQUEST_CODE_ADD_STEP_COOK.toInt()
            ) {
                StepCookFunction.addingImageIntoStepCookView(
                    activity = this,
                    parentView = binding.llListStepCook,
                    requestCode = requestCode,
                    uri = uri
                )
            }


            if (requestCode == IMAGE_PICKER_ADD_RECIPE_CODE) {
                Glide.with(this)
                    .asBitmap()
                    .load(uri)
                    .into(binding.imgRecipe)

            }

        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        const val IMAGE_PICKER_ADD_RECIPE_CODE = 101
    }
}
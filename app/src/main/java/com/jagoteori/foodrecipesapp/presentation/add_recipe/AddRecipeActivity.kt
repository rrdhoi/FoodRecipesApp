package com.jagoteori.foodrecipesapp.presentation.add_recipe

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatSpinner
import androidx.cardview.widget.CardView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.get
import com.google.android.flexbox.FlexboxLayout
import com.jagoteori.foodrecipesapp.R
import com.jagoteori.foodrecipesapp.app.Constants
import com.jagoteori.foodrecipesapp.app.extention.isEmpty
import com.jagoteori.foodrecipesapp.app.extention.isNotNullOrEmpty
import com.jagoteori.foodrecipesapp.app.extention.twoDigitsFormat
import com.jagoteori.foodrecipesapp.app.utils.imagePicker
import com.jagoteori.foodrecipesapp.data.Resource
import com.jagoteori.foodrecipesapp.databinding.ActivityAddRecipeBinding
import com.jagoteori.foodrecipesapp.domain.entity.IngredientEntity
import com.jagoteori.foodrecipesapp.domain.entity.RecipeEntity
import com.jagoteori.foodrecipesapp.domain.entity.StepCookEntity
import com.jagoteori.foodrecipesapp.domain.entity.UserEntity
import com.jagoteori.foodrecipesapp.presentation.ui.pages.add_recipe.AddRecipeScreen
import org.koin.androidx.viewmodel.ext.android.viewModel


class AddRecipeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddRecipeBinding
    private lateinit var spinnerListIngredient: Array<out String>
    private lateinit var myUserEntity: UserEntity
    private val addRecipeViewModel: AddRecipeViewModel by viewModel()

    private var imageRecipeUri: Uri = Uri.EMPTY
    private val listIngredients: ArrayList<IngredientEntity> = ArrayList()
    private val listStepCook: ArrayList<StepCookEntity> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    AddRecipeScreen(
                        modifier = Modifier,
                        viewModel = addRecipeViewModel,
                    )
                }
            }
        }

        /* binding = ActivityAddRecipeBinding.inflate(layoutInflater)

         setContentView(binding.root)
         setUpIngredients()
         setUpStepCook()
         toolbarSetUp()

         StepCookFunction.onActivityCreate()

         binding.btnAddIngredient.setOnClickListener { addIngredientView(binding.listIngredients) }
         binding.imgRecipe.setOnClickListener { imagePicker(this, IMAGE_PICKER_ADD_RECIPE_CODE) }
         binding.btnAddStepCook.setOnClickListener { addStepCookView() }

         binding.btnSubmit.setOnClickListener { submitRecipe() }

         viewModelObserve()*/
    }

    private fun viewModelObserve() {
        addRecipeViewModel.addRecipe.observe(this) {
            when (it) {
                is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    finish()
                    Toast.makeText(this, "Upload resep berhasil!", Toast.LENGTH_LONG).show()
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, "Upload resep gagal!", Toast.LENGTH_LONG).show()
                }
                else -> {}
            }
        }

        addRecipeViewModel.myUser.observe(this) {
            when (it) {
                is Resource.Success -> {
                    myUserEntity = it.data!!
                }
                is Resource.Error -> {
                    Toast.makeText(this, "Gagal memuat server, coba lagi!!", Toast.LENGTH_LONG)
                        .show()
                }
                else -> {}
            }
        }
    }

    private fun setUpIngredients() {
        // remove first view button delete ingredient
        val rowAddIngredientView = binding.listIngredients[0] as ConstraintLayout
        val btnDelete = rowAddIngredientView.findViewById<CardView>(R.id.card_btn_delete)
        rowAddIngredientView.removeView(btnDelete)

        setUpSpinnerIngredients()
    }

    private fun setUpStepCook() {
        val flexibleListStepCook =
            binding.llListStepCook[0].findViewById<FlexboxLayout>(R.id.list_image_step_cook)

        val btnAddImageStepCook =
            flexibleListStepCook.findViewById<CardView>(R.id.card_add_image_step_cook)

        btnAddImageStepCook.setOnClickListener {
            val setIdImageView =
                "${Constants.REQUEST_CODE_ADD_STEP_COOK}00${flexibleListStepCook.childCount}".toInt()

            imagePicker(activity = this, requestCode = setIdImageView, isGalleryOnly = true)
        }
    }

    private fun setUpSpinnerIngredients() {
        spinnerListIngredient = resources.getStringArray(R.array.list_quantity)
        val arrayAdapterIngredient = ArrayAdapter(
            this,
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
            spinnerListIngredient
        )
        binding.listIngredients[0].findViewById<Spinner>(R.id.spinner_quantity).adapter =
            arrayAdapterIngredient
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun submitRecipe() {
        if (binding.etTitle.text.isBlank() and
            binding.etDescription.text.isNullOrBlank() and
            imageRecipeUri.isEmpty() and
            binding.etCategory.text.isNullOrBlank()
        ) {
            Toast.makeText(this, "Lengkapi resep anda!", Toast.LENGTH_LONG).show()
            return
        }

        if (checkStepCookValid(binding.llListStepCook) and checkIngredientValid(binding.listIngredients)) {
            val recipeEntity = RecipeEntity(
                id = null,
                title = binding.etTitle.text.toString(),
                category = binding.etCategory.text.toString(),
                description = binding.etDescription.text.toString(),
                publisherId = myUserEntity.userId,
                publisher = myUserEntity.name,
                recipePicture = imageRecipeUri.toString(),
                listIngredients = listIngredients,
                listStepCooking = listStepCook,
                listComments = null
            )

//            addRecipeViewModel.addRecipe(recipeEntity)
        }
    }

    private fun addStepCookView() {
        val rowAddStepCookingView =
            layoutInflater.inflate(R.layout.row_add_step_cooking, binding.root, false)
        binding.llListStepCook.addView(rowAddStepCookingView)

        val flexibleListStepCook =
            rowAddStepCookingView.findViewById<FlexboxLayout>(R.id.list_image_step_cook)

        val btnAddImageStepCook =
            flexibleListStepCook.findViewById<CardView>(R.id.card_add_image_step_cook)

        val indexRowAddStep = binding.llListStepCook.indexOfChild(rowAddStepCookingView)
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
                val stepImages = mutableListOf<String>()
                for (indexImageView in 0 until listStepImagesView.childCount) {
                    for (image in StepCookFunction.listStepCookImages) {
                        val imageView = listStepImagesView.getChildAt(indexImageView)

                        if (imageView.id.toString() == image.values.first().toString())
                            stepImages.add(image.values.last())
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
                    R.drawable.ic_baseline_clear_24,
                    null
                )
            setNavigationOnClickListener { finish() }
        }
    }


   /* @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            val uri: Uri = data?.data!!

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

                imageRecipeUri = uri
            }

        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }*/

    companion object {
        const val IMAGE_PICKER_ADD_RECIPE_CODE = 101
    }
}
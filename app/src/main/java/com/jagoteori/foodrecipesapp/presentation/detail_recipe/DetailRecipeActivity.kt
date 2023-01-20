package com.jagoteori.foodrecipesapp.presentation.detail_recipe

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.core.content.res.ResourcesCompat
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.jagoteori.foodrecipesapp.R
import com.jagoteori.foodrecipesapp.app.parcelable
import com.jagoteori.foodrecipesapp.databinding.ActivityDetailRecipeBinding
import com.jagoteori.foodrecipesapp.domain.entity.RecipeEntity
import com.jagoteori.foodrecipesapp.presentation.ui.pages.DetailRecipeScreen
import com.jagoteori.foodrecipesapp.presentation.ui.pages.DetailRecipeScreenError


class DetailRecipeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailRecipeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*   binding = ActivityDetailRecipeBinding.inflate(layoutInflater)


           setContentView(binding.root)
           setTabLayout(recipeData!!)
           setDetailRecipe(recipeData)
           setUpToolbar()*/
        val recipeData = intent.parcelable<RecipeEntity>(DATA_RECIPE)

        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    if (recipeData == null)
                        DetailRecipeScreenError(modifier = Modifier)
                    else
                        DetailRecipeScreen(modifier = Modifier, recipeEntity = recipeData)
                }
            }
        }
    }


    private fun setUpToolbar() {
        binding.toolbar.apply {
            navigationIcon =
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.ic_baseline_arrow_back_ios_24,
                    null
                )
            setNavigationOnClickListener { finish() }
        }
    }

    private fun setDetailRecipe(recipeEntity: RecipeEntity) = recipeEntity.run {
        with(binding) {
            tvTitleRecipe.text = title
            tvDetailRecipe.text = description
            tvPublisher.text = publisher

            Glide.with(applicationContext)
                .load(recipePicture)
                .into(ivImgRecipe)
        }
    }

    private fun setTabLayout(recipeEntity: RecipeEntity) {
        val viewPager = binding.viewPager
        viewPager.isUserInputEnabled = false

        val sectionsPagerAdapter = SectionsPagerAdapter(this, recipeEntity)
        viewPager.adapter = sectionsPagerAdapter

        TabLayoutMediator(binding.tabs, viewPager) { tab, position ->
            tab.text = this.resources.getStringArray(R.array.tab_detail_recipe)[position]
        }.attach()

        binding.tabs.requestLayout()
    }

    companion object {
        const val DATA_RECIPE = "DATA_RECIPE"
    }
}
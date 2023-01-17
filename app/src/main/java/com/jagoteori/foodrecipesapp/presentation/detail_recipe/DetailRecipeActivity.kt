package com.jagoteori.foodrecipesapp.presentation.detail_recipe

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.jagoteori.foodrecipesapp.R
import com.jagoteori.foodrecipesapp.app.parcelable
import com.jagoteori.foodrecipesapp.databinding.ActivityDetailRecipeBinding
import com.jagoteori.foodrecipesapp.domain.entity.RecipeEntity
import org.koin.androidx.viewmodel.ext.android.viewModel


class DetailRecipeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailRecipeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailRecipeBinding.inflate(layoutInflater)

        val recipeData = intent.parcelable<RecipeEntity>(DATA_RECIPE)

        setContentView(binding.root)
        setTabLayout(recipeData!!)
        setDetailRecipe(recipeData)
        setUpToolbar()
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
            tab.text = this.resources.getStringArray(R.array.tab_title)[position]
        }.attach()

        binding.tabs.requestLayout()
    }

    companion object {
        const val DATA_RECIPE = "DATA_RECIPE"
    }
}
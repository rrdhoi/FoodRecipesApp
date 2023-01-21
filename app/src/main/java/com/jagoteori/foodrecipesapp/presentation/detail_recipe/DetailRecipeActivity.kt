package com.jagoteori.foodrecipesapp.presentation.detail_recipe

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.jagoteori.foodrecipesapp.app.parcelable
import com.jagoteori.foodrecipesapp.domain.entity.RecipeEntity
import com.jagoteori.foodrecipesapp.presentation.ui.pages.DetailRecipeScreen
import com.jagoteori.foodrecipesapp.presentation.ui.pages.DetailRecipeScreenError
import org.koin.androidx.viewmodel.ext.android.viewModel


class DetailRecipeActivity : AppCompatActivity() {
    private val detailViewModel: DetailRecipeViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val recipeData = intent.parcelable<RecipeEntity>(DATA_RECIPE)

        detailViewModel.getMyUser()

        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    if (recipeData == null)
                        DetailRecipeScreenError(modifier = Modifier)
                    else
                        DetailRecipeScreen(
                            modifier = Modifier,
                            recipeEntity = recipeData,
                            detailViewModel = detailViewModel
                        )
                }
            }
        }
    }

    companion object {
        const val DATA_RECIPE = "DATA_RECIPE"
    }
}
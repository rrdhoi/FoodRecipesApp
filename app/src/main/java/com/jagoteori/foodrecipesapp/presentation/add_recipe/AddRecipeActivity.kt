package com.jagoteori.foodrecipesapp.presentation.add_recipe

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.jagoteori.foodrecipesapp.presentation.ui.pages.add_recipe.AddRecipeScreen
import org.koin.androidx.viewmodel.ext.android.viewModel


class AddRecipeActivity : AppCompatActivity() {
    private val addRecipeViewModel: AddRecipeViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                AddRecipeScreen(
                    modifier = Modifier,
                    viewModel = addRecipeViewModel,
                    onBackPressed = {
                        finish()
                    }
                )
            }
        }
    }
}
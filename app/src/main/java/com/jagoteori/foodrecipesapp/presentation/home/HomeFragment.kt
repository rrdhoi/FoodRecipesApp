package com.jagoteori.foodrecipesapp.presentation.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.jagoteori.foodrecipesapp.presentation.detail_recipe.DetailRecipeActivity
import com.jagoteori.foodrecipesapp.presentation.ui.pages.home.HomeScreen
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class HomeFragment : Fragment() {
    private val homeViewModel: HomeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Timber.plant(Timber.DebugTree())
        return ComposeView(requireContext()).apply {
            setContent {
                MaterialTheme {
                    HomeScreen(
                        modifier = Modifier,
                        homeViewModel = homeViewModel,
                        onItemRecommendationClicked = {
                            val intent = Intent(context, DetailRecipeActivity::class.java).putExtra(
                                DetailRecipeActivity.DATA_RECIPE,
                                it
                            )
                            startActivity(intent)
                        },
                        onItemCategoryClicked = {
                            val intent = Intent(context, DetailRecipeActivity::class.java).putExtra(
                                DetailRecipeActivity.DATA_RECIPE,
                                it
                            )
                            startActivity(intent)
                        },
                    )
                }
            }
        }
    }

}
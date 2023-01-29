package com.jagoteori.foodrecipesapp.presentation.profile.my_recipes

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
import com.jagoteori.foodrecipesapp.presentation.ui.pages.profile.MyRecipeScreen
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyRecipesFragment : Fragment() {
    private val viewModel: MyRecipesViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                MaterialTheme {
                    MyRecipeScreen(
                        modifier = Modifier, viewModel = viewModel,
                        onItemClicked = {
                            val intent = Intent(context, DetailRecipeActivity::class.java).putExtra(
                                DetailRecipeActivity.DATA_RECIPE, it
                            )
                            startActivity(intent)
                        },
                        onBackPressed = {
                            val fragmentManager = parentFragmentManager
                            fragmentManager.popBackStack()
                        },
                    )
                }
            }
        }
    }
}
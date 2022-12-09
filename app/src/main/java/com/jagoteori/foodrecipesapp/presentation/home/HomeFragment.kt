package com.jagoteori.foodrecipesapp.presentation.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.jagoteori.foodrecipesapp.data.Resource
import com.jagoteori.foodrecipesapp.databinding.HomeFragmentBinding
import com.jagoteori.foodrecipesapp.domain.entity.RecipeEntity
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {
    private val homeViewModel: HomeViewModel by viewModel()
    private lateinit var binding: HomeFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataRecipe()
    }

    private fun dataRecipe() {
        binding.getRecipeButton.setOnClickListener {
            homeViewModel.getRecipeById("7DgQ06RgdJiacM5iJdKx")
            homeViewModel.recipeId.observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Success -> {
                        Log.d("Get Recipe Id ::", "dataRecipe: ${it.data}")
                    }
                    is Resource.Error -> {
                        Log.d("Get Recipe Id ::", "dataRecipe: ${it.message}")
                    }
                    is Resource.Loading -> {
                        Log.d("Get Recipe Id ::", "dataRecipe: Loading")
                    }
                }
            }
        }
        binding.addButton.setOnClickListener {
            val random = (1..100).random()
            val recipe = RecipeEntity(
                id = "",
                title = "Chicken $random",
                publisher = "JagoTeori $random",
                recipePicture = "https://www.themealdb.com/images/media/meals/58oia61564916529.jpg",
                description = " Chicken is the most common type of poultry in the world. Owing to the relative ease and low cost of raising them in comparison to animals such as cattle or hogs, chickens have become prevalent throughout the cuisine of cultures around the world, and their meat has been variously adapted to regional tastes.",
            )
            homeViewModel.addRecipe(recipe)
        }
        homeViewModel.allRecipes.observe(viewLifecycleOwner) { recipes ->
            when (recipes) {
                is Resource.Loading -> {
                    Log.d("Home Fragment ::", "dataRecipe: Loading")
                }
                is Resource.Success -> {
                    Log.d("Home Fragment ::", "dataRecipe: ${recipes.data}")
                }
                is Resource.Error -> {
                    Log.d("Home Fragment ::", "dataRecipe: Error ${recipes.message}")
                }
            }

        }
    }
}
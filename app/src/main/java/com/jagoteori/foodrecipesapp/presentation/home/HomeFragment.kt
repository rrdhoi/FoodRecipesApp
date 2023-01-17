package com.jagoteori.foodrecipesapp.presentation.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.compose.material.MaterialTheme
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayoutMediator
import com.jagoteori.foodrecipesapp.R
import com.jagoteori.foodrecipesapp.data.Resource
import com.jagoteori.foodrecipesapp.databinding.HomeFragmentBinding
import com.jagoteori.foodrecipesapp.databinding.ItemRecommendationRecipeBinding
import com.jagoteori.foodrecipesapp.domain.entity.RecipeEntity
import com.jagoteori.foodrecipesapp.presentation.adapter.GenericListAdapter
import com.jagoteori.foodrecipesapp.presentation.detail_recipe.DetailRecipeActivity
import com.jagoteori.foodrecipesapp.presentation.ui.components.ItemRecommendation
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class HomeFragment : Fragment() {
    private val homeViewModel: HomeViewModel by viewModel()
    private lateinit var binding: HomeFragmentBinding
    private lateinit var recommendationRecipeAdapter: GenericListAdapter<RecipeEntity>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Timber.plant(Timber.DebugTree())
        binding = HomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpListAdapter()
        showRecyclerList()
        observeRecipes()
    }

    private fun setTabLayout(listRecipe: List<RecipeEntity>) {
        val viewPager = binding.viewPager
        viewPager.isUserInputEnabled = false

        val sectionsPagerAdapter = SectionsPagerCategoryAdapter(requireActivity(), listRecipe)
        viewPager.adapter = sectionsPagerAdapter

        TabLayoutMediator(binding.tabs, viewPager) { tab, position ->
            tab.text = resources.getStringArray(R.array.tab_category)[position]
        }.attach()

        val tabs = binding.tabs.getChildAt(0) as ViewGroup

        for (index in 0 until tabs.childCount) {
            Timber.d("ikang :: $index")
            val tab = tabs.getChildAt(index)
            val layoutParams = tab.layoutParams as LinearLayout.LayoutParams
            layoutParams.marginEnd = 30
        }
        binding.tabs.requestLayout()
    }

    private fun setUpListAdapter() {
        recommendationRecipeAdapter = @SuppressLint("ResourceType")
        object : GenericListAdapter<RecipeEntity>(
            R.layout.item_recommendation_recipe,
            bind = { item, holder, _ ->
                val binding = ItemRecommendationRecipeBinding.bind(holder.itemView)
                binding.composeView.setContent {
                    MaterialTheme {
                        ItemRecommendation(
                            title = item.title,
                            publisher = item.publisher,
                            imageRecipe = item.recipePicture
                        ) {
                            val intent = Intent(context, DetailRecipeActivity::class.java).putExtra(
                                DetailRecipeActivity.DATA_RECIPE,
                                item
                            )
                            startActivity(intent)
                        }
                    }
                }
            }
        ) {}
    }

    private fun showRecyclerList() {
        with(binding.rvRecommendationRecipe) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = recommendationRecipeAdapter
        }
    }


    private fun observeRecipes() {
        homeViewModel.allRecipes.observe(viewLifecycleOwner) { recipes ->
            when (recipes) {
                is Resource.Loading -> {
                    Timber.tag("Home Fragment ::").d("dataRecipe: Loading")
                }
                is Resource.Success -> {
                    recipes.data?.let { setTabLayout(it) }
                    recommendationRecipeAdapter.submitList(recipes.data)
                    Timber.tag("Home Fragment ::").d("dataRecipe: ${recipes.data}")
                }
                is Resource.Error -> {
                    Timber.tag("Home Fragment ::").d("dataRecipe: Error %s", recipes.message)
                }
            }
        }
    }

}
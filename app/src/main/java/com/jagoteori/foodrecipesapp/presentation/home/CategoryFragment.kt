package com.jagoteori.foodrecipesapp.presentation.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.MaterialTheme
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.jagoteori.foodrecipesapp.R
import com.jagoteori.foodrecipesapp.databinding.FragmentCategoryBinding
import com.jagoteori.foodrecipesapp.databinding.ItemCategoryBinding
import com.jagoteori.foodrecipesapp.domain.entity.RecipeEntity
import com.jagoteori.foodrecipesapp.presentation.adapter.GenericListAdapter
import com.jagoteori.foodrecipesapp.presentation.detail_recipe.DetailRecipeActivity
import com.jagoteori.foodrecipesapp.presentation.ui.components.ItemCategoryCard
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class CategoryFragment(private val listRecipe: List<RecipeEntity>) : Fragment() {

    private val viewModel: CategoryViewModel by viewModel()
    private lateinit var binding: FragmentCategoryBinding
    private lateinit var categoryRecipeAdapter: GenericListAdapter<RecipeEntity>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Timber.plant(Timber.DebugTree())
        binding = FragmentCategoryBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpListAdapter()
        showRecyclerList()
    }

    private fun setUpListAdapter() {
        categoryRecipeAdapter = @SuppressLint("ResourceType")
        object : GenericListAdapter<RecipeEntity>(
            R.layout.item_category,
            bind = { item, holder, _ ->
                val binding = ItemCategoryBinding.bind(holder.itemView)
                binding.composeView.setContent {
                    MaterialTheme {
                        ItemCategoryCard(
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
        with(binding.rvListRecipes) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = categoryRecipeAdapter
        }
        categoryRecipeAdapter.submitList(listRecipe)
    }

}
package com.jagoteori.foodrecipesapp.presentation.profile.my_recipes

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.MaterialTheme
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.jagoteori.foodrecipesapp.R
import com.jagoteori.foodrecipesapp.data.Resource
import com.jagoteori.foodrecipesapp.databinding.FragmentMyRecipesBinding
import com.jagoteori.foodrecipesapp.databinding.ItemMyRecipeBinding
import com.jagoteori.foodrecipesapp.domain.entity.RecipeEntity
import com.jagoteori.foodrecipesapp.presentation.adapter.GenericListAdapter
import com.jagoteori.foodrecipesapp.presentation.detail_recipe.DetailRecipeActivity
import com.jagoteori.foodrecipesapp.presentation.ui.components.ItemCategoryCard
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyRecipesFragment : Fragment() {
    private val viewModel: MyRecipesViewModel by viewModel()
    private lateinit var binding: FragmentMyRecipesBinding
    private lateinit var myRecipesAdapter: GenericListAdapter<RecipeEntity>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyRecipesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpListAdapter()
        showRecyclerList()
        observeRecipes()

        binding.ivBack.setOnClickListener {
            val fragmentManager = parentFragmentManager
            fragmentManager.popBackStack()
        }
    }

    private fun setUpListAdapter() {
        myRecipesAdapter = @SuppressLint("ResourceType")
        object : GenericListAdapter<RecipeEntity>(
            R.layout.item_my_recipe,
            bind = { item, holder, _ ->
                val binding = ItemMyRecipeBinding.bind(holder.itemView)

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
            layoutManager = LinearLayoutManager(context)
            adapter = myRecipesAdapter
        }
    }

    private fun observeRecipes() {
        viewModel.getMyRecipes()
        viewModel.myRecipes.observe(viewLifecycleOwner) { recipes ->
            when (recipes) {
                is Resource.Loading -> {
                    binding.pbHome.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    myRecipesAdapter.submitList(recipes.data)
                    binding.pbHome.visibility = View.GONE
                }
                is Resource.Error -> {
                    binding.pbHome.visibility = View.GONE
                    binding.tvMessage.text = recipes.message
                }
            }

        }
    }
}
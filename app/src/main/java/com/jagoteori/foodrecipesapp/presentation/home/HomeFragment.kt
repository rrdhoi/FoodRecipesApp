package com.jagoteori.foodrecipesapp.presentation.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import coil.compose.AsyncImage
import com.google.android.material.tabs.TabLayoutMediator
import com.jagoteori.foodrecipesapp.R
import com.jagoteori.foodrecipesapp.data.Resource
import com.jagoteori.foodrecipesapp.databinding.HomeFragmentBinding
import com.jagoteori.foodrecipesapp.databinding.ItemRecommendationRecipeBinding
import com.jagoteori.foodrecipesapp.domain.entity.RecipeEntity
import com.jagoteori.foodrecipesapp.presentation.adapter.GenericListAdapter
import com.jagoteori.foodrecipesapp.presentation.detail_recipe.DetailRecipeActivity
import com.jagoteori.foodrecipesapp.presentation.ui.theme.BlackColor500
import com.jagoteori.foodrecipesapp.presentation.ui.theme.GreyColor300
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

    @Composable
    fun ItemRecommendation(
        title: String?,
        publisher: String?,
        imageRecipe: String?,
        itemOnClick: () -> Unit
    ) {
        ConstraintLayout(modifier = Modifier.clickable { itemOnClick() }) {
            val (idImageRecipe, idTitle, idPublisher) = createRefs()

            Box(modifier = Modifier
                .clip(RoundedCornerShape(12))
                .constrainAs(idImageRecipe) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }) {
                AsyncImage(
                    model = imageRecipe,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(220.dp, 120.dp)
                )
                Surface(
                    shape = RoundedCornerShape(bottomStart = 10.dp, topEnd = 22.dp),
                    color = BlackColor500,
                    modifier = Modifier
                        .width(220.dp)
                        .height(120.dp)
                        .alpha(0.15f)
                ) {}
            }

            Text(
                text = title ?: "",
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Start,
                maxLines = 2,
                fontSize = 16.sp,
                color = BlackColor500,
                modifier = Modifier.constrainAs(idTitle) {
                    top.linkTo(idImageRecipe.bottom, margin = 8.dp)
                    start.linkTo(parent.start, margin = 8.dp)
                    end.linkTo(parent.end, margin = 8.dp)
                    width = Dimension.fillToConstraints
                }
            )

            Text(
                text = publisher ?: "",
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Start,
                maxLines = 2,
                fontSize = 14.sp,
                color = GreyColor300,
                modifier = Modifier.constrainAs(idPublisher) {
                    top.linkTo(idTitle.bottom, margin = 4.dp)
                    start.linkTo(idTitle.start)
                    bottom.linkTo(parent.bottom, margin = 8.dp)
                }
            )
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun NewsItemPreview() {
        MaterialTheme {
            ItemRecommendation("asdas", "New News", "2022-02-22") {}
        }
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
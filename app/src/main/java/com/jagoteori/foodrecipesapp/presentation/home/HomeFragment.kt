package com.jagoteori.foodrecipesapp.presentation.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.jagoteori.foodrecipesapp.R
import com.jagoteori.foodrecipesapp.databinding.HomeFragmentBinding
import com.jagoteori.foodrecipesapp.databinding.ItemRecommendationRecipeBinding
import com.jagoteori.foodrecipesapp.domain.entity.RecipeEntity
import com.jagoteori.foodrecipesapp.presentation.adapter.GenericListAdapter
import com.jagoteori.foodrecipesapp.presentation.detail_recipe.DetailRecipeActivity
import com.jagoteori.foodrecipesapp.presentation.ui.UiState
import com.jagoteori.foodrecipesapp.presentation.ui.components.ItemCategoryCard
import com.jagoteori.foodrecipesapp.presentation.ui.components.ItemRecommendation
import com.jagoteori.foodrecipesapp.presentation.ui.extention.NoRippleTheme
import com.jagoteori.foodrecipesapp.presentation.ui.theme.BlackColor500
import com.jagoteori.foodrecipesapp.presentation.ui.theme.GreyColor100
import com.jagoteori.foodrecipesapp.presentation.ui.theme.WhiteColor
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import java.util.*

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

        binding.composeView.setContent {
            MaterialTheme {
                HomeScreen(modifier = Modifier, itemCategoryOnClick = {
                    val intent = Intent(context, DetailRecipeActivity::class.java).putExtra(
                        DetailRecipeActivity.DATA_RECIPE,
                        it
                    )
                    startActivity(intent)
                })
            }
        }

        setUpListAdapter()
        showRecyclerList()
    }

    @Composable
    fun HomeScreen(modifier: Modifier, itemCategoryOnClick: (recipe: RecipeEntity) -> Unit) {
        Column(modifier = modifier.fillMaxSize()) {
            homeViewModel.uiState.collectAsState().value.let { uiState ->
                when (uiState) {
                    is UiState.Loading -> {
                    }
                    is UiState.Success -> {
                        recommendationRecipeAdapter.submitList(uiState.data)
                        HomeTabLayout(
                            recipes = uiState.data,
                            modifier = modifier,
                            itemCategoryOnClick
                        )
                    }
                    is UiState.Error -> {}
                }
            }
        }
    }


    @OptIn(ExperimentalPagerApi::class)
    @Composable
    fun HomeTabLayout(
        recipes: List<RecipeEntity>?,
        modifier: Modifier,
        itemCategoryOnClick: (recipe: RecipeEntity) -> Unit
    ) {
        val pagerState = rememberPagerState()
        val coroutineScope = rememberCoroutineScope()
        val tabRowItems = stringArrayResource(id = R.array.tab_category)

        Column() {
            CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
                TabRow(
                    modifier = modifier.padding(vertical = 16.dp, horizontal = 24.dp),
                    selectedTabIndex = pagerState.currentPage,
                    backgroundColor = Color.Transparent,
                    indicator = {
                        Box {}
                    },
                    divider = {
                        Box {}
                    }
                ) {

                    tabRowItems.forEachIndexed { index, item ->
                        val selected = pagerState.currentPage == index
                        Tab(
                            selected = pagerState.currentPage == index,
                            onClick = { coroutineScope.launch { pagerState.animateScrollToPage(index) } },
                            modifier = if (selected) modifier
                                .background(
                                    color = BlackColor500,
                                    shape = RoundedCornerShape(8.dp)
                                )
                            else modifier
                                .background(color = WhiteColor),
                            text = {
                                Text(
                                    text = item,
                                    maxLines = 2,
                                    color = if (selected) GreyColor100 else BlackColor500,
                                    overflow = TextOverflow.Ellipsis,
                                )
                            },
                        )
                    }
                }
                HorizontalPager(
                    count = tabRowItems.size,
                    state = pagerState,
                ) {
                    if (recipes == null) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            modifier = modifier.height(250.dp)
                        ) {
                            CircularProgressIndicator(color = BlackColor500)
                        }
                    } else {
                        ScreenPager(
                            recipes = recipes.filter { it.category!!.capitalize(Locale.ROOT) == tabRowItems[pagerState.currentPage] },
                            modifier = modifier,
                            itemCategoryOnClick
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun ScreenPager(
        recipes: List<RecipeEntity>?,
        modifier: Modifier,
        itemCategoryOnClick: (recipe: RecipeEntity) -> Unit
    ) {
        if (recipes.isNullOrEmpty()) {
            Column(verticalArrangement = Arrangement.Center, modifier = modifier.height(250.dp)) {
                Text(text = "Belum ada resep :)")
            }
        } else {
            LazyColumn(
                modifier = modifier.padding(top = 8.dp),
                contentPadding = PaddingValues(
                    bottom = 30.dp,
                    start = 24.dp, end = 24.dp,
                ), content = {
                    items(recipes.size) { index ->
                        ItemCategoryCard(
                            title = recipes[index].title,
                            publisher = recipes[index].publisher,
                            imageRecipe = recipes[index].recipePicture
                        ) {
                            itemCategoryOnClick(recipes[index])
                        }
                    }
                })
        }
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

}
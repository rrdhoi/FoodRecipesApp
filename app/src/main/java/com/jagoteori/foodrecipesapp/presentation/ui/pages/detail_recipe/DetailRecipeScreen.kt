package com.jagoteori.foodrecipesapp.presentation.ui.pages.detail_recipe

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.jagoteori.foodrecipesapp.R
import com.jagoteori.foodrecipesapp.domain.entity.RecipeEntity
import com.jagoteori.foodrecipesapp.presentation.ui.components.TopAppBarBlack
import com.jagoteori.foodrecipesapp.presentation.extention.NoRippleTheme
import com.jagoteori.foodrecipesapp.presentation.ui.pages.detail_recipe.components.CommentsList
import com.jagoteori.foodrecipesapp.presentation.ui.pages.detail_recipe.components.IngredientsList
import com.jagoteori.foodrecipesapp.presentation.ui.pages.detail_recipe.components.StepsCookList
import com.jagoteori.foodrecipesapp.presentation.ui.pages.detail_recipe.view_model.DetailRecipeViewModel
import com.jagoteori.foodrecipesapp.presentation.ui.theme.*
import kotlinx.coroutines.launch
import java.util.*

@Composable
fun DetailRecipeScreen(
    modifier: Modifier = Modifier,
    detailViewModel: DetailRecipeViewModel,
    onBackPressed: () -> Unit,
) {
    val scrollState = rememberScrollState()
    val recipeEntity = detailViewModel.recipeEntity

    Scaffold(
        topBar = {
            TopAppBarBlack(
                title = recipeEntity.title ?: "Detail Resep",
                icon = painterResource(id = R.drawable.ic_arrow_back_white)
            ) {
                onBackPressed()
            }
        },
    ) { innerPadding ->
        Column(
            Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            AsyncImage(
                model = recipeEntity.recipePicture,
                contentDescription = "Image Recipe",
                contentScale = ContentScale.Crop,
                modifier = modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )

            DetailContent(modifier, recipeEntity)
            DetailTabLayout(modifier = modifier, recipeEntity = recipeEntity, detailViewModel)
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun DetailTabLayout(
    modifier: Modifier,
    recipeEntity: RecipeEntity,
    detailViewModel: DetailRecipeViewModel
) {
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    val tabRowItems = stringArrayResource(id = R.array.tab_detail_recipe)

    Column() {
        CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
            ScrollableTabRow(
                modifier = modifier
                    .padding(vertical = 16.dp, horizontal = 24.dp)
                    .align(Alignment.Start),
                selectedTabIndex = pagerState.currentPage,
                backgroundColor = Color.Transparent,
                edgePadding = 0.dp,
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
                        modifier = modifier.background(color = BackgroundColor).height(40.dp),
                        text = {
                            Text(
                                text = item,
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
                                ),
                                color = if (selected) BlackColor500 else GreyColor300,
                            )
                        },
                    )
                }
            }
            HorizontalPager(
                count = tabRowItems.size,
                state = pagerState,
            ) {
                when (pagerState.currentPage) {
                    0 -> IngredientsList(modifier = modifier, recipeEntity = recipeEntity)
                    1 -> StepsCookList(modifier = modifier, recipeEntity = recipeEntity)
                    2 -> CommentsList(
                        modifier = modifier,
                        recipeEntity = recipeEntity,
                        detailViewModel = detailViewModel
                    )
                }
            }
        }
    }
}


@Composable
fun DetailContent(modifier: Modifier, recipeEntity: RecipeEntity) {
    Column {
        Text(
            text = recipeEntity.title ?: "",
            style = TextStyle(
                color = BlackColor500,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
            ),
            modifier = modifier.padding(top = 16.dp, start = 24.dp),
        )

        Text(
            text = recipeEntity.publisher ?: "",
            style = TextStyle(
                color = GreyColor500,
                fontSize = 16.sp,
            ),
            modifier = modifier.padding(top = 6.dp, start = 24.dp),
        )

        Text(
            text = "Tentang",
            style = TextStyle(
                color = BlackColor500,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
            ),
            modifier = modifier.padding(top = 16.dp, start = 24.dp),
        )

        Text(
            text = recipeEntity.description ?: "",
            style = TextStyle(
                color = GreyColor500,
                fontSize = 16.sp,
            ),
            lineHeight = 26.sp,
            modifier = modifier
                .padding(top = 16.dp, start = 24.dp)
                .fillMaxWidth(),
        )
    }
}
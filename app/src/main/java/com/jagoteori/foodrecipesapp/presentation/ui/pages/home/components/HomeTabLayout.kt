package com.jagoteori.foodrecipesapp.presentation.ui.pages.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.jagoteori.foodrecipesapp.R
import com.jagoteori.foodrecipesapp.domain.entity.RecipeEntity
import com.jagoteori.foodrecipesapp.presentation.extention.NoRippleTheme
import com.jagoteori.foodrecipesapp.presentation.ui.pages.home.ScreenPager
import com.jagoteori.foodrecipesapp.presentation.ui.theme.BackgroundColor
import com.jagoteori.foodrecipesapp.presentation.ui.theme.BlackColor500
import com.jagoteori.foodrecipesapp.presentation.ui.theme.GreyColor100
import kotlinx.coroutines.launch
import java.util.*


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

    Column {
        CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
            ScrollableTabRow(
                modifier = modifier.padding(vertical = 16.dp, horizontal = 24.dp),
                selectedTabIndex = pagerState.currentPage,
                edgePadding = 0.dp,
                backgroundColor = BackgroundColor,
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
                            .height(40.dp)
                            .background(
                                color = BlackColor500,
                                shape = RoundedCornerShape(8.dp)
                            )
                        else modifier
                            .height(40.dp)
                            .background(color = BackgroundColor),
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
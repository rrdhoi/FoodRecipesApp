package com.jagoteori.foodrecipesapp.presentation.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.jagoteori.foodrecipesapp.DummyData.errorMessage
import com.jagoteori.foodrecipesapp.DummyData.listRecipeEntity
import com.jagoteori.foodrecipesapp.DummyData.recipeEntity
import com.jagoteori.foodrecipesapp.LiveDataTestUtil.captureValues
import com.jagoteori.foodrecipesapp.data.Resource
import com.jagoteori.foodrecipesapp.domain.entity.RecipeEntity
import com.jagoteori.foodrecipesapp.domain.usecase.RecipeUseCase
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.amshove.kluent.shouldBeEqualTo
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val standardTestDispatcher = StandardTestDispatcher()

    private val useCase: RecipeUseCase = mockk()
    private lateinit var homeViewModel: HomeViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(standardTestDispatcher)
        homeViewModel = HomeViewModel(useCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        standardTestDispatcher.cancel()
    }

    @Test
    fun testAssertionDataRecipe() = runTest {
        val dummyRecipe = Resource.Success(listRecipeEntity)
        coEvery {
            useCase.getAllRecipes()
        } returns flowOf(dummyRecipe)

        val liveDataRecipe = homeViewModel.allRecipes

        liveDataRecipe.captureValues {
            advanceUntilIdle()

            coVerify { useCase.getAllRecipes() }

            values.first()?.data shouldBeEqualTo arrayListOf(dummyRecipe).first().data
        }
    }

    @Test
    fun testAssertionRecipeById() = runTest {
        val dummyRecipe = Resource.Success(recipeEntity)

        coEvery {
            useCase.getRecipeById("1")
        } returns dummyRecipe

        homeViewModel.getRecipeById("1")
        val liveDataRecipe = homeViewModel.getRecipe
        liveDataRecipe.captureValues {
            advanceUntilIdle()

            coVerify { useCase.getRecipeById("1") }

            values.first()?.data shouldBeEqualTo dummyRecipe.data
        }
    }

    @Test
    fun testAssertionAddRecipe() = runTest {
        val dummyResult = Resource.Success(errorMessage)

        coEvery {
            useCase.addRecipe(recipeEntity)
        } returns dummyResult

        homeViewModel.addRecipe(recipeEntity)
        val liveDataAddRecipe = homeViewModel.addRecipe
        liveDataAddRecipe.captureValues {
            advanceUntilIdle()

            coVerify { useCase.addRecipe(recipeEntity) }

            values.first()?.data shouldBeEqualTo dummyResult.data
        }
    }
}
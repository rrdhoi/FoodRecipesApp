package com.jagoteori.foodrecipesapp.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.testIn
import com.jagoteori.foodrecipesapp.DummyData.errorMessage
import com.jagoteori.foodrecipesapp.DummyData.listRecipeEntity
import com.jagoteori.foodrecipesapp.DummyData.recipeEntity
import com.jagoteori.foodrecipesapp.DummyData.successMessage
import com.jagoteori.foodrecipesapp.data.Resource
import com.jagoteori.foodrecipesapp.domain.entity.RecipeEntity
import com.jagoteori.foodrecipesapp.domain.repository.RecipeRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.amshove.kluent.shouldBeEqualTo
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RecipeUseCaseTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val mockRepository: RecipeRepository = mockk()

    private val standardTestDispatcher = StandardTestDispatcher()
    private lateinit var useCase: RecipeUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(standardTestDispatcher)
        useCase = RecipeInteractor(mockRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        standardTestDispatcher.cancel()
    }

    @Test
    fun `should get list of recipe from repository getAllRecipe()`() = runTest {
        val dataFlow = flowOf(Resource.Success(listRecipeEntity))
        coEvery {
            useCase.getAllRecipes()
        } returns dataFlow

        val result = useCase.getAllRecipes().testIn(backgroundScope)

        dataFlow.first() shouldBeEqualTo result.awaitItem()
    }

    @Test
    fun `should get error from repository getAllRecipe()`() = runTest {
        val dataFlow = flowOf(Resource.Error<List<RecipeEntity>>(errorMessage))
        coEvery {
            useCase.getAllRecipes()
        } returns dataFlow

        val result = useCase.getAllRecipes().testIn(backgroundScope)

        dataFlow.first() shouldBeEqualTo result.awaitItem()
    }

    @Test
    fun `should empty list from repository getAllRecipe()`() = runTest {
        val dataFlow = flowOf(Resource.Success<List<RecipeEntity>>(emptyList()))
        coEvery {
            useCase.getAllRecipes()
        } returns dataFlow

        val result = useCase.getAllRecipes().testIn(backgroundScope)

        dataFlow.first() shouldBeEqualTo result.awaitItem()
    }

    @Test
    fun `should get recipe from repository getRecipeById()`() = runTest {
        val data = Resource.Success(recipeEntity)
        coEvery {
            useCase.getRecipeById("1")
        } returns data

        val result = useCase.getRecipeById("1")

        data.data shouldBeEqualTo result.data
    }

    @Test
    fun `should get success message from repository addRecipe()`() = runTest {
        val data = Resource.Success(successMessage)
        coEvery {
            useCase.addRecipe(recipeEntity)
        } returns data

        val result = useCase.addRecipe(recipeEntity)

        data.data shouldBeEqualTo result.data
    }
}
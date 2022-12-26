package com.jagoteori.foodrecipesapp.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.jagoteori.foodrecipesapp.DummyData.errorMessage
import com.jagoteori.foodrecipesapp.DummyData.listRecipeEntity
import com.jagoteori.foodrecipesapp.DummyData.listRecipeModel
import com.jagoteori.foodrecipesapp.DummyData.notFoundRecipeMessage
import com.jagoteori.foodrecipesapp.DummyData.recipeEntity
import com.jagoteori.foodrecipesapp.DummyData.recipeModel
import com.jagoteori.foodrecipesapp.DummyData.successMessage
import com.jagoteori.foodrecipesapp.data.model.DataMapper
import com.jagoteori.foodrecipesapp.data.model.RecipeModel
import com.jagoteori.foodrecipesapp.data.source.remote.ApiResponse
import com.jagoteori.foodrecipesapp.data.source.remote.RemoteDataSource
import com.jagoteori.foodrecipesapp.domain.repository.RecipeRepository
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.*
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeInstanceOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RecipeRepositoryTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val mockRemoteDataSource: RemoteDataSource = mockk()

    private val standardTestDispatcher = StandardTestDispatcher()

    private lateinit var recipeRepository: RecipeRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(standardTestDispatcher)
        recipeRepository = RecipeRepositoryImpl(mockRemoteDataSource)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        standardTestDispatcher.cancel()
    }

    @Test
    fun `getAllRecipes should fetch the recipes when response is successful`() = runTest {
        val successResponse = ApiResponse.Success(listRecipeModel)
        val resourceData = Resource.Success(listRecipeEntity)

        coEvery {
            mockRemoteDataSource.getAllRecipes()
        } returns flowOf(successResponse)

        recipeRepository.getAllRecipes().test {
            val first = awaitItem()
            val second = awaitItem()

            first shouldBeInstanceOf Resource.Loading::class.java
            second.data shouldBeEqualTo resourceData.data

            awaitComplete()
        }
    }

    @Test
    fun `getAllRecipes should fetch error when response is error`() = runTest {
        val errorDataResponse = ApiResponse.Error(errorMessage)
        coEvery {
            mockRemoteDataSource.getAllRecipes()
        } returns flowOf(errorDataResponse)

        recipeRepository.getAllRecipes().test {
            val first = awaitItem()
            val second = awaitItem()

            first shouldBeInstanceOf Resource.Loading::class.java
            second.message shouldBeEqualTo errorDataResponse.errorMessage

            awaitComplete()
        }
    }

    @Test
    fun `getAllRecipes should fetch empty data when response is empty`() = runTest {
        val emptyList = emptyList<RecipeModel>()
        val emptyResponse = ApiResponse.Empty
        coEvery {
            mockRemoteDataSource.getAllRecipes()
        } returns flowOf(emptyResponse)

        recipeRepository.getAllRecipes().test {
            val first = awaitItem()
            val second = awaitItem()

            first shouldBeInstanceOf Resource.Loading::class.java
            second.data shouldBeEqualTo emptyList

            awaitComplete()
        }
    }

    @Test
    fun `getRecipeById should fetch recipe when response is successful`() = runTest {
        val successResponse = ApiResponse.Success(recipeModel)
        val successResource = Resource.Success(recipeEntity)

        coEvery {
            mockRemoteDataSource.getRecipeById("1")
        } returns successResponse

        val result = recipeRepository.getRecipeById("1")

        result.data shouldBeEqualTo successResource.data
    }

    @Test
    fun `getRecipeById should fetch error message when response is error`() = runTest {
        val errorResponse = ApiResponse.Error(errorMessage)

        coEvery {
            mockRemoteDataSource.getRecipeById("1")
        } returns errorResponse

        val result = recipeRepository.getRecipeById("1")

        result.message shouldBeEqualTo errorMessage
    }

    @Test
    fun `getRecipeById should fetch empty data when response is empty`() = runTest {
        val emptyResponse = ApiResponse.Empty

        coEvery {
            mockRemoteDataSource.getRecipeById("1")
        } returns emptyResponse

        val result = recipeRepository.getRecipeById("1")

        result.message shouldBeEqualTo notFoundRecipeMessage
    }

    @Test
    fun `addRecipe should fetch success message when response is successful`() = runTest {
        val successMessage = ApiResponse.Success(successMessage)

        coEvery {
            mockRemoteDataSource.addRecipe(recipeModel)
        } returns successMessage

        val result = recipeRepository.addRecipe(DataMapper.recipeModelToEntity(recipeModel))

        result.data shouldBeEqualTo successMessage.data
    }

    @Test
    fun `addRecipe should fetch error message when response is error`() = runTest {
        val errorMessage = ApiResponse.Error(errorMessage)

        coEvery {
            mockRemoteDataSource.addRecipe(recipeModel)
        } returns errorMessage

        val result = recipeRepository.addRecipe(DataMapper.recipeModelToEntity(recipeModel))

        result.message shouldBeEqualTo errorMessage.errorMessage
    }
}

/*
@OptIn(DelicateCoroutinesApi::class, ExperimentalCoroutinesApi::class)
suspend fun <T> Flow<T>.testWithScheduler(
    timeoutMs: Long = 1000,
    validate: suspend ReceiveTurbine<T>.() -> Unit
) {
    val testScheduler = coroutineContext[TestCoroutineScheduler]
    return if (testScheduler == null) {
        test(1000.toDuration(DurationUnit.SECONDS),"Null Test Scheduler", validate)
    } else {
        flowOn(UnconfinedTestDispatcher(testScheduler))
            .test(1000.toDuration(DurationUnit.SECONDS), "On Test Scheduler",validate)
    }
}*/

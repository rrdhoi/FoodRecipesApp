package com.jagoteori.foodrecipesapp.data.remote

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.firebase.firestore.QuerySnapshot
import com.jagoteori.foodrecipesapp.data.model.RecipeModel
import com.jagoteori.foodrecipesapp.data.source.remote.ApiResponse
import com.jagoteori.foodrecipesapp.data.source.remote.RemoteDataSource
import com.jagoteori.foodrecipesapp.data.source.remote.RemoteDataSourceImpl
import com.jagoteori.foodrecipesapp.data.source.remote.firestore.FirestoreQuery
import com.jagoteori.foodrecipesapp.data.source.remote.firestore.ResponseCallBack
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.kotlin.*

@OptIn(ExperimentalCoroutinesApi::class)
class RemoteDataSourceTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val mockFirestoreQuery: FirestoreQuery = mock()

    private val recipeModel: RecipeModel = mock()
    private val listRecipeModel: List<RecipeModel> = mock()

    private val mockQuerySnapshot: QuerySnapshot = mock()

    private val standardTestDispatcher = StandardTestDispatcher()
    private lateinit var remoteDataSource: RemoteDataSource

    @Before
    fun setUp() {
        Dispatchers.setMain(standardTestDispatcher)
        remoteDataSource = RemoteDataSourceImpl(mockFirestoreQuery)
    }


    @After
    fun tearDown() {
        Dispatchers.resetMain()
        standardTestDispatcher.cancel()
    }

    @Test
    fun getRecipes() = runTest {
       /* doAnswer { invocation ->
            (invocation.arguments[1] as ResponseCallBack)
                .onCallBackSuccess(mockQuerySnapshot)
            null
        }.`when`(mockFirestoreQuery).getAllRecipe(any())
*/
        // TODO:: Cari tau cara membuat callbackflow menjalankan fungsi didalamnya
        remoteDataSource.getAllRecipes()

        verify(mockFirestoreQuery).getAllRecipe(any())
        verifyNoInteractions(mockFirestoreQuery)
    }

    @Test
    fun getRecipeById() = runTest {
        remoteDataSource.getRecipeById("1")

        verify(mockFirestoreQuery).getRecipeById("1")
        verifyNoMoreInteractions(mockFirestoreQuery)
    }

    @Test
    fun addRecipe() = runTest {
        remoteDataSource.addRecipe(recipeModel)

        verify(mockFirestoreQuery).addRecipe(recipeModel)
        verifyNoMoreInteractions(mockFirestoreQuery)
    }
}
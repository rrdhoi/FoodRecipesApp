package com.jagoteori.foodrecipesapp.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.jagoteori.foodrecipesapp.data.RecipeRepositoryImpl
import com.jagoteori.foodrecipesapp.data.source.remote.RemoteDataSource
import com.jagoteori.foodrecipesapp.data.source.remote.RemoteDataSourceImpl
import com.jagoteori.foodrecipesapp.domain.repository.RecipeRepository
import com.jagoteori.foodrecipesapp.domain.usecase.RecipeInteractor
import com.jagoteori.foodrecipesapp.domain.usecase.RecipeUseCase
import com.jagoteori.foodrecipesapp.presentation.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val firebaseModule = module {
    single { FirebaseFirestore.getInstance() }
    single { FirebaseAuth.getInstance() }
    single { FirebaseStorage.getInstance() }
}

val repositoryModule = module {
    single<RemoteDataSource> { RemoteDataSourceImpl(get()) }
    single<RecipeRepository> { RecipeRepositoryImpl(get()) }
}

val useCaseModule = module {
    factory<RecipeUseCase> { RecipeInteractor(get()) }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
}
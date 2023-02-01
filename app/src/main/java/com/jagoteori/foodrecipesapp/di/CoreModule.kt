package com.jagoteori.foodrecipesapp.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.jagoteori.foodrecipesapp.data.RecipeRepositoryImpl
import com.jagoteori.foodrecipesapp.data.source.remote.RemoteDataSource
import com.jagoteori.foodrecipesapp.data.source.remote.RemoteDataSourceImpl
import com.jagoteori.foodrecipesapp.data.source.remote.firestore.FirestoreQuery
import com.jagoteori.foodrecipesapp.domain.repository.RecipeRepository
import com.jagoteori.foodrecipesapp.domain.usecase.RecipeInteractor
import com.jagoteori.foodrecipesapp.domain.usecase.RecipeUseCase
import com.jagoteori.foodrecipesapp.presentation.ui.pages.add_recipe.view_model.AddRecipeViewModel
import com.jagoteori.foodrecipesapp.presentation.ui.pages.sign_in.view_model.SignInViewModel
import com.jagoteori.foodrecipesapp.presentation.ui.pages.sign_up.view_model.SignUpViewModel
import com.jagoteori.foodrecipesapp.presentation.ui.pages.detail_recipe.view_model.DetailRecipeViewModel
import com.jagoteori.foodrecipesapp.presentation.ui.pages.home.view_model.HomeViewModel
import com.jagoteori.foodrecipesapp.presentation.ui.pages.profile.view_model.ProfileViewModel
import com.jagoteori.foodrecipesapp.presentation.ui.pages.profile.view_model.MyRecipesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val firebaseModule = module {
    single { FirebaseFirestore.getInstance() }
    single { FirebaseAuth.getInstance() }
    single { FirebaseStorage.getInstance() }
}

val remoteDataSource = module {
    single { FirestoreQuery(get(), get(), get()) }
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
    viewModel { AddRecipeViewModel(get()) }
    viewModel { DetailRecipeViewModel(get()) }
    viewModel { SignUpViewModel(get()) }
    viewModel { SignInViewModel(get()) }
    viewModel { ProfileViewModel(get()) }
    viewModel { MyRecipesViewModel(get()) }
}
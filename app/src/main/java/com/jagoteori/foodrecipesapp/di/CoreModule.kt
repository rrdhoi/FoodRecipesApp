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
import com.jagoteori.foodrecipesapp.presentation.add_recipe.AddRecipeViewModel
import com.jagoteori.foodrecipesapp.presentation.auth.sign_in.SignInViewModel
import com.jagoteori.foodrecipesapp.presentation.auth.sign_up.SignUpViewModel
import com.jagoteori.foodrecipesapp.presentation.detail_recipe.DetailRecipeViewModel
import com.jagoteori.foodrecipesapp.presentation.detail_recipe.comments.CommentsViewModel
import com.jagoteori.foodrecipesapp.presentation.home.CategoryViewModel
import com.jagoteori.foodrecipesapp.presentation.home.HomeViewModel
import com.jagoteori.foodrecipesapp.presentation.profile.ProfileViewModel
import com.jagoteori.foodrecipesapp.presentation.profile.my_recipes.MyRecipesViewModel
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
    viewModel { CommentsViewModel(get()) }
    viewModel { SignUpViewModel(get()) }
    viewModel { SignInViewModel(get()) }
    viewModel { ProfileViewModel(get()) }
    viewModel { MyRecipesViewModel(get()) }
    viewModel { CategoryViewModel(get()) }
}
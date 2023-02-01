package com.jagoteori.foodrecipesapp.presentation.ui.pages.profile.view_model

import android.content.Intent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.jagoteori.foodrecipesapp.data.Resource
import com.jagoteori.foodrecipesapp.domain.entity.UserEntity
import com.jagoteori.foodrecipesapp.domain.usecase.RecipeUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ProfileViewModel(private val recipeUseCase: RecipeUseCase) : ViewModel() {
    var myUser by mutableStateOf<Resource<UserEntity>>(Resource.Loading())

    var imagePickerDialogState by mutableStateOf(false)

    init {
        getMyUser()
    }

    fun getMyUser() {
        viewModelScope.launch(Dispatchers.Main) {
            myUser = recipeUseCase.getMyUser()
        }
    }

    fun signOut() {
        FirebaseAuth.getInstance().signOut()
    }

    fun updateUser(userEntity: UserEntity) = viewModelScope.launch(Dispatchers.IO) {
        recipeUseCase.updateUser(userEntity)
    }
}
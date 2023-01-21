package com.jagoteori.foodrecipesapp.presentation.detail_recipe

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jagoteori.foodrecipesapp.app.extention.isNotErrorHandler
import com.jagoteori.foodrecipesapp.data.Resource
import com.jagoteori.foodrecipesapp.domain.entity.CommentEntity
import com.jagoteori.foodrecipesapp.domain.entity.RecipeEntity
import com.jagoteori.foodrecipesapp.domain.entity.UserEntity
import com.jagoteori.foodrecipesapp.domain.usecase.RecipeUseCase
import com.jagoteori.foodrecipesapp.presentation.ui.UiState
import com.jagoteori.foodrecipesapp.presentation.ui.extention.isEmptyOrBlank
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailRecipeViewModel(private val useCase: RecipeUseCase) : ViewModel() {
    var addComment by mutableStateOf(TextFieldValue(""))
    var addCommentError by mutableStateOf(false)
    var addCommentErrorMessage by mutableStateOf("")

    private var _user : UserEntity? = null

    fun getMyUser() = viewModelScope.launch(Dispatchers.Main) {
        val user = useCase.getMyUser()
        if (user is Resource.Success) {
            _user = user.data
        }
    }

    fun addComment(recipeId: String) =
        viewModelScope.launch(Dispatchers.Main) {
           if (_user != null) {
               val comment = CommentEntity(
                   id = null,
                   name = _user?.name,
                   message = addComment.text,
                   profilePicture = _user?.profilePicture
               )
               useCase.addComment(recipeId, comment)
           }
        }

    fun checkCommentFormIsInvalid(): Boolean {
        val isNotCommentValid = addComment.isEmptyOrBlank(title = "komentar") {
            addCommentError = true
            addCommentErrorMessage = it
        }.isNotErrorHandler {
            addCommentError = false
        }

        return isNotCommentValid
    }

}

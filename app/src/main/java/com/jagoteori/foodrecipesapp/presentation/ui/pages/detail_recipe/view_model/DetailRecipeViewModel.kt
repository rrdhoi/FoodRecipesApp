package com.jagoteori.foodrecipesapp.presentation.ui.pages.detail_recipe.view_model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jagoteori.foodrecipesapp.app.extention.isNotErrorHandler
import com.jagoteori.foodrecipesapp.data.Resource
import com.jagoteori.foodrecipesapp.domain.entity.CommentEntity
import com.jagoteori.foodrecipesapp.domain.entity.RecipeEntity
import com.jagoteori.foodrecipesapp.domain.entity.UserEntity
import com.jagoteori.foodrecipesapp.domain.usecase.RecipeUseCase
import com.jagoteori.foodrecipesapp.presentation.extention.isEmptyOrBlank
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailRecipeViewModel(private val useCase: RecipeUseCase) : ViewModel() {
    var addComment by mutableStateOf(TextFieldValue(""))
    private var addCommentError by mutableStateOf(false)
    private var addCommentErrorMessage by mutableStateOf("")

    private var _user: UserEntity? = null
    var recipeEntity by mutableStateOf(RecipeEntity())

    fun setRecipe(newRecipe: RecipeEntity) {
        recipeEntity = newRecipe
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

    init {
        viewModelScope.launch(Dispatchers.Main) {
            val user = useCase.getMyUser()
            if (user is Resource.Success) {
                _user = user.data
            }
        }
    }

}

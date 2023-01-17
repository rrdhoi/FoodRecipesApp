package com.jagoteori.foodrecipesapp.presentation.detail_recipe.comments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jagoteori.foodrecipesapp.data.Resource
import com.jagoteori.foodrecipesapp.domain.entity.CommentEntity
import com.jagoteori.foodrecipesapp.domain.usecase.RecipeUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CommentsViewModel(private val useCase: RecipeUseCase) : ViewModel() {
    private val _addComment = MutableLiveData<Resource<String>>()
    val addComment: LiveData<Resource<String>> get() = _addComment

    fun addComment(recipeId: String, commentEntity: CommentEntity) =
        viewModelScope.launch(Dispatchers.Main) {
            _addComment.postValue(useCase.addComment(recipeId, commentEntity))
        }
}
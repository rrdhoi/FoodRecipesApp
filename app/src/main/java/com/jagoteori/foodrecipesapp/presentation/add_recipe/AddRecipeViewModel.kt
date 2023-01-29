package com.jagoteori.foodrecipesapp.presentation.add_recipe

import android.net.Uri
import androidx.compose.runtime.*
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jagoteori.foodrecipesapp.data.Resource
import com.jagoteori.foodrecipesapp.domain.entity.IngredientEntity
import com.jagoteori.foodrecipesapp.domain.entity.RecipeEntity
import com.jagoteori.foodrecipesapp.domain.entity.StepCookEntity
import com.jagoteori.foodrecipesapp.domain.entity.UserEntity
import com.jagoteori.foodrecipesapp.domain.usecase.RecipeUseCase
import com.jagoteori.foodrecipesapp.presentation.ui.pages.add_recipe.ItemIngredient
import com.jagoteori.foodrecipesapp.presentation.ui.pages.add_recipe.ItemStepCook
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddRecipeViewModel(private val useCase: RecipeUseCase) : ViewModel() {
    private val _addRecipe = MutableLiveData<Resource<String>>()
    val addRecipe: LiveData<Resource<String>> get() = _addRecipe

    private var _getMyUser = MutableLiveData<Resource<UserEntity>>()
    private val myUser: LiveData<Resource<UserEntity>> get() = _getMyUser

    var title by mutableStateOf(TextFieldValue(""))
    var titleError by mutableStateOf(false)
    var titleErrorMessage by mutableStateOf("")

    var category by mutableStateOf(TextFieldValue(""))
    var categoryError by mutableStateOf(false)
    var categoryErrorMessage by mutableStateOf("")

    var description by mutableStateOf(TextFieldValue(""))
    var descriptionError by mutableStateOf(false)
    var descriptionErrorMessage by mutableStateOf("")

    var listIngredientFormSize by mutableStateOf(1)
    var listItemIngredientForm = mutableStateListOf<MutableState<ItemIngredient>>()

    var listStepCookFormSize by mutableStateOf(1)
    var listItemStepCookForm = mutableStateListOf<MutableState<ItemStepCook>>()

    var imageRecipe by mutableStateOf("")

    var isLoading by mutableStateOf(false)

    var imagePickerDialogState by mutableStateOf(false)

    fun getMyUser() = viewModelScope.launch(Dispatchers.Main) {
        _getMyUser.value = useCase.getMyUser()
    }

    fun onSubmitRecipe() {
        isLoading = true
        val listIngredient = mutableListOf<IngredientEntity>()
        val listStepCook = mutableListOf<StepCookEntity>()

        listItemIngredientForm.forEach { listItem ->
            val ingredientEntity = IngredientEntity(
                ingredient = listItem.value.ingredient?.value?.text,
                quantity = listItem.value.quantity?.value?.text,
                typeQuantity = listItem.value.typeQuantity?.value,
            )

            listIngredient.add(ingredientEntity)
        }

        listItemStepCookForm.forEachIndexed { index, listItem ->
            val itemStepCook = StepCookEntity(
                stepNumber = index + 1,
                stepDescription = listItem.value.description?.value?.text,
                stepImages = listItem.value.listImageUri?.map { uri: Uri? -> uri.toString() }
                    ?.toList()
            )

            listStepCook.add(itemStepCook)
        }

        val recipeEntity = RecipeEntity(
            id = null,
            title = title.text,
            category = category.text,
            description = description.text,
            publisherId = myUser.value?.data?.userId,
            publisher = myUser.value?.data?.name,
            recipePicture = imageRecipe,
            listComments = null,
            listIngredients = listIngredient,
            listStepCooking = listStepCook
        )

        viewModelScope.launch(Dispatchers.IO) {
            useCase.addRecipe(recipeEntity)
        }

        isLoading = false
    }

    init {
        getMyUser()
    }
}
package com.jagoteori.foodrecipesapp.data.model

data class RecipeModel(
    var id: String? = null,
    var title: String? = null,
    var category: String? = null,
    var description: String? = null,
    var publisherId: String? = null,
    var publisher: String? = null,
    var recipePicture: String? = null,
    val listIngredients: List<IngredientModel>? = null,
    val listStepCooking: List<StepCookModel>? = null,
    var listComments: List<CommentModel>? = null
)
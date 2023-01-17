package com.jagoteori.foodrecipesapp.app.utils

import com.jagoteori.foodrecipesapp.data.model.*
import com.jagoteori.foodrecipesapp.domain.entity.*

object DataMapper {
    fun recipeModelToEntity(model: RecipeModel): RecipeEntity = RecipeEntity(
        id = model.id,
        title = model.title,
        description = model.description,
        category = model.category,
        publisherId = model.publisherId,
        publisher = model.publisher,
        recipePicture = model.recipePicture,
        listIngredients = model.listIngredients?.map { ingredientModelToEntity(it) }?.toList(),
        listStepCooking = model.listStepCooking?.map { stepCookModelToEntity(it) }?.toList(),
        listComments = model.listComments?.map { commentModelToEntity(it) }?.toList()
    )

    fun recipeEntityToModel(entity: RecipeEntity) = RecipeModel(
        id = entity.id,
        title = entity.title,
        category = entity.category,
        description = entity.description,
        publisherId = entity.publisherId,
        publisher = entity.publisher,
        recipePicture = entity.recipePicture,
        listIngredients = entity.listIngredients?.map { ingredientEntityToModel(it) }?.toList(),
        listStepCooking = entity.listStepCooking?.map { stepCookEntityToModel(it) }?.toList(),
        listComments = entity.listComments?.map { commentEntityToModel(it) }?.toList(),
    )

    fun userEntityToModel(model: UserEntity) = UserModel(
        userId = model.userId,
        name = model.name,
        profilePicture = model.profilePicture,
        email = model.email,
    )

    fun userModelToEntity(entity: UserModel) = UserEntity(
        userId = entity.userId,
        name = entity.name,
        profilePicture = entity.profilePicture,
        email = entity.email,
    )

    private fun ingredientEntityToModel(entity: IngredientEntity) = IngredientModel(
        ingredient = entity.ingredient,
        quantity = entity.quantity,
        typeQuantity = entity.typeQuantity,
    )

    private fun ingredientModelToEntity(model: IngredientModel) = IngredientEntity(
        ingredient = model.ingredient,
        quantity = model.quantity,
        typeQuantity = model.typeQuantity,
    )

    private fun stepCookEntityToModel(entity: StepCookEntity) = StepCookModel(
        stepNumber = entity.stepNumber,
        stepDescription = entity.stepDescription,
        stepImages = entity.stepImages,
    )

    private fun stepCookModelToEntity(model: StepCookModel) = StepCookEntity(
        stepNumber = model.stepNumber,
        stepDescription = model.stepDescription,
        stepImages = model.stepImages,
    )

    fun commentEntityToModel(entity: CommentEntity) = CommentModel(
        id = entity.id,
        name = entity.name,
        message = entity.message,
        profilePicture = entity.profilePicture
    )

    private fun commentModelToEntity(model: CommentModel) = CommentEntity(
        id = model.id,
        name = model.name,
        message = model.message,
        profilePicture = model.profilePicture
    )
}
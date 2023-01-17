package com.jagoteori.foodrecipesapp.data.source.remote.firestore

import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.FirebaseStorage
import com.jagoteori.foodrecipesapp.data.model.CommentModel
import com.jagoteori.foodrecipesapp.data.model.RecipeModel
import com.jagoteori.foodrecipesapp.data.model.StepCookModel
import com.jagoteori.foodrecipesapp.data.model.UserModel
import com.jagoteori.foodrecipesapp.data.source.remote.await

class FirestoreQuery(
    private val firestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth,
    private val storage: FirebaseStorage
) : IFirestoreQuery {
    override fun getAllRecipe(callBack: ResponseCallBack) =
        firestore.collection("recipes").addSnapshotListener { snapshot, error ->
            when {
                error != null -> callBack.onCallBackError(error.message.toString())
                snapshot != null -> {
                    callBack.onCallBackSuccess(snapshot)
                }
                else -> callBack.onCallBackEmpty()
            }
        }

    override fun getCategoryRecipe(category: String) =
        firestore.collection("recipes").whereEqualTo("category", category).get()

    override fun getComments(recipeId: String): Task<QuerySnapshot> =
        firestore.collection("recipes").document(recipeId).collection("comments").get()

    override fun getRecipeById(recipeId: String) =
        firestore.collection("recipes").document(recipeId).get()

    override fun getMyRecipes() =
        firestore.collection("recipes").whereEqualTo("publisherId", firebaseAuth.uid).get()

    override fun getMyUser() =
        firestore.collection("users").document(firebaseAuth.uid!!).get()

    override suspend fun addRecipe(recipe: RecipeModel): Task<Void> {
        val createDocument = firestore.collection("recipes").document()
        val newListStepCook = ArrayList<StepCookModel>()

        val refImageRecipe =
            storage.getReference("/${recipe.publisherId}/recipes/${createDocument.id}")

        val uploadRecipeImageTask = refImageRecipe.putFile(Uri.parse(recipe.recipePicture))

        uploadRecipeImageTask.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw Throwable("Failed to upload image recipe")
                }
            }
            refImageRecipe.downloadUrl.addOnSuccessListener {
                recipe.recipePicture = it.toString()
            }.addOnFailureListener {
                throw Throwable("Failed to upload image recipe")
            }
        }.await()

        // upload step cook image to storage
        if (!recipe.listStepCooking.isNullOrEmpty()) {
            for (stepCook in recipe.listStepCooking) {
                val refStepCookImage =
                    storage.getReference("/${recipe.publisherId}/recipes/${createDocument.id}/cook_steps/${stepCook.stepNumber}")

                if (!stepCook.stepImages.isNullOrEmpty()) {
                    val stepCookImages = ArrayList<String>()

                    for (image in stepCook.stepImages) {
                        val uploadStepCookTask = refStepCookImage.putFile(Uri.parse(image))

                        uploadStepCookTask.continueWithTask { task ->
                            if (!task.isSuccessful) {
                                task.exception?.let {
                                    throw Throwable("Failed to upload image step cook recipe")
                                }
                            }
                            refStepCookImage.downloadUrl
                        }.addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val downloadUri = task.result.toString()
                                stepCookImages.add(downloadUri)
                            } else {
                                throw Throwable("Failed to upload image step cook recipe")
                            }
                        }.await()
                    }

                    val newStepCookImages = stepCook.copy(stepImages = stepCookImages)
                    newListStepCook.add(newStepCookImages)
                } else {
                    newListStepCook.add(stepCook)
                }
            }
        }

        return firestore.collection("recipes").document(createDocument.id)
            .set(recipe.copy(id = createDocument.id, listStepCooking = newListStepCook))
    }

    override suspend fun addComment(recipeId: String, commentModel: CommentModel): Task<Void> {
        val createDocument =
            firestore.collection("recipes").document(recipeId).collection("comments").document()

        return firestore.collection("recipes").document(recipeId).collection("comments")
            .document(createDocument.id).set(commentModel.copy(id = createDocument.id))
    }

    override fun userSignUp(userModel: UserModel, password: String): Task<AuthResult> =
        firebaseAuth.createUserWithEmailAndPassword(userModel.email!!, password)
            .addOnSuccessListener {
                if (firebaseAuth.uid.isNullOrBlank()) throw Throwable("Failed to sign up")

                firestore.collection("users").document(firebaseAuth.uid!!)
                    .set(userModel.copy(userId = firebaseAuth.uid))

            }.addOnFailureListener {
                throw Throwable(it.message)
            }

    override fun userSignIn(email: String, password: String): Task<AuthResult> =
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
            if (firebaseAuth.uid.isNullOrBlank()) throw Throwable("Failed to sign in")
        }.addOnFailureListener {
            Throwable(it.message)
        }

    override suspend fun updateUser(user: UserModel): Task<Void> {
        if (user.profilePicture != null) {
            val ref = storage.getReference("/${user.userId}/profile/image_profile")
            val uploadProfile = ref.putFile(Uri.parse(user.profilePicture))

            uploadProfile.addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw Throwable("Failed to upload image profile")
                    }
                }
                ref.downloadUrl.addOnSuccessListener {
                    user.profilePicture = it.toString()
                }.addOnFailureListener {
                    throw Throwable("Failed to upload image profile")
                }
            }.await()
        }

        val mapUser = mutableMapOf<String, Any>(
            "userId" to user.userId!!,
            "name" to user.name!!,
            "email" to user.email!!,
        )

        if (user.profilePicture != null) mapUser["profilePicture"] = user.profilePicture!!

        return firestore.collection("users").document(user.userId!!).update(mapUser)
    }

}
package com.jagoteori.foodrecipesapp.data.source.remote.firestore

import com.google.firebase.firestore.QuerySnapshot

interface ResponseCallBack {
    fun onCallBackSuccess(response: QuerySnapshot)
    fun onCallBackEmpty()
    fun onCallBackError(response: String)
}
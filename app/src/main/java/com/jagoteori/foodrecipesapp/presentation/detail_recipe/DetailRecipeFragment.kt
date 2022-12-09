package com.jagoteori.foodrecipesapp.presentation.detail_recipe

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jagoteori.foodrecipesapp.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailRecipeFragment : Fragment() {
    private val detailViewModel: DetailRecipeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.detail_receipe_fragment, container, false)
    }
}
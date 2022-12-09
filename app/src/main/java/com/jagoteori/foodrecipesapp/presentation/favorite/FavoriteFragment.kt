package com.jagoteori.foodrecipesapp.presentation.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jagoteori.foodrecipesapp.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteFragment : Fragment() {
    private val fragmentViewModel: FavoriteViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.favorite_fragment, container, false)
    }

}
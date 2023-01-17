package com.jagoteori.foodrecipesapp.presentation.home

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.jagoteori.foodrecipesapp.R
import com.jagoteori.foodrecipesapp.domain.entity.RecipeEntity
import java.util.*

class SectionsPagerCategoryAdapter(private val activity: FragmentActivity, private val listRecipe: List<RecipeEntity>) :
    FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        val fragment: Fragment?

        fragment = CategoryFragment(listRecipe = listRecipe.filter {
            it.category!!.capitalize(Locale.ROOT) == activity.resources.getStringArray(R.array.tab_category)[position]
        })
        return fragment
    }
}
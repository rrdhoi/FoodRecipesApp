package com.jagoteori.foodrecipesapp.presentation.detail_recipe

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.jagoteori.foodrecipesapp.domain.entity.RecipeEntity
import com.jagoteori.foodrecipesapp.presentation.detail_recipe.comments.CommentsFragment

class SectionsPagerAdapter(activity: AppCompatActivity, val recipeEntity: RecipeEntity) :
    FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment =
                IngredientsFragment(recipeEntity)
            1 -> fragment =
                StepsCookFragment(recipeEntity)
            2 -> fragment = CommentsFragment(recipeEntity)
        }
        return fragment as Fragment
    }
}
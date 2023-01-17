package com.jagoteori.foodrecipesapp.presentation.detail_recipe

import android.graphics.Color
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RectShape
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import com.jagoteori.foodrecipesapp.R
import com.jagoteori.foodrecipesapp.databinding.FragmentIngredientsBinding
import com.jagoteori.foodrecipesapp.domain.entity.RecipeEntity


class IngredientsFragment(val recipeEntity: RecipeEntity) : Fragment() {
    private lateinit var binding: FragmentIngredientsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentIngredientsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        // request layout for root to main container like Constraint/LinearLayout for wrap height fragment
        binding.root.requestLayout()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpLayout()

    }

    private fun setUpLayout() {
        if (recipeEntity.listIngredients.isNullOrEmpty()) return
        for (recipe in recipeEntity.listIngredients) {
            val itemIngredient =
                layoutInflater.inflate(R.layout.item_ingredient, binding.root, false)

            val tvIngredient = itemIngredient.findViewById<TextView>(R.id.tv_ingredient)
            val tvTypeQuantity = itemIngredient.findViewById<TextView>(R.id.tv_type_quantity)
            val tvQuantity = itemIngredient.findViewById<TextView>(R.id.tv_quantity)

            tvIngredient.text = recipe.ingredient
            tvTypeQuantity.text = recipe.typeQuantity
            tvQuantity.text = recipe.quantity

            binding.listIngredients.addView(itemIngredient)
            binding.listIngredients.addView(customViewDivider())

            itemIngredient.setPadding(0, 12, 0, 12)

            if (recipeEntity.listIngredients.indexOf(recipe) == 0) {
                itemIngredient.updatePadding(top = 0)
            }

        }
    }


    private fun customViewDivider(): View {
        val viewDivider = ImageView(context)

        viewDivider.background = createDashedLined()
        viewDivider.setLayerType(View.LAYER_TYPE_SOFTWARE, Paint())

        viewDivider.layoutParams =
            LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 5)

        return viewDivider
    }

    private fun createDashedLined(): Drawable {
        val sd = ShapeDrawable(RectShape())
        val fgPaintSel = sd.paint
        fgPaintSel.color = Color.BLACK
        fgPaintSel.style = Paint.Style.STROKE
        fgPaintSel.pathEffect = DashPathEffect(floatArrayOf(5f, 5f), 0F)
        return sd
    }
}
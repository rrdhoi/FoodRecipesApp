package com.jagoteori.foodrecipesapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.messaging.FirebaseMessaging
import com.jagoteori.foodrecipesapp.app.Constants.TOPIC
import com.jagoteori.foodrecipesapp.app.service.FirebaseService
import com.jagoteori.foodrecipesapp.databinding.ActivityMainBinding
import com.jagoteori.foodrecipesapp.presentation.add_recipe.AddRecipeActivity
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.plant(Timber.DebugTree())
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.floatingButtonAddRecipe.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    AddRecipeActivity::class.java
                )
            )
        }

        bottomNavigationSetUp()
        firebaseSetUp()
    }

    private fun bottomNavigationSetUp() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        val navController = navHostFragment.navController

        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)

        binding.bottomNavigationView.background = null
        binding.bottomNavigationView.menu.getItem(1).isEnabled = false

        Timber.d("is checked ${binding.bottomNavigationView.menu.getItem(2).isChecked}")
    }

    private fun firebaseSetUp() {
        FirebaseService.sharePreferences = getSharedPreferences("sharedPref", Context.MODE_PRIVATE)

        FirebaseMessaging.getInstance().apply {
            subscribeToTopic(TOPIC)
            token.addOnSuccessListener {
                val token: String = it
                FirebaseService.token = token
                Timber.tag("TOKEN FCM:::").d(token)
            }
        }
    }
}
package com.jagoteori.foodrecipesapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.jagoteori.foodrecipesapp.databinding.ActivityAuthBinding
import com.jagoteori.foodrecipesapp.databinding.LayoutToolbarBinding
import timber.log.Timber

class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding
    private lateinit var bindingToolBar: LayoutToolbarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        bindingToolBar = binding.toolbarMain
        setContentView(binding.root)

        Timber.plant(Timber.DebugTree())

        checkHasLogged()
        navigationSetUp()
    }

    private fun navigationSetUp() {
        val pageRequestSignUp = intent.getIntExtra(PAGE_AUTH_SIGNUP, 0)

        if (pageRequestSignUp == 1) {
            toolbarSignUp()
            val navOptions = NavOptions.Builder()
                .setPopUpTo(R.id.signInFragment, true)
                .build()
            Navigation.findNavController(findViewById(R.id.nav_host_fragment_auth))
                .navigate(R.id.action_signInFragment_to_signUpFragment, null, navOptions)
        }
    }

    private fun checkHasLogged(){
        val uid = FirebaseAuth.getInstance().uid

        if (uid != null) {
            val intent = Intent(this,
                MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun toolbarSignUp() {
        bindingToolBar.toolbar.title = "Buat Akun,"
        bindingToolBar.toolbar.subtitle = "Daftar untuk memulai!"
        bindingToolBar.toolbar.navigationIcon =
            ResourcesCompat.getDrawable(resources, R.drawable.ic_arrow_back, null)
        bindingToolBar.toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    companion object {
        const val PAGE_AUTH_SIGNUP = "AUTH_SIGNUP"
    }
}
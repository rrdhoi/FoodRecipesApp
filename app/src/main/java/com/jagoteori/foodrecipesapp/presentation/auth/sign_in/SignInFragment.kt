package com.jagoteori.foodrecipesapp.presentation.auth.sign_in

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.fragment.app.Fragment
import com.jagoteori.foodrecipesapp.AuthActivity
import com.jagoteori.foodrecipesapp.AuthActivity.Companion.PAGE_AUTH_SIGNUP
import com.jagoteori.foodrecipesapp.MainActivity
import com.jagoteori.foodrecipesapp.app.utils.firebaseAuthHandler
import com.jagoteori.foodrecipesapp.data.Resource
import com.jagoteori.foodrecipesapp.databinding.FragmentSignInBinding
import com.jagoteori.foodrecipesapp.presentation.ui.components.CustomTextField
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class SignInFragment : Fragment() {
    private val viewModel: SignInViewModel by viewModel()
    private lateinit var binding: FragmentSignInBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        Timber.plant(Timber.DebugTree())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.composeView.setContent {
            MaterialTheme {
                FormSignIn(modifier = Modifier)
            }
        }

        whenButtonSignUpClicked()
        submitRegistrationData()
        observeSignInUser()
    }

    private fun whenButtonSignUpClicked() {
        binding.btnGotoSignup.setOnClickListener {
            val signup = Intent(activity, AuthActivity::class.java)
            signup.putExtra(PAGE_AUTH_SIGNUP, 1)
            startActivity(signup)
        }
    }

    private fun submitRegistrationData() {
        with(binding) {
            btnSignIn.setOnClickListener {
                if (viewModel.checkFormIsValid()) {
                    viewModel.login()
                }
            }
        }
    }

    @Composable
    fun FormSignIn(modifier: Modifier) {
        Column {
            CustomTextField(
                title = "Email",
                modifier = modifier,
                value = viewModel.email,
                onValueChange = { viewModel.email = it },
                isError = viewModel.emailError,
                errorMessage = viewModel.emailErrorMessage
            )

            CustomTextField(
                title = "Password",
                modifier = modifier,
                value = viewModel.password,
                onValueChange = { viewModel.password = it },
                isError = viewModel.passwordError,
                errorMessage = viewModel.passwordErrorMessage,
                isPassword = true
            )
        }
    }

    private fun observeSignInUser() {
        viewModel.userSignIn.observe(viewLifecycleOwner) { signInResponse ->
            when (signInResponse) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    val intent = Intent(context, MainActivity::class.java)
                    startActivity(intent)

                    Timber.tag("Sign In Response ::").d(signInResponse.data)
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE

                    firebaseAuthHandler(context, signInResponse.message)

                    Timber.tag("Sign In Response ::").e(signInResponse.message)
                }
            }
        }
    }


}
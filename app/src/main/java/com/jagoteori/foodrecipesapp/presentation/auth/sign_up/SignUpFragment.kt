package com.jagoteori.foodrecipesapp.presentation.auth.sign_up

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.fragment.app.Fragment
import com.jagoteori.foodrecipesapp.MainActivity
import com.jagoteori.foodrecipesapp.data.Resource
import com.jagoteori.foodrecipesapp.databinding.FragmentSignUpBinding
import com.jagoteori.foodrecipesapp.presentation.ui.components.CustomTextField
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class SignUpFragment : Fragment() {
    private val viewModel: SignUpViewModel by viewModel()
    private lateinit var binding: FragmentSignUpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
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

        submitRegistrationData()
        observeSignUpUser()
    }


    private fun submitRegistrationData() {
        with(binding) {
            btnSignUp.setOnClickListener {
                if (!viewModel.checkFormIsInvalid()) {
                    viewModel.createUserAccount()
                }
            }
        }
    }

    @Composable
    fun FormSignIn(modifier: Modifier) {
        Column {
            CustomTextField(
                title = "Nama Lengkap",
                modifier = modifier,
                value = viewModel.fullName,
                onValueChange = { viewModel.fullName = it },
                isError = viewModel.fullNameError,
                errorMessage = viewModel.fullNameErrorMessage
            )
            CustomTextField(
                title = "Email Address",
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
                errorMessage = viewModel.passwordErrorMessage
            )
            CustomTextField(
                title = "Ulangi Password",
                modifier = modifier,
                value = viewModel.passwordRepeat,
                onValueChange = { viewModel.passwordRepeat = it },
                isError = viewModel.passwordRepeatError,
                errorMessage = viewModel.passwordRepeatErrorMessage
            )

        }
    }

    private fun observeSignUpUser() {
        viewModel.userSignUp.observe(viewLifecycleOwner) { signUpResponse ->
            when (signUpResponse) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    val intent = Intent(context, MainActivity::class.java)
                    startActivity(intent)

                    Timber.tag("Sign Up Response ::").d(signUpResponse.data)
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Timber.tag("Sign Up Response ::").e(signUpResponse.message)
                }
            }
        }
    }
}
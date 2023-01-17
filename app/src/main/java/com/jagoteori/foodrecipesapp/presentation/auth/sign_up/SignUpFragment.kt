package com.jagoteori.foodrecipesapp.presentation.auth.sign_up

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.jagoteori.foodrecipesapp.MainActivity
import com.jagoteori.foodrecipesapp.app.extention.isEmailFormat
import com.jagoteori.foodrecipesapp.app.extention.isNotNullOrEmpty
import com.jagoteori.foodrecipesapp.app.extention.isPasswordEquals
import com.jagoteori.foodrecipesapp.data.Resource
import com.jagoteori.foodrecipesapp.databinding.FragmentSignUpBinding
import com.jagoteori.foodrecipesapp.domain.entity.UserEntity
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

        submitRegistrationData()
        observeSignUpUser()
    }


    private fun submitRegistrationData() {
        with(binding) {
            btnSignUp.setOnClickListener {
                if (checkFormValid()) {
                    val user = UserEntity(
                        userId = null,
                        name = etName.text.toString(),
                        email = etEmail.text.toString(),
                        profilePicture = null,
                    )

                    viewModel.createUserAccount(user, etPassword.text.toString())
                } else {
                    Timber.d("Terjadi kesalahan")
                }
            }
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


    private fun checkFormValid(): Boolean {
        with(binding) {
            if (etEmail.isNotNullOrEmpty("Silahkan isi email anda") and
                etName.isNotNullOrEmpty("Silahkan isi nama lengkap anda") and
                etPassword.isNotNullOrEmpty("Silahkan isi password anda") and
                etPasswordRepeat.isNotNullOrEmpty("Silahkan isi password anda") and
                (etPassword.isPasswordEquals(etPasswordRepeat, "Silahkan masukkan password dengan benar")) and
                etEmail.isEmailFormat("Masukkan format email dengan benar")
            ) {
                return true
            }
        }
        return false
    }

}
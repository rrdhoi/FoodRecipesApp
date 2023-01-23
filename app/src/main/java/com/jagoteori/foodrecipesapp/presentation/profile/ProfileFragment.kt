package com.jagoteori.foodrecipesapp.presentation.profile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.auth.FirebaseAuth
import com.jagoteori.foodrecipesapp.AuthActivity
import com.jagoteori.foodrecipesapp.R
import com.jagoteori.foodrecipesapp.data.Resource
import com.jagoteori.foodrecipesapp.databinding.ProfileFragmentBinding
import com.jagoteori.foodrecipesapp.domain.entity.UserEntity
import com.jagoteori.foodrecipesapp.presentation.profile.my_recipes.MyRecipesFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class ProfileFragment : Fragment() {
    private val viewModel: ProfileViewModel by viewModel()
    private lateinit var binding: ProfileFragmentBinding
    private lateinit var userEntity: UserEntity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Timber.plant(Timber.DebugTree())
        binding = ProfileFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeMyUser()
        setUpViewBinding()
    }

    private fun setUpViewBinding() {
        binding.tvSignOut.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(context, AuthActivity::class.java))
        }
        binding.tvMyRecipes.setOnClickListener {
            goToReportsFragment()
        }
        binding.ivProfile.setOnClickListener {
            ImagePicker.with(this)
                .galleryOnly()
                .compress(1024)
                .createIntent {
                    startForProfileImageResult.launch(it)
                }
        }
    }

    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            if (resultCode == Activity.RESULT_OK) {
                val fileUri = data?.data!!

                Glide.with(this)
                    .asBitmap()
                    .load(fileUri)
                    .into(binding.ivProfile)

                viewModel.updateUser(userEntity.copy(profilePicture = fileUri.toString()))
            } else if (resultCode == ImagePicker.RESULT_ERROR) {
                Toast.makeText(requireContext(), ImagePicker.getError(data), Toast.LENGTH_SHORT)
                    .show()
            }
        }

    private fun observeMyUser() {
        viewModel.myUser.observe(viewLifecycleOwner) { user ->
            when (user) {
                is Resource.Loading -> {
                    binding.pbFragmentProfile.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    userEntity = user.data!!
                    binding.pbFragmentProfile.visibility = View.GONE
                    setUpProfileData(userEntity)
                }
                is Resource.Error -> {
                    binding.pbFragmentProfile.visibility = View.GONE
                    Timber.tag("Profile Fragment ::").d("dataRecipe: Error %s", user.message)
                }
            }
        }
    }

    private fun goToReportsFragment() {
        val myRecipesFragment = MyRecipesFragment()
        val fragmentManager = parentFragmentManager
        fragmentManager.beginTransaction().apply {
            replace(
                R.id.frame_container,
                myRecipesFragment,
                myRecipesFragment::class.java.simpleName
            )
            addToBackStack(null)
            setReorderingAllowed(true)
            commit()
        }
    }

    private fun setUpProfileData(userEntity: UserEntity) {
        binding.tvName.text = userEntity.name
        binding.tvEmail.text = userEntity.email

        if (userEntity.profilePicture != null) {
            Glide.with(this)
                .load(userEntity.profilePicture)
                .into(binding.ivProfile)
        }
    }

}
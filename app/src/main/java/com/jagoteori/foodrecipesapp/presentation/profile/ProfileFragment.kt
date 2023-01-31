package com.jagoteori.foodrecipesapp.presentation.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.fragment.app.Fragment
import com.jagoteori.foodrecipesapp.R
import com.jagoteori.foodrecipesapp.databinding.ProfileFragmentBinding
import com.jagoteori.foodrecipesapp.presentation.profile.my_recipes.MyRecipesFragment
import com.jagoteori.foodrecipesapp.presentation.ui.pages.profile.ProfileScreen
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class ProfileFragment : Fragment() {
    private val viewModel: ProfileViewModel by viewModel()
    private lateinit var binding: ProfileFragmentBinding

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
        binding.composeView.setContent {
            MaterialTheme {
                ProfileScreen(modifier = Modifier, profileViewModel = viewModel, onClickReports = {
                    goToReportsFragment()
                }, onSignOut = {
                    viewModel.signOut()
//                    startActivity(Intent(context, AuthActivity::class.java))
                })
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
}
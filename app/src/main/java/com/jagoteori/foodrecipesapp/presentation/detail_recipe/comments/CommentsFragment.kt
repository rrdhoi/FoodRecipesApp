package com.jagoteori.foodrecipesapp.presentation.detail_recipe.comments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.jagoteori.foodrecipesapp.R
import com.jagoteori.foodrecipesapp.databinding.FragmentCommentsBinding
import com.jagoteori.foodrecipesapp.domain.entity.CommentEntity
import com.jagoteori.foodrecipesapp.domain.entity.RecipeEntity
import de.hdodenhof.circleimageview.CircleImageView
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber


class CommentsFragment(val recipeEntity: RecipeEntity) : Fragment() {
    private val commentsViewModel: CommentsViewModel by viewModel()
    private lateinit var binding: FragmentCommentsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Timber.plant(Timber.DebugTree())
        binding = FragmentCommentsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        whenClickingCommentSendButton()
        setUpLayout()
    }

    private fun whenClickingCommentSendButton() {
        binding.ilAddComment.setEndIconOnClickListener {
            val comment = CommentEntity(
                id = "",
                name = "dahlang",
                message = binding.etAddComment.text.toString(),
                profilePicture = ""
            )
            commentsViewModel.addComment(recipeId = recipeEntity.id!!,comment).invokeOnCompletion {
                binding.etAddComment.text?.clear()
            }
        }
    }

    private fun setUpLayout() {
        if (recipeEntity.listComments.isNullOrEmpty()) {
            binding.listComments.addView(emptyMessageView())
            return
        }

        for (comment in recipeEntity.listComments) {
            val itemComment = layoutInflater.inflate(R.layout.item_comment, binding.root, false)

            val tvMessage = itemComment.findViewById<TextView>(R.id.tv_message)
            val tvName = itemComment.findViewById<TextView>(R.id.tv_name)
            val ivProfile = itemComment.findViewById<CircleImageView>(R.id.iv_profile)

            tvMessage.text = comment.message
            tvName.text = comment.name

            if ((comment.profilePicture as String).isNotBlank()) Glide.with(requireContext())
                .load(comment.profilePicture)
                .into(ivProfile)

            binding.listComments.addView(itemComment)
        }
    }

    private fun emptyMessageView(): View {
        val messageView = TextView(context)
        val message = "Belum ada komentar!"
        messageView.text = message
        messageView.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        return messageView
    }
}
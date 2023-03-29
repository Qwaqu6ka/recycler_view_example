package com.example.recycler_view_example.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.recycler_view_example.R
import com.example.recycler_view_example.databinding.FragmentUserDetailsBinding
import com.example.recycler_view_example.navigator

class UserDetailsFragment : Fragment() {

    private val viewModel: UserDetailsViewModel by viewModels { factory() }
    private lateinit var binding: FragmentUserDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadUserById(requireArguments().getLong(ARG_USER_ID))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserDetailsBinding.inflate(inflater, container, false)
        viewModel.userDetails.observe(viewLifecycleOwner) {
            if (it.user.photo.isNotBlank()) {
                Glide.with(binding.userPhotoImageView)
                    .load(it.user.photo)
                    .circleCrop()
                    .into(binding.userPhotoImageView)
            } else {
                Glide.with(this)
                    .load(R.drawable.ic_user)
                    .into(binding.userPhotoImageView)
            }
            binding.userNameTextView.text = it.user.name
            binding.userDetailsTextView.text = it.details
        }

        binding.deleteButton.setOnClickListener {
            viewModel.deleteUser()
            navigator().toast(R.string.user_has_been_deleted)
            navigator().goBack()
        }

        return binding.root
    }

    companion object {
        private const val ARG_USER_ID = "ARG_USER_ID"

        fun getInstance(userId: Long) = UserDetailsFragment().apply {
            arguments = bundleOf(
                ARG_USER_ID to userId
            )
        }
    }
}
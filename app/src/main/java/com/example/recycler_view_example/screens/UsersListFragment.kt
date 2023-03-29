package com.example.recycler_view_example.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recycler_view_example.UserActionListener
import com.example.recycler_view_example.UsersAdapter
import com.example.recycler_view_example.databinding.FragmentUsersListBinding
import com.example.recycler_view_example.models.User
import com.example.recycler_view_example.navigator

class UsersListFragment : Fragment() {

    private val viewModel: UsersListViewModel by viewModels { factory() }
    private lateinit var binding: FragmentUsersListBinding
    private lateinit var adapter: UsersAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUsersListBinding.inflate(inflater, container, false)
        adapter = UsersAdapter(object : UserActionListener {
            override fun onUserDetails(user: User) {
                navigator().onUserDetails(user.id)
            }

            override fun onUserRemove(user: User) {
                viewModel.deleteUser(user)
            }

            override fun onUserMove(user: User, moveBy: Int) {
                viewModel.moveUser(user, moveBy)
            }
        })

        viewModel.users.observe(viewLifecycleOwner) {
            adapter.users = it
        }

        val layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = layoutManager

        return binding.root
    }
}
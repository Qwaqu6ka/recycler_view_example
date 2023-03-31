package com.example.recycler_view_example.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recycler_view_example.models.User
import com.example.recycler_view_example.models.UserListener
import com.example.recycler_view_example.models.UserService

class UsersListViewModel(private val userService: UserService) : ViewModel() {

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> = _users
    private val userListener: UserListener = {
        _users.value = it
    }

    init {
        loadUsers()
    }

    override fun onCleared() {
        super.onCleared()
        userService.removeListener(userListener)
    }

    private fun loadUsers() {
        userService.addListener(userListener)
    }

    fun moveUser(user: User, moveBy: Int) {
        userService.moveUser(user, moveBy)
    }

    fun deleteUser(user: User) {
        userService.deleteUser(user)
    }

    fun fireUser(user: User) {
        userService.fireUser(user)
    }
}
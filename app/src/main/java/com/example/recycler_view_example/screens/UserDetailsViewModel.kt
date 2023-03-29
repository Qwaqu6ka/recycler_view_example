package com.example.recycler_view_example.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recycler_view_example.UserNotFoundException
import com.example.recycler_view_example.models.UserDetails
import com.example.recycler_view_example.models.UserService

class UserDetailsViewModel(private val userService: UserService) : ViewModel() {

    private val _userDetails = MutableLiveData<UserDetails>()
    val userDetails: LiveData<UserDetails> = _userDetails

    fun loadUserById(userId: Long) {
        if (_userDetails.value != null) return
        try {
            _userDetails.value = userService.getUserById(userId)
        }
        catch (e: UserNotFoundException) {
            e.printStackTrace()
        }
    }

    fun deleteUser() {
        val userDetails = this._userDetails.value ?: return
        userService.deleteUser(userDetails.user)
    }
}
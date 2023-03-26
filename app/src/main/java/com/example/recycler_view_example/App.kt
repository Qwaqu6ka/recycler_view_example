package com.example.recycler_view_example

import android.app.Application
import com.example.recycler_view_example.models.UserService

class App: Application() {
    val userService = UserService()
}
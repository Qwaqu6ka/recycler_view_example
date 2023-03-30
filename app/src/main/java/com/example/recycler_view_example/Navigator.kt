package com.example.recycler_view_example

import androidx.annotation.StringRes

interface Navigator {

    fun onUserDetails(userId: Long)

    fun goBack()

    fun toast(@StringRes resId: Int)
}
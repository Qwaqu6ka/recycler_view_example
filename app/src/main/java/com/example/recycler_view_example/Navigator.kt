package com.example.recycler_view_example

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

interface Navigator {

    fun onUserDetails(userId: Long)

    fun goBack()

    fun toast(@StringRes resId: Int)
}

fun Fragment.navigator() = requireActivity() as Navigator
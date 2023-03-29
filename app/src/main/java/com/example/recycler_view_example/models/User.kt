package com.example.recycler_view_example.models

data class User(
    val id: Long,
    val photo: String,
    val name: String,
    val company: String
) {
    override fun toString(): String {
        return name
    }
}

data class UserDetails(
    val user: User,
    val details: String
)

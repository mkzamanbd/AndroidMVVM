package com.example.androidmvvm.data.response

import com.example.androidmvvm.data.database.entities.User

data class AuthResponse(
    val responseCode: Int?,
    val user: User
)
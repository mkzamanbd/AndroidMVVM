package com.example.androidmvvm.ui.auth

import com.example.androidmvvm.data.database.entities.User

interface AuthListener {

    fun onStarted()
    fun onSuccess(user: User)
    fun onFailure(message: String)

}
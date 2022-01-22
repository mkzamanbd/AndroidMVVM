package com.example.androidmvvm.ui.auth

import android.view.View
import androidx.lifecycle.ViewModel
import com.example.androidmvvm.R
import com.example.androidmvvm.data.repository.UserRepository
import com.example.androidmvvm.utils.Coroutines

class AuthViewModel : ViewModel() {

    var email: String? = "zaman7u@gmail.com"
    var password: String? = "password"

    var authListener: AuthListener? = null


    fun onLoginButtonClick(view: View) {
        authListener?.onStarted()
        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            authListener?.onFailure("Invalid email or password")
            return
        }
        Coroutines.main {
            val response = UserRepository().userLogin(email!!, password!!)
            if(response.isSuccessful){
                authListener?.onSuccess(response.body()?.user!!)
            }
        }
    }
}
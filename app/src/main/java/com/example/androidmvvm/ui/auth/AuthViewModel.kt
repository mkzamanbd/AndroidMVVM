package com.example.androidmvvm.ui.auth

import android.view.View
import androidx.lifecycle.ViewModel
import com.example.androidmvvm.R
import com.example.androidmvvm.data.repository.UserRepository
import com.example.androidmvvm.utils.ApiException
import com.example.androidmvvm.utils.Coroutines
import com.example.androidmvvm.utils.NoInternetException

class AuthViewModel(
    private val repository: UserRepository
) : ViewModel() {

    var email: String? = "zaman7u@gmail.com"
    var password: String? = "password"

    var authListener: AuthListener? = null

    fun getLoggedInUser() = repository.getUser()


    fun onLoginButtonClick(view: View) {
        authListener?.onStarted()
        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            authListener?.onFailure("Invalid email or password")
            return
        }
        Coroutines.main {
            try {
                val authResponse = repository.userLogin(email!!, password!!)
                authResponse.user.let {
                    authListener?.onSuccess(it)
                    repository.saveUser(it)
                    return@main
                }
            } catch (e: ApiException) {
                authListener?.onFailure(e.message!!)
            }catch (e: NoInternetException){
                authListener?.onFailure(e.message!!)
            }

        }
    }
}
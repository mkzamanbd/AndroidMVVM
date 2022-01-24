package com.example.androidmvvm.ui.auth

import android.content.Intent
import android.view.View
import androidx.lifecycle.ViewModel
import com.example.androidmvvm.data.repository.UserRepository
import com.example.androidmvvm.utils.ApiException
import com.example.androidmvvm.utils.Coroutines
import com.example.androidmvvm.utils.NoInternetException

class AuthViewModel(
    private val repository: UserRepository,
) : ViewModel() {
    var name: String? = "Kamruzzaman"
    var email: String? = "zaman7u@gmail.com"
    var password: String? = "password"
    var passwordConfirmation: String? = "password"

    var authListener: AuthListener? = null

    fun getLoggedInUser() = repository.getUser()

    fun onLogin(view: View) {
        Intent(view.context, LoginActivity::class.java).also {
            view.context.startActivity(it)
        }
    }

    fun onRegister(view: View) {
        Intent(view.context, RegisterActivity::class.java).also {
            view.context.startActivity(it)
        }
    }

    fun onLoginButtonClick(view: View) {
        authListener?.onStarted()
        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            authListener?.onFailure("Invalid email or password")
            return
        }
        Coroutines.main {
            try {
                val authResponse = repository.login(email!!, password!!)
                authResponse.user.let {
                    authListener?.onSuccess(it)
                    repository.saveUser(it)
                    return@main
                }
            } catch (e: ApiException) {
                authListener?.onFailure(e.message!!)
            } catch (e: NoInternetException) {
                authListener?.onFailure(e.message!!)
            }

        }
    }

    fun onRegisterButtonClick(view: View) {
        authListener?.onStarted()
        if (name.isNullOrEmpty()) {
            authListener?.onFailure("Name is required")
            return
        }
        if (email.isNullOrEmpty()) {
            authListener?.onFailure("Email is required")
            return
        }
        if (password.isNullOrEmpty()) {
            authListener?.onFailure("Password is required")
            return
        }

        if (password != passwordConfirmation) {
            authListener?.onFailure("Password and confirm password not match")
            return
        }
        Coroutines.main {
            try {
                val authResponse =
                    repository.userRegister(name!!, email!!, password!!, passwordConfirmation!!)
                authResponse.user.let {
                    authListener?.onSuccess(it)
                    return@main
                }
            } catch (e: ApiException) {
                authListener?.onFailure(e.message!!)
            } catch (e: NoInternetException) {
                authListener?.onFailure(e.message!!)
            }

        }
    }
}
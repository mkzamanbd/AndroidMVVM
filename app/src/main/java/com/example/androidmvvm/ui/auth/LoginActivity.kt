package com.example.androidmvvm.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.androidmvvm.R
import com.example.androidmvvm.data.database.entities.User
import com.example.androidmvvm.databinding.ActivityLoginBinding
import com.example.androidmvvm.utils.hide
import com.example.androidmvvm.utils.show
import com.example.androidmvvm.utils.toast


class LoginActivity : AppCompatActivity(), AuthListener {

    lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityLoginBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_login)
        val viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)

        binding.login = viewModel

        viewModel.authListener = this

        progressBar = findViewById(R.id.progress_bar)
    }

    override fun onStarted() {
        progressBar.show()
    }

    override fun onSuccess(user: User) {
        toast("${user.name} is Logged in")
    }

    override fun onFailure(message: String) {
        toast(message)
        progressBar.hide()
    }
}
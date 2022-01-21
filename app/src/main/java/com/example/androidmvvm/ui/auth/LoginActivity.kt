package com.example.androidmvvm.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.androidmvvm.R
import com.example.androidmvvm.databinding.ActivityLoginBinding
import com.example.androidmvvm.utils.toast


class LoginActivity : AppCompatActivity(), AuthListener {

    lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        val viewModel = ViewModelProviders.of(this).get(AuthViewModel::class.java)

        binding.login = viewModel

        viewModel.authListener = this

        progressBar = findViewById(R.id.progress_bar)
    }

    override fun onStarted() {
        progressBar.visibility = View.VISIBLE
    }

    override fun onSuccess(loginResponse: LiveData<String>) {
        loginResponse.observe(this, Observer {
            toast(it)
            progressBar.visibility = View.GONE
        })
    }

    override fun onFailure(message: String) {
        toast(message)
        progressBar.visibility = View.GONE
    }
}
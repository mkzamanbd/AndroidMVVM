package com.example.androidmvvm.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.androidmvvm.R
import com.example.androidmvvm.data.database.AppDatabase
import com.example.androidmvvm.data.database.entities.User
import com.example.androidmvvm.data.network.RetrofitClient
import com.example.androidmvvm.data.network.NetworkConnectionInterceptor
import com.example.androidmvvm.data.repository.UserRepository
import com.example.androidmvvm.databinding.ActivityLoginBinding
import com.example.androidmvvm.ui.MainActivity
import com.example.androidmvvm.utils.hide
import com.example.androidmvvm.utils.show
import com.example.androidmvvm.utils.toast


class LoginActivity : AppCompatActivity(), AuthListener {

    lateinit var progressBar: ProgressBar
    lateinit var tvErrorMessage: AppCompatTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val networkConnectionInterceptor = NetworkConnectionInterceptor(this)

        val api = RetrofitClient(networkConnectionInterceptor)
        val database = AppDatabase(this)
        val repository = UserRepository(api, database)
        val factory = AuthViewModelFactory(repository)

        val binding: ActivityLoginBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_login)
        val viewModel = ViewModelProvider(this, factory).get(AuthViewModel::class.java)

        binding.login = viewModel

        viewModel.authListener = this

        viewModel.getLoggedInUser().observe(this, Observer { user ->
            if(user != null){
                Intent(this, MainActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                }
            }
        })

        progressBar = findViewById(R.id.progress_bar)
        tvErrorMessage = findViewById(R.id.tvErrorMessage)
    }

    override fun onStarted() {
        progressBar.show()
        tvErrorMessage.visibility = View.GONE
    }

    override fun onSuccess(user: User) {
        toast("${user.name} is Logged in")
        progressBar.hide()
    }

    override fun onFailure(message: String) {
        toast(message)
        progressBar.hide()
        tvErrorMessage.visibility = View.VISIBLE
    }
}
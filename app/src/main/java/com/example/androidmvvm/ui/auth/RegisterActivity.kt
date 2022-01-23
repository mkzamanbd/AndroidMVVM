package com.example.androidmvvm.ui.auth

import android.os.Bundle
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.androidmvvm.R
import com.example.androidmvvm.data.database.entities.User
import com.example.androidmvvm.databinding.ActivityRegisterBinding
import com.example.androidmvvm.utils.hide
import com.example.androidmvvm.utils.show
import com.example.androidmvvm.utils.toast
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance


class RegisterActivity : AppCompatActivity(), AuthListener, KodeinAware {
    lateinit var progressBar: ProgressBar
    override val kodein by kodein()
    private val factory: AuthViewModelFactory by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityRegisterBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_register)
        val viewModel = ViewModelProvider(this, factory).get(AuthViewModel::class.java)
        binding.authViewModel = viewModel
        viewModel.authListener = this
        progressBar = findViewById(R.id.progress_bar)
    }

    override fun onStarted() {
        progressBar.show()
    }

    override fun onSuccess(user: User) {
        toast("${user.name} is successfully register")
        progressBar.hide()
    }

    override fun onFailure(message: String) {
        toast(message)
        progressBar.hide()
    }
}
package com.example.androidmvvm.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.androidmvvm.R
import com.example.androidmvvm.data.database.entities.User
import com.example.androidmvvm.databinding.ActivityLoginBinding
import com.example.androidmvvm.ui.MainActivity
import com.example.androidmvvm.utils.hide
import com.example.androidmvvm.utils.show
import com.example.androidmvvm.utils.toast
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance


class LoginActivity : AppCompatActivity(), AuthListener, KodeinAware {
    lateinit var progressBar: ProgressBar
    lateinit var tvErrorMessage: AppCompatTextView

    override val kodein by kodein()
    private val factory: AuthViewModelFactory by instance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityLoginBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_login)
        val viewModel = ViewModelProvider(this, factory).get(AuthViewModel::class.java)

        binding.authViewModel = viewModel

        viewModel.authListener = this

        viewModel.getLoggedInUser().observe(this, Observer { user ->
            if (user != null) {
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
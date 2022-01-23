package com.example.androidmvvm

import android.app.Application
import com.example.androidmvvm.data.database.AppDatabase
import com.example.androidmvvm.data.network.NetworkConnectionInterceptor
import com.example.androidmvvm.data.network.RetrofitClient
import com.example.androidmvvm.data.repository.UserRepository
import com.example.androidmvvm.ui.auth.AuthViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class BaseApplication : Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidXModule(this@BaseApplication))

        bind() from singleton { NetworkConnectionInterceptor(instance()) }
        bind() from singleton { RetrofitClient(instance()) }
        bind() from singleton { AppDatabase(instance()) }
        bind() from singleton { UserRepository(instance(), instance()) }
        bind() from provider { AuthViewModelFactory(instance()) }
    }
}
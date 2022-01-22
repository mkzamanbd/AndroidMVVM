package com.example.androidmvvm.data.repository


import com.example.androidmvvm.data.network.MyApi
import com.example.androidmvvm.data.response.AuthResponse
import retrofit2.Response

class UserRepository {

    suspend fun userLogin(email: String, password: String): Response<AuthResponse> {
        return MyApi().userLogin(email, password)
    }
}
package com.example.androidmvvm.data.repository


import com.example.androidmvvm.data.database.AppDatabase
import com.example.androidmvvm.data.database.entities.User
import com.example.androidmvvm.data.network.MyApi
import com.example.androidmvvm.data.network.SafeApiRequest
import com.example.androidmvvm.data.response.AuthResponse

class UserRepository(
    private val api: MyApi,
    private val database: AppDatabase,
) : SafeApiRequest() {

    suspend fun userLogin(email: String, password: String): AuthResponse {
        return apiRequest { api.userLogin(email, password) }
    }

    suspend fun saveUser(user: User) = database.getUserDao().upsert(user)

    fun getUser() = database.getUserDao().getUser()


}
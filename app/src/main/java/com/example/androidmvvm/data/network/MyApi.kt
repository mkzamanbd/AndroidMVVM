package com.example.androidmvvm.data.network

import com.example.androidmvvm.BuildConfig
import com.example.androidmvvm.data.response.AuthResponse
import okhttp3.Authenticator
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface MyApi {

    @FormUrlEncoded
    @POST("auth/login")
    suspend fun userLogin(
        @Field("email") email: String,
        @Field("password") password: String,
    ): Response<AuthResponse>

    companion object {
        operator fun invoke(): MyApi {
            return Retrofit.Builder()
                .baseUrl("https://api.kzaman.me/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(getRetrofitClient())
                .build()
                .create(MyApi::class.java)
        }

        private fun getRetrofitClient(authenticator: Authenticator? = null): OkHttpClient {
            return OkHttpClient.Builder()
                .addInterceptor { chain ->
                    chain.proceed(chain.request().newBuilder().also {
                        it.addHeader("Accept", "application/json")
                    }.build())
                }.also { client ->
                    authenticator?.let { client.authenticator(it) }
                    if (BuildConfig.DEBUG) {
                        val logging = HttpLoggingInterceptor()
                        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                        client.addInterceptor(logging)
                    }
                }.build()
        }
    }
}
package com.example.androidmvvm.data.network

import com.example.androidmvvm.BuildConfig
import okhttp3.Authenticator
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface MyApi {

    @FormUrlEncoded
    @POST("auth/login")
    fun userLogin(
        @Field("username") email: String,
        @Field("password") password: String,
    ): Call<ResponseBody>

    companion object {
        operator fun invoke(): MyApi {
            return Retrofit.Builder()
                .baseUrl("http://203.188.245.58:8889/api/")
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
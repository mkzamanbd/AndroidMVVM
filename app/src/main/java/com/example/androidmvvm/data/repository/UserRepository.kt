package com.example.androidmvvm.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.androidmvvm.data.network.MyApi
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import org.json.JSONObject

class UserRepository {

    fun userLogin(email: String, password: String): LiveData<String> {
        val loginResponse = MutableLiveData<String>()

        MyApi().userLogin(email, password)
            .enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>,
                ) {
                    if (response.isSuccessful) {
                        // val jsonSuccessObject = JSONObject(response.body()!!.string())
                        loginResponse.value = response.body()?.string()
                    } else {
                        // val jsonErrorObject = JSONObject(response.errorBody()!!.string())
                        loginResponse.value = response.errorBody()?.string()
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    loginResponse.value = t.message
                }

            })

        return loginResponse
    }
}
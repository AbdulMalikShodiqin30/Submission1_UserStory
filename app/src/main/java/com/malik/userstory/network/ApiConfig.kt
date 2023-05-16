package com.malik.userstory.network

import android.content.Context
import com.malik.userstory.data.preference.UserPreferences
//import com.malik.userstory.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {
    companion object {
        fun getApiService(context: Context): ApiService {
            val userPreferences = UserPreferences(context)
            val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            val interceptor = Interceptor { chain ->
                val token = userPreferences.getToken()
                val requestToken = "Bearer $token"
                val req = chain.request()
                val requestHeader = req.newBuilder()
                    .addHeader("Authorization", requestToken)
                    .build()
                chain.proceed(requestHeader)
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(interceptor)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl("https://story-api.dicoding.dev/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}
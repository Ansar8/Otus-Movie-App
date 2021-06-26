package com.example.otusmovieapp

import android.app.Application
import com.example.otusmovieapp.BuildConfig.BASE_URL
import com.example.otusmovieapp.data.network.MoviesApi
import com.example.otusmovieapp.data.network.MoviesApiInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class App: Application() {

    lateinit var moviesApi: MoviesApi

    override fun onCreate() {
        super.onCreate()
        instance = this

        initRetrofit()
    }

    private fun initRetrofit() {
        val client = OkHttpClient().newBuilder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor(MoviesApiInterceptor())
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        moviesApi = retrofit.create(MoviesApi::class.java)
    }

    companion object {

        lateinit var instance: App
            private set

    }
}
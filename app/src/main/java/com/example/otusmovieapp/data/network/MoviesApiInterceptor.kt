package com.example.otusmovieapp.data.network

import com.example.otusmovieapp.BuildConfig.API_KEY
import okhttp3.Interceptor
import okhttp3.Response

class MoviesApiInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalHttpUrl = originalRequest.url

        val url = originalHttpUrl.newBuilder()
            .addQueryParameter("api_key", API_KEY)
            .build()

        val request = originalRequest.newBuilder()
            .url(url)
            .build()

        return chain.proceed(request)
    }
}
package com.example.otusmovieapp.data.network

import com.example.otusmovieapp.data.network.response.MoviesResponse
import retrofit2.Call
import retrofit2.http.GET

interface MoviesApi {

    @GET("movie/popular?page=1")
    fun getMovies(): Call<MoviesResponse>

}
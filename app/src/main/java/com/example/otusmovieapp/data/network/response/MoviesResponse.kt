package com.example.otusmovieapp.data.network.response

import com.example.otusmovieapp.data.network.response.MovieResponse
import com.google.gson.annotations.SerializedName

data class MoviesResponse(
    @SerializedName("results") val movies: List<MovieResponse>
)
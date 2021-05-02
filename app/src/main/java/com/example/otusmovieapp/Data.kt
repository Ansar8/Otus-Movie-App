package com.example.otusmovieapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

class Data {

    companion object{
        val movieList: ArrayList<Movie> = arrayListOf(
            Movie("Godzilla", R.drawable.image1, false),
            Movie("Raya", R.drawable.image2, false),
            Movie("Justice League", R.drawable.image3, false)
        )
    }
}
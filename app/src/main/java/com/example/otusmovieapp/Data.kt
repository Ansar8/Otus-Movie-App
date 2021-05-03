package com.example.otusmovieapp

class Data {

    companion object {
        val movieList: ArrayList<Movie> = arrayListOf(
                Movie("Godzilla", R.drawable.image1, isReviewed = false, isFavorite = false),
                Movie("Raya", R.drawable.image2, isReviewed = false, isFavorite = false),
                Movie("Justice League", R.drawable.image3, isReviewed = false, isFavorite = false),
                Movie("Godzilla", R.drawable.image1, isReviewed = false, isFavorite = false),
                Movie("Raya", R.drawable.image2, isReviewed = false, isFavorite = false),
                Movie("Justice League", R.drawable.image3, isReviewed = false, isFavorite = false)
        )
    }
}
package com.example.otusmovieapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(val id: Int,
                 val title: String,
                 val imageUrl: String,
                 var isReviewed: Boolean = false,
                 var isFavorite: Boolean = false) : Parcelable
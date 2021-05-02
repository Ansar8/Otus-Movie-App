package com.example.otusmovieapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(val title: String, val imageResource: Int, var isReviewed: Boolean): Parcelable
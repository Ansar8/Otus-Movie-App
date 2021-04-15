package com.example.otusmovieapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Movie(val title: String, val imageResource: Int): Parcelable
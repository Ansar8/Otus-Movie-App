package com.example.otusmovieapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.example.otusmovieapp.Data.*

class DetailsActivity : AppCompatActivity() {

    companion object {
        const val DETAILS_EXTRA = "DETAILS_EXTRA"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        intent.getParcelableExtra<Movie>(DETAILS_EXTRA)?.let{
            findViewById<ImageView>(R.id.detailsImage).setImageResource(it.imageResource)
            findViewById<TextView>(R.id.detailsTitle).text = it.title
        }
    }
}
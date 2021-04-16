package com.example.otusmovieapp

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.otusmovieapp.Data.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val MOVIES = "MOVIES"
    }

    private lateinit var movieList: ArrayList<Movie>
    private lateinit var viewList: List<Pair<ImageView, TextView>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewList = listOf(
            Pair(findViewById(R.id.imageView1), findViewById(R.id.textView1)),
            Pair(findViewById(R.id.imageView2), findViewById(R.id.textView2)),
            Pair(findViewById(R.id.imageView3), findViewById(R.id.textView3)),
        )

        movieList =
            if (savedInstanceState != null)
                savedInstanceState.getParcelableArrayList(MOVIES)!!
            else
                Data.movieList

        onBindMovies()
    }

    override fun onStart() {
        super.onStart()
        onBindMovies()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelableArrayList(MOVIES, movieList)
        super.onSaveInstanceState(outState)
    }

    fun onDetailsClicked(view: View){
        val position = when(view.id){
            R.id.detailsBtn1 -> 0
            R.id.detailsBtn2 -> 1
            R.id.detailsBtn3 -> 2
            else -> -1
        }
        if (position != -1)
            launchDetailsActivity(movieList[position])
    }

    private fun launchDetailsActivity(movie: Movie){
        movie.isReviewed = true

        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(DetailsActivity.DETAILS_EXTRA, movie)
        startActivity(intent)
    }

    private fun onBindMovies(){
        for ((position, movie) in movieList.withIndex()) {
            val (imageView, textView) = viewList[position]
            imageView.setImageResource(movie.imageResource)
            textView.text = movie.title
            if (movie.isReviewed) textView.setTextColor(Color.RED)
        }
    }
}
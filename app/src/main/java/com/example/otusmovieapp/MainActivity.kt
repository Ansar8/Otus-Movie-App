package com.example.otusmovieapp

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    companion object {
        const val MOVIES = "MOVIES"
    }

    private lateinit var movieList: ArrayList<Movie>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        movieList =
            if (savedInstanceState != null)
                savedInstanceState.getParcelableArrayList(MOVIES)!!
            else
                Data.movieList

        updateMoviesReviewState()
    }

    override fun onStart() {
        super.onStart()
        updateMoviesReviewState()
    }

    fun onDetailsClicked(view: View){
        when(view.id){
            R.id.detailsBtn1 -> {
                launchDetailsActivity(movieList[0])
            }
            R.id.detailsBtn2 -> {
                launchDetailsActivity(movieList[1])
            }
            R.id.detailsBtn3 -> {
                launchDetailsActivity(movieList[2])
            }
        }
    }

    private fun launchDetailsActivity(movie: Movie){
        movie.isReviewed = true

        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(DetailsActivity.DETAILS_EXTRA, movie)
        startActivity(intent)
    }

    private fun updateMoviesReviewState(){
        if (movieList[0].isReviewed)
            findViewById<TextView>(R.id.textView1).setTextColor(Color.RED)
        if (movieList[1].isReviewed)
            findViewById<TextView>(R.id.textView2).setTextColor(Color.RED)
        if (movieList[2].isReviewed)
            findViewById<TextView>(R.id.textView3).setTextColor(Color.RED)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelableArrayList(MOVIES, movieList)
        super.onSaveInstanceState(outState)
    }
}
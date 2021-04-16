package com.example.otusmovieapp

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.otusmovieapp.Data.Movie

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

    fun onDetailsButtonClicked(view: View){
        val position = when(view.id){
            R.id.detailsBtn1 -> 0
            R.id.detailsBtn2 -> 1
            R.id.detailsBtn3 -> 2
            else -> -1
        }
        if (position != -1)
            showDetails(movieList[position])
    }

    fun onInviteButtonClicked(view: View){
        val position = when(view.id){
            R.id.inviteBtn1 -> 0
            R.id.inviteBtn2 -> 1
            R.id.inviteBtn3 -> 2
            else -> -1
        }
        if (position != -1)
            sendInvite(movieList[position])
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun sendInvite(movie: Movie){
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "plain/text"
        intent.putExtra(Intent.EXTRA_SUBJECT, "Checkout a great movie !")
        intent.putExtra(Intent.EXTRA_TEXT, "Don't forget to watch \"${movie.title}\"!")
        intent.resolveActivity(packageManager)?.let {
            startActivity(Intent.createChooser(intent, "Choose an Email client :"))
        }
    }

    private fun showDetails(movie: Movie){
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
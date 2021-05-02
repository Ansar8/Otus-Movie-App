package com.example.otusmovieapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    companion object {
        const val MOVIES = "MOVIES"
    }

    private lateinit var movieList: ArrayList<Movie>
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        movieList =
                if (savedInstanceState != null)
                    savedInstanceState.getParcelableArrayList(MOVIES)!!
                else
                    Data.movieList

        recyclerView = findViewById(R.id.movieRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = MovieItemAdapter(movieList, this::showDetails, this::sendInvite)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelableArrayList(MOVIES, movieList)
        super.onSaveInstanceState(outState)
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun sendInvite(movie: Movie) {
        val intent = Intent(Intent.ACTION_SEND)

        intent.type = "plain/text"
        intent.putExtra(Intent.EXTRA_SUBJECT, "Checkout a great movie !")
        intent.putExtra(Intent.EXTRA_TEXT, "Don't forget to watch \"${movie.title}\"!")
        intent.resolveActivity(packageManager)?.let {
            startActivity(Intent.createChooser(intent, "Choose a client :"))
        }
    }

    private fun showDetails(movie: Movie, position: Int) {
        movie.isReviewed = true
        recyclerView.adapter?.notifyItemChanged(position)

        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(DetailsActivity.DETAILS_EXTRA, movie)
        startActivity(intent)
    }
}
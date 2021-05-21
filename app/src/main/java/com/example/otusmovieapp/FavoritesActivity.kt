package com.example.otusmovieapp

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FavoritesActivity : AppCompatActivity() {

    companion object{
        const val HAS_UPDATES = "HAS_UPDATES"
    }

    private lateinit var movieList: ArrayList<Movie>
    private lateinit var recyclerView: RecyclerView
    private var isMovieListUpdated: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)

        supportActionBar?.title = "Favorites"

        movieList = Data.movieList.filter { it.isFavorite } as ArrayList
        savedInstanceState?.let {
            isMovieListUpdated = it.getBoolean(HAS_UPDATES, false)
        }

        initViews()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBoolean(HAS_UPDATES, isMovieListUpdated)
        super.onSaveInstanceState(outState)
    }

    override fun onBackPressed() {
        val intent = Intent()
        intent.putExtra(HAS_UPDATES, isMovieListUpdated)
        setResult(RESULT_OK, intent)
        finish()
    }

    private fun initViews() {
        val layoutManager = when (resources.configuration.orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> GridLayoutManager(this, 2)
            else -> LinearLayoutManager(this)
        }

        recyclerView = findViewById(R.id.favoriteMovieRecyclerView)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = FavoriteMovieItemAdapter(movieList, this::deleteItem)
    }

    private fun deleteItem(item: Movie, position: Int) {
        movieList.remove(item)
        recyclerView.adapter?.notifyItemRemoved(position)
        item.isFavorite = false

        isMovieListUpdated = true

        Toast.makeText(this, "Movie was deleted !", Toast.LENGTH_SHORT).show()
    }
}
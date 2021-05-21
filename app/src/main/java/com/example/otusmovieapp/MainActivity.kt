package com.example.otusmovieapp

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var movieList: ArrayList<Movie>
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.title = "Movies"

        movieList = Data.movieList

        initViews()
    }

    private fun initViews() {
        val layoutManager = when (resources.configuration.orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> GridLayoutManager(this, 2)
            else -> LinearLayoutManager(this)
        }

        recyclerView = findViewById(R.id.movieRecyclerView)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = MovieItemAdapter(movieList, this::showDetails, this::sendInvite)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.movies_menu, menu)
        return true
    }

    override fun onBackPressed() {
        ExitDialog().show(supportFragmentManager, "exitDialog")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.favoritesItem -> {
                showFavorites()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK) {
            val hasUpdates = data?.getBooleanExtra(FavoritesActivity.HAS_UPDATES, false) ?: false
            if (hasUpdates)
                updateFavoriteMovies()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun updateFavoriteMovies() {
        recyclerView.adapter?.notifyDataSetChanged()
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

    private fun showFavorites(){
        val intent = Intent(this, FavoritesActivity::class.java)
        startActivityForResult(intent, 0)
    }
}
package com.example.otusmovieapp

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FavoritesActivity : AppCompatActivity() {

    companion object {
        const val FAVORITE_MOVIES = "FAVORITE_MOVIES"
        const val REMOVED_MOVIES = "REMOVED_MOVIES"
    }

    private lateinit var movieList: ArrayList<Movie>
    private lateinit var removedList: ArrayList<Movie>
    private lateinit var recyclerView: RecyclerView
    private var favoritesMenu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)

        supportActionBar?.title = "Favorites"

        intent.getParcelableArrayListExtra<Movie>(FAVORITE_MOVIES).let {
            movieList = it as ArrayList<Movie>
        }

        removedList = if (savedInstanceState != null)
            savedInstanceState.getParcelableArrayList(REMOVED_MOVIES)!!
        else
            ArrayList()

        initViews()
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

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelableArrayList(REMOVED_MOVIES, removedList)
        super.onSaveInstanceState(outState)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.favorites_menu, menu)
        favoritesMenu = menu

        favoritesMenu?.findItem(R.id.saveItem)?.isEnabled = removedList.size > 0
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.saveItem -> {
                saveChanges()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun saveChanges() {
        val intent = Intent()
        intent.putExtra(REMOVED_MOVIES, removedList)
        setResult(RESULT_OK, intent)
        finish()
    }

    private fun deleteItem(item: Movie, position: Int) {
        movieList.remove(item)
        removedList.add(item)
        recyclerView.adapter?.notifyItemRemoved(position)

        favoritesMenu?.findItem(R.id.saveItem)?.isEnabled = true
        Toast.makeText(this, getString(R.string.save_changes_message), Toast.LENGTH_SHORT).show()
    }
}
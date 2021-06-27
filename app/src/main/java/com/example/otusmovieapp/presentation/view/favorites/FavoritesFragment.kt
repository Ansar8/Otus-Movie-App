package com.example.otusmovieapp.presentation.view.favorites

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.otusmovieapp.data.domain.Movie
import com.example.otusmovieapp.R
import com.example.otusmovieapp.data.db.Data

class FavoritesFragment : Fragment(R.layout.fragment_favorites) {

    private lateinit var movieList: ArrayList<Movie>
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieList = Data.movieList.filter { it.isFavorite } as ArrayList
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews(view)
    }

    private fun initViews(view: View) {
        view.findViewById<Toolbar>(R.id.favoritesToolbar).title = TOOLBAR_TITLE

        val layoutManager = when (resources.configuration.orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> GridLayoutManager(requireContext(), 2)
            else -> LinearLayoutManager(requireContext())
        }

        recyclerView = view.findViewById(R.id.favoritesRecyclerView)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = FavoriteMovieItemAdapter(movieList, this::deleteItem)
    }

    private fun deleteItem(item: Movie, position: Int){
        item.isFavorite = false
        movieList.remove(item)
        recyclerView.adapter?.notifyItemRemoved(position)

        Toast.makeText(requireContext(), "${item.title} was removed from favorites!", Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val TOOLBAR_TITLE = "Favorites"

        @JvmStatic
        fun newInstance() = FavoritesFragment()
    }
}
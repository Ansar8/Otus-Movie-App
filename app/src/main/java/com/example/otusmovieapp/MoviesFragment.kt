package com.example.otusmovieapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.otusmovieapp.MovieItemAdapter.*
import com.example.otusmovieapp.data.network.response.MoviesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class MoviesFragment : Fragment(R.layout.fragment_movies), OnMovieClickListener {

    private lateinit var recyclerView: RecyclerView
    private var listener: OnItemSelectedListener? = null
    private val adapter = MovieItemAdapter(Data.movieList, this)
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews(view)
        if (Data.movieList.isEmpty()) getMovies()
    }

    private fun initViews(view: View){
        view.findViewById<Toolbar>(R.id.moviesToolbar).title = TOOLBAR_TITLE

        val layoutManager = when (resources.configuration.orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> GridLayoutManager(requireContext(), 2)
            else -> LinearLayoutManager(requireContext())
        }

        recyclerView = view.findViewById(R.id.moviesRecyclerView)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        swipeRefreshLayout = view.findViewById(R.id.swipeContainer)
        swipeRefreshLayout.setColorSchemeResources(R.color.progress_bar)
        swipeRefreshLayout.setOnRefreshListener {
            getMovies()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is OnItemSelectedListener)
            listener = context
        else
            throw ClassCastException("$context must implement AllMoviesFragment.OnItemSelectedListener")
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onDetailsClick(movie: Movie) {
        listener?.onDetailsSelected(movie)
    }

    @SuppressLint("QueryPermissionsNeeded")
    override fun onInviteClick(movie: Movie) {
        val intent = Intent(Intent.ACTION_SEND)

        intent.type = "plain/text"
        intent.putExtra(Intent.EXTRA_SUBJECT, "Checkout a great movie !")
        intent.putExtra(Intent.EXTRA_TEXT, "Don't forget to watch \"${movie.title}\"!")
        intent.resolveActivity(requireActivity().packageManager)?.let {
            startActivity(Intent.createChooser(intent, "Choose a client :"))
        }
    }

    override fun onFavoriteClick(movie: Movie) {
        val message = if (movie.isFavorite)
            "${movie.title} was added to favorites !"
        else
            "${movie.title} was removed from favorites !"
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun getMovies() {
        swipeRefreshLayout.isRefreshing = true

        App.instance.moviesApi.getMovies().enqueue( object : Callback<MoviesResponse> {
            override fun onResponse(call: Call<MoviesResponse>, response: Response<MoviesResponse>) {
                if (response.isSuccessful) {
                    Data.movieList.clear()
                    response.body()?.movies?.forEach {
                        Data.movieList.add(
                            Movie(id = it.id, title = it.title, imageUrl = BuildConfig.IMAGES_BASE_URL + it.image)
                        )
                    }
                    adapter.notifyDataSetChanged()
                }
                else {
                    Log.d("getMovies", "Network connection issues: ${response.errorBody().toString()}")
                }
                swipeRefreshLayout.isRefreshing = false
            }

            override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                Log.d("getMovies", "Failed to load movies with the error: ${t.message}")
                swipeRefreshLayout.isRefreshing = false
            }
        })
    }

    interface OnItemSelectedListener {
        fun onDetailsSelected(movie: Movie)
    }

    companion object {
        private const val TOOLBAR_TITLE = "Movies"

        @JvmStatic
        fun newInstance() = MoviesFragment()
    }

}
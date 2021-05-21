package com.example.otusmovieapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MoviesFragment : Fragment(R.layout.fragment_movies) {

    private lateinit var recyclerView: RecyclerView
    private var listener: OnItemSelectedListener? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews(view)
    }

    private fun initViews(view: View){
        view.findViewById<Toolbar>(R.id.moviesToolbar).title = TOOLBAR_TITLE

        val layoutManager = when (resources.configuration.orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> GridLayoutManager(requireContext(), 2)
            else -> LinearLayoutManager(requireContext())
        }

        recyclerView = view.findViewById(R.id.moviesRecyclerView)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = MovieItemAdapter(
                Data.movieList,
                this::showDetails,
                this::sendInvite,
                this::showFavoritesUpdate
        )
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

    @SuppressLint("QueryPermissionsNeeded")
    private fun sendInvite(movie: Movie) {
        val intent = Intent(Intent.ACTION_SEND)

        intent.type = "plain/text"
        intent.putExtra(Intent.EXTRA_SUBJECT, "Checkout a great movie !")
        intent.putExtra(Intent.EXTRA_TEXT, "Don't forget to watch \"${movie.title}\"!")
        intent.resolveActivity(requireActivity().packageManager)?.let {
            startActivity(Intent.createChooser(intent, "Choose a client :"))
        }
    }

    private fun showDetails(movie: Movie) {
        listener?.onDetailsSelected(movie)
    }

    private fun showFavoritesUpdate(movie: Movie){
        val message = if (movie.isFavorite)
            "${movie.title} was added to favorites !"
        else
            "${movie.title} was removed from favorites !"
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
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
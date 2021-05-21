package com.example.otusmovieapp

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.CollapsingToolbarLayout

class DetailsFragment : Fragment(R.layout.fragment_details) {

    private lateinit var movie: Movie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            movie = it.getParcelable(PARAM_MOVIE)!!
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<ImageView>(R.id.backdrop).setImageResource(movie.imageResource)
        view.findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout).title = movie.title
    }

    companion object {
        private const val PARAM_MOVIE = "PARAM_MOVIE"

        @JvmStatic
        fun newInstance(movie: Movie) =
            DetailsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(PARAM_MOVIE, movie)
                }
            }
    }
}
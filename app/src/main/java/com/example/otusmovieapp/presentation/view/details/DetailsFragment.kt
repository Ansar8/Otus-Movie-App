package com.example.otusmovieapp.presentation.view.details

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.otusmovieapp.data.domain.Movie
import com.example.otusmovieapp.R
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

        view.findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout).title = movie.title
        view.findViewById<ImageView>(R.id.backdrop).apply {
            Glide.with(this.context)
                .load(movie.imageUrl)
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_error)
                .transition(DrawableTransitionOptions.withCrossFade(500))
                .into(this)
        }
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
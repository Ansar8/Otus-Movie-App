package com.example.otusmovieapp.presentation.view.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.otusmovieapp.R
import com.example.otusmovieapp.data.domain.Movie

class MovieItemAdapter(private val items: List<Movie>,
                       private val listener: OnMovieClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_movie, parent, false)

        return MovieItemViewHolder(view, listener)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        (holder as MovieItemViewHolder).onBind(item)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    interface OnMovieClickListener {

        fun onDetailsClick(movie: Movie)
        fun onInviteClick(movie: Movie)
        fun onFavoriteClick(movie: Movie)

    }
}
package com.example.otusmovieapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class FavoriteMovieItemAdapter(private val items: List<Movie>,
                               private val deleteClickListener: (Movie, Int) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_favorite_movie, parent, false)

        return FavoriteMovieItemViewHolder(view, deleteClickListener)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        (holder as FavoriteMovieItemViewHolder).onBind(item, position)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
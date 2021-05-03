package com.example.otusmovieapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class MovieItemAdapter(private val items: List<Movie>,
                       private val detailsClickListener: (Movie, Int) -> Unit,
                       private val inviteClickListener: (Movie) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_movie, parent, false)

        return MovieItemViewHolder(
                view,
                detailsClickListener,
                inviteClickListener
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        (holder as MovieItemViewHolder).onBind(item, position)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
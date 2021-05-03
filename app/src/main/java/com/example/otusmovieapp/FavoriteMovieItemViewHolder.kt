package com.example.otusmovieapp

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FavoriteMovieItemViewHolder(
    itemView: View,
    val deleteClicked: (Movie, Int) -> Unit
) : RecyclerView.ViewHolder(itemView) {

    private val image: ImageView = itemView.findViewById(R.id.imageView)
    private val title: TextView = itemView.findViewById(R.id.textView)
    private val deleteButton: ImageView = itemView.findViewById(R.id.deleteIcon)

    fun onBind(item: Movie, position: Int) {
        deleteButton.setOnClickListener { deleteClicked(item, position) }

        image.setImageResource(item.imageResource)
        title.text = item.title
    }
}
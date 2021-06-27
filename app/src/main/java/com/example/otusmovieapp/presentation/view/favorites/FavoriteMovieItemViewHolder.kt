package com.example.otusmovieapp.presentation.view.favorites

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.otusmovieapp.data.domain.Movie
import com.example.otusmovieapp.R

class FavoriteMovieItemViewHolder(
    itemView: View,
    val deleteClicked: (Movie, Int) -> Unit
) : RecyclerView.ViewHolder(itemView) {

    private val image: ImageView = itemView.findViewById(R.id.imageView)
    private val title: TextView = itemView.findViewById(R.id.textView)
    private val deleteButton: ImageView = itemView.findViewById(R.id.deleteIcon)

    fun onBind(item: Movie) {
        deleteButton.setOnClickListener { deleteClicked(item, layoutPosition) }

        title.text = item.title
        setImage(item.imageUrl)
    }

    private fun setImage(imageUrl: String){
        Glide.with(image.context)
            .load(imageUrl)
            .placeholder(R.drawable.ic_placeholder)
            .error(R.drawable.ic_error)
            .transition(DrawableTransitionOptions.withCrossFade(500))
            .into(image)
    }
}
package com.example.otusmovieapp

import android.graphics.Color
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.otusmovieapp.MovieItemAdapter.*

class MovieItemViewHolder(itemView: View,
                          private val listener: OnMovieClickListener
) : RecyclerView.ViewHolder(itemView) {

    private val image: ImageView = itemView.findViewById(R.id.imageView)
    private val title: TextView = itemView.findViewById(R.id.textView)
    private val starButton: ImageView = itemView.findViewById(R.id.starIcon)
    private val detailsButton: Button = itemView.findViewById(R.id.detailsBtn)
    private val inviteButton: Button = itemView.findViewById(R.id.inviteBtn)

    fun onBind(item: Movie) {

        detailsButton.setOnClickListener { listener.onDetailsClick(item) }
        inviteButton.setOnClickListener { listener.onInviteClick(item) }
        starButton.setOnClickListener {
            item.isFavorite = !item.isFavorite
            listener.onFavoriteClick(item)
            setStarButtonColor(item)
        }

        image.setImageResource(item.imageResource)
        title.text = item.title
        title.apply {
            if (item.isReviewed)
                setTextColor(Color.RED)
            else
                setTextColor(Color.BLACK)
        }

        setStarButtonColor(item)
    }

    private fun setStarButtonColor(item: Movie){
        starButton.apply {
            if (item.isFavorite)
                setImageResource(R.drawable.ic_star_yellow)
            else
                setImageResource(R.drawable.ic_star_white)
        }
    }
}
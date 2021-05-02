package com.example.otusmovieapp

import android.graphics.Color
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MovieItemViewHolder(itemView: View,
                          val detailsClicked: (Movie) -> Unit,
                          val inviteClicked: (Movie) -> Unit): RecyclerView.ViewHolder(itemView) {

    private val image: ImageView = itemView.findViewById(R.id.imageView)
    private val title: TextView = itemView.findViewById(R.id.textView)
    private val detailsButton: Button =  itemView.findViewById(R.id.detailsBtn)
    val inviteButton: Button =  itemView.findViewById(R.id.inviteBtn)

    fun onBind(item: Movie){
        image.setImageResource(item.imageResource)
        title.text = item.title
        detailsButton.setOnClickListener { detailsClicked(item) }
        inviteButton.setOnClickListener { inviteClicked(item) }

        if (item.isReviewed)
            title.setTextColor(Color.RED)
        else
            title.setTextColor(Color.BLACK)
    }
}
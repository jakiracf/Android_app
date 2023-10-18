package com.example.jakirac_projekt

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

enum class ItemClickType {
    REMOVE,
    EXPAND
}

class GameRecyclerAdapter(private val items: ArrayList<Game>, private val listener: ContentListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return GameViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is GameViewHolder -> {
                holder.bind(position, items[position], listener)
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }


    class GameViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val imageButton = view.findViewById<ImageView>(R.id.gameImage)
        private val title = view.findViewById<TextView>(R.id.titleTextView)

        fun bind(index: Int, game: Game, listener: ContentListener) {
            Glide.with(view.context).load(game.imageUrl).into(imageButton)
            title.setText(game.title)

            imageButton.setOnClickListener {
                listener.onItemButtonClick(index, game, ItemClickType.EXPAND)
            }

        }
    }

    interface ContentListener {
        fun onItemButtonClick(index: Int, game: Game, clickType: ItemClickType)
    }
}
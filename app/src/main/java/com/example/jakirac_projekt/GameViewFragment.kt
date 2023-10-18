package com.example.jakirac_projekt

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class GameViewFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_game_view, container, false)
        var image = view.findViewById<ImageView>(R.id.expandedImageView)
        var title = view.findViewById<TextView>(R.id.expandedTitleTextview)
        var description = view.findViewById<TextView>(R.id.expandedDescriptionTextView)
        var year = view.findViewById<TextView>(R.id.expandedYearTextView)
        var developer = view.findViewById<TextView>(R.id.expandedDeveloperTextView)

        val args = this.arguments

        title.text = args?.get("title").toString()
        description.text = args?.get("description").toString()
        year.text = args?.get("year").toString()
        developer.text = args?.get("developer").toString()
        Glide.with(view.context).load(args?.get("imageUrl").toString()).into(image)

        val backButton = view.findViewById<Button>(R.id.backButton)
            .setOnClickListener {
                val transaction = fragmentManager?.beginTransaction()
                transaction?.replace(R.id.activity, MainFragment())?.commit()
            }

        return view
    }
}
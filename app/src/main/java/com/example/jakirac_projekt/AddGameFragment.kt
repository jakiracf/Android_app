package com.example.jakirac_projekt

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class AddGameFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_game, container, false)
        // Inflate the layout for this fragment

        val imgUrl = view.findViewById<EditText>(R.id.imgUrl)
        val title = view.findViewById<EditText>(R.id.editTextGameTitle)
        val year = view.findViewById<EditText>(R.id.editTextGameYear)
        val developer = view.findViewById<EditText>(R.id.editTextTextGameDeveloper)
        val description = view.findViewById<EditText>(R.id.editTextGameDescritpion)

        val database = Firebase.firestore


        val button = view.findViewById<Button>(R.id.homeButton)
            .setOnClickListener {
                val transaction = fragmentManager?.beginTransaction()
                transaction?.replace(R.id.activity, MainFragment())?.commit()
            }
        val addButton = view.findViewById<Button>(R.id.addButton)
        addButton.setOnClickListener {
            val newGame = Game(
                imageUrl = imgUrl.text.toString(),
                title = title.text.toString(),
                year = year.text.toString(),
                developer = developer.text.toString(),
                description = description.text.toString()

            )
            database.collection("games").add(newGame)
        }
        return view
    }
}
package com.example.jakirac_projekt

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class MainFragment : Fragment(R.layout.fragment_main), GameRecyclerAdapter.ContentListener {

    private val database = Firebase.firestore
    private lateinit var recyclerAdapter: GameRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_main, container, false)
        var searchInput = view.findViewById<EditText>(R.id.editTextSearch)

        val addGameButton = view.findViewById<Button>(R.id.AddGameButton)
        addGameButton.setOnClickListener {
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.activity, AddGameFragment())?.commit()
        }

        val recyclerView = view.findViewById<RecyclerView>(R.id.GameList)
        database.collection("games")
            .get()
            .addOnSuccessListener { result ->
                var gameList = ArrayList<Game>()
                for (data in result.documents) {
                    val game = data.toObject(Game::class.java)
                    if (game != null) {
                        game.id = data.id
                        gameList.add(game)
                    }
                }
                recyclerAdapter = GameRecyclerAdapter(gameList, this@MainFragment)
                recyclerView.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = recyclerAdapter
                }

                searchInput.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    }

                    override fun onTextChanged(query: CharSequence, p1: Int, p2: Int, p3: Int) {
                        query.toString().lowercase()
                        var filteredList = ArrayList<Game>()
                        for (i in 0 until gameList.size) {
                            if (gameList[i].title.toString().lowercase().contains(query)) {
                                filteredList.add(gameList[i])
                            }
                        }
                        recyclerAdapter = GameRecyclerAdapter(filteredList, this@MainFragment)
                        recyclerView.apply {
                            layoutManager = LinearLayoutManager(context)
                            adapter = recyclerAdapter
                        }
                    }

                    override fun afterTextChanged(p0: Editable?) {
                    }

                })
            }
            .addOnFailureListener { exception ->
                Log.w("MainFragment", "Error getting documents", exception)

            }
        return view
    }

    override fun onItemButtonClick(index: Int, game: Game, clickType: ItemClickType) {
        if (clickType == ItemClickType.EXPAND) {
            val bundle = Bundle()

            bundle.putString("imageUrl", game.imageUrl)
            bundle.putString("title", game.title)
            bundle.putString("description", game.description)
            bundle.putString("year", game.year)
            bundle.putString("developer", game.developer)

            val gameViewFragment = GameViewFragment()
            gameViewFragment.arguments = bundle

            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.activity, gameViewFragment)?.commit()
        }
    }
}


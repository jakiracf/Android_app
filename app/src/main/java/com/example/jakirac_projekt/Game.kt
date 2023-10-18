package com.example.jakirac_projekt

data class Game(
    var id: String = "",
    var imageUrl: String? = null,
    var title: String? = null,
    var description: String? = null,
    var developer: String? = null,
    var year: String? = null
)
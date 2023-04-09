package com.example.otrio.classes

class Player(private val name: String, private val color: String, private var wins: Int, private val pieces: ArrayList<ArrayList<Piece>>) {
    fun getName(): String {
        return name
    }

    fun getColor(): String {
        return color
    }

    fun getPieces(): ArrayList<ArrayList<Piece>> {
        return pieces
    }
}

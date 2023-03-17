package com.example.otrio

class Player(private val name: String, private val color: String, private var wins: Int, private val pieces: ArrayList<ArrayList<Piece>>) {
    fun winGame() {
        wins++
    }

    fun getName(): String {
        return name
    }

    fun getColor(): String {
        return color
    }

    fun getWins(): Int {
        return wins
    }

    fun getPieces(): ArrayList<ArrayList<Piece>> {
        return pieces
    }
}
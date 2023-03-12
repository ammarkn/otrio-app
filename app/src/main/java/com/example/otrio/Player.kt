package com.example.otrio

class Player(private val name: String, private var wins: Int, private val pieces: ArrayList<Piece>) {
    fun winGame() {
        wins++
    }

    fun getName(): String {
        return name
    }

    fun getWins(): Int {
        return wins
    }

    fun getPieces(): ArrayList<Piece> {
        return pieces
    }
}
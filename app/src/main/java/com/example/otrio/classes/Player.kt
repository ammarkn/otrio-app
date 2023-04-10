/*
File contributors: Deniz Kaptan
 */


package com.example.otrio.classes

//the players should have a name, color, and pieces associated with them to play the game
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

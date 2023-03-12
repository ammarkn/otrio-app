package com.example.otrio

class Piece(private val color: String, private val size: String) {
    var position: Int? = null
    fun setPiecePosition(position: Int) {
        this.position = position
    }

    fun getColor(): String {
        return color
    }

    fun getSize(): String {
        return size
    }

    fun getPiecePosition(): Int? {
        return position
    }
}
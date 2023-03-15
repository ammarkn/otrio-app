package com.example.otrio

class Piece(private val color: String, private val size: String) {
    var xPos: Int? = null;
    var yPos: Int? = null;

    fun setPiecePosition(xPos: Int, yPos: Int) {
        this.xPos = xPos
        this.yPos = yPos
    }

    fun getColor(): String {
        return color
    }

    fun getSize(): String {
        return size
    }

    fun getxPos() : Int? {
        return xPos
    }

    fun getyPos() : Int? {
        return yPos;
    }

}

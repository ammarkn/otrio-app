/*
File contributors: Katie Arsenault, Deniz Kaptan
 */

package com.example.otrio.classes

class Piece(private val color: String, private val size: String) {
    private var xPos: Int? = null
    private var yPos: Int? = null

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
        return yPos
    }

}

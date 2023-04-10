/*
File contributors: Katie Arsenault, Deniz Kaptan
 */

package com.example.otrio.classes

//the pieces on the gameboard require functions to track their position, size and color
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
}

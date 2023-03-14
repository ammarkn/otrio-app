package com.example.otrio

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import java.lang.System.arraycopy

class BoardActivity : AppCompatActivity(), View.OnClickListener {

    private var rounds = 0;

    private var redWin = false
    private var blueWin = false

    var redWins = 0
    var blueWins = 0

    private val buttons = Array(3) { arrayOfNulls<Button>(3) }

    private var textP1 = TextView(this)
    private var textP2 = TextView(this)

    var redPieces = ArrayList<Piece>()
    var bluePieces = ArrayList<Piece>()

    var redPeg0 = Piece("Red", "Peg")
    var redMedium0 = Piece("Red", "Medium")
    var redBig0 = Piece("Red", "Big")

    var redPeg1 = Piece("Red", "Peg")
    var redMedium1 = Piece("Red", "Medium")
    var redBig1 = Piece("Red", "Big")

    var redPeg2 = Piece("Red", "Peg")
    var redMedium2 = Piece("Red", "Medium")
    var redBig2 = Piece("Red", "Big")

    var bluePeg0 = Piece("Blue", "Peg")
    var blueMedium0 = Piece("Blue", "Medium")
    var blueBig0 = Piece("Blue", "Big")

    var bluePeg1 = Piece("Blue", "Peg")
    var blueMedium1 = Piece("Blue", "Medium")
    var blueBig1 = Piece("Blue", "Big")

    var bluePeg2 = Piece("Blue", "Peg")
    var blueMedium2 = Piece("Blue", "Medium")
    var blueBig2 = Piece("Blue", "Big")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board)

        val homeButtonClick = findViewById<Button>(R.id.homeButton)
        homeButtonClick.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val instructionsButtonClick = findViewById<Button>(R.id.instructionsButton)
        instructionsButtonClick.setOnClickListener {
            val intent = Intent(this, InstructionsActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        redPieces.add(redPeg0)
        redPieces.add(redMedium0)
        redPieces.add(redBig0)
        redPieces.add(redPeg1)
        redPieces.add(redMedium1)
        redPieces.add(redBig1)
        redPieces.add(redPeg2)
        redPieces.add(redMedium2)
        redPieces.add(redBig2)

        bluePieces.add(bluePeg0)
        bluePieces.add(blueMedium0)
        bluePieces.add(blueBig0)
        bluePieces.add(bluePeg1)
        bluePieces.add(blueMedium1)
        bluePieces.add(blueBig1)
        bluePieces.add(bluePeg2)
        bluePieces.add(blueMedium2)
        bluePieces.add(blueBig2)

        var p1 = Player("player1", redWins, redPieces)
        var p2 = Player("player2", blueWins, bluePieces)

        textP1 = findViewById(R.id.p1Text)
        textP2 = findViewById(R.id.p2Text)

        for (i in 0..2) {
            for (j in 0..2) {
                val buttonID = "grid" + i + j
                val resID = resources.getIdentifier(buttonID, "id", packageName)
                buttons[i][j] = findViewById(resID)
                buttons[i][j]!!.setOnClickListener(this)
            }
        }
    }

    override fun onClick(v: View) {

    }

    private fun placePiece(piece: Piece, xPos: Int, yPos: Int) {
        piece.setPiecePosition(xPos, yPos);
    }

    /*
    Method 1 of winning: https://otrio.com/pages/how-to-play
     */
    private fun samePieceWin() {
        var temp = Array(3) { arrayOfNulls <String>(3) }
        var board = Array(3) { arrayOfNulls <Piece?>(3) }

        for (i in 0..2) {
            for (j in 0..2) {
                temp[i][j] = buttons[i][j]!!.text.toString()
            }
        }

        for(i in temp.indices) {
            arraycopy(temp[i], 0, board[i], 0, temp[0].size)
        }

        //Rows
        for (i in 0..2) {
            if (board[i][0] == board[i][1] && board[i][0] == board[i][2] && board[i][0]!!.getColor() == "Red") {
                redWin = true
            }
            else if(board[i][0] == board[i][1] && board[i][0] == board[i][2] && board[i][0]!!.getColor() == "Blue") {
                blueWin = true
            }
        }

        //Columns
        for (i in 0..2) {
            if (board[0][i] == board[1][i] && board[0][i] == board[2][i] && board[0][i]!!.getColor() == "Red") {
                redWin = true
            }
            else if (board[0][i] == board[1][i] && board[0][i] == board[2][i] && board[0][i]!!.getColor() == "Blue") {
                blueWin = true
            }
        }

        //Diagonal (Left to right)
        if (board[0][0] == board[1][1] && board[0][0] == board[2][2] && board[0][0]!!.getColor() == "Red") {
            redWin = true
        }
        //Diagonal (Right to left)
        else if (board[0][2] == board[1][1] && board[0][2] == board[2][0] && board[0][2]!!.getColor() == "Blue") {
            blueWin = true
        }

        if(redWin) { redWins++ } else if(blueWin) { blueWins++ }
    }

    /*
    Method 2 of winning: https://otrio.com/pages/how-to-play
     */
    private fun sameSpaceWin() {
        var temp = Array(3) { arrayOfNulls <String>(3) }
        var board = Array(3) { arrayOfNulls <Piece?>(3) }

        for (i in 0..2) {
            for (j in 0..2) {
                temp[i][j] = buttons[i][j]!!.text.toString()
            }
        }

        for(i in temp.indices) {
            arraycopy(temp[i], 0, board[i], 0, temp[0].size)
        }
    }

    /*
    Method 3 of winning: https://otrio.com/pages/how-to-play
     */
    private fun ascendingDescendingWin() {
        var temp = Array(3) { arrayOfNulls <String>(3) }
        var board = Array(3) { arrayOfNulls <Piece?>(3) }

        for (i in 0..2) {
            for (j in 0..2) {
                temp[i][j] = buttons[i][j]!!.text.toString()
            }
        }

        for(i in temp.indices) {
            arraycopy(temp[i], 0, board[i], 0, temp[0].size)
        }

        // Ascending/descending row
        for (i in 0..2) {
            if(board[i][0]!!.getSize() == ("Peg") && board[i][1]!!.getSize() == ("Medium") && board[i][2]!!.getSize() == ("Big")
                && board[i][0]!!.getColor() == ("Red") && board[i][1]!!.getColor() == ("Red") && board[i][2]!!.getColor() == ("Red")) {
                redWin = true
            }
            else if(board[i][2]!!.getSize() == ("Peg") && board[i][1]!!.getSize() == ("Medium") && board[i][0]!!.getSize() == ("Big")
                && board[i][2]!!.getColor() == ("Red") && board[i][1]!!.getColor() == ("Red") && board[i][0]!!.getColor() == ("Red")) {
                redWin = true
            }
        }

        for (i in 0..2) {
            if(board[i][0]!!.getSize() == ("Peg") && board[i][1]!!.getSize() == ("Medium") && board[i][2]!!.getSize() == ("Big")
                && board[i][0]!!.getColor() == ("Blue") && board[i][1]!!.getColor() == ("Blue") && board[i][2]!!.getColor() == ("Blue")) {
                blueWin = true
            }
            else if(board[i][2]!!.getSize() == ("Peg") && board[i][1]!!.getSize() == ("Medium") && board[i][0]!!.getSize() == ("Big")
                && board[i][2]!!.getColor() == ("Blue") && board[i][1]!!.getColor() == ("Blue") && board[i][0]!!.getColor() == ("Blue")) {
                blueWin = true
            }
        }

        // Ascending/descending column
        for (i in 0..2) {
            if(board[0][i]!!.getSize() == ("Peg") && board[1][i]!!.getSize() == ("Medium") && board[2][i]!!.getSize() == ("Big")
                && board[0][i]!!.getColor() == ("Red") && board[1][i]!!.getColor() == ("Red") && board[2][i]!!.getColor() == ("Red")) {
                redWin = true
            }
            else if(board[2][i]!!.getSize() == ("Peg") && board[1][i]!!.getSize() == ("Medium") && board[0][i]!!.getSize() == ("Big")
                && board[2][i]!!.getColor() == ("Red") && board[1][i]!!.getColor() == ("Red") && board[0][i]!!.getColor() == ("Red")) {
                redWin = true
            }
        }

        for (i in 0..2) {
            if(board[0][i]!!.getSize() == ("Peg") && board[1][i]!!.getSize() == ("Medium") && board[2][i]!!.getSize() == ("Big")
                && board[0][i]!!.getColor() == ("Blue") && board[1][i]!!.getColor() == ("Blue") && board[2][i]!!.getColor() == ("Blue")) {
                blueWin = true
            }
            else if(board[2][i]!!.getSize() == ("Peg") && board[1][i]!!.getSize() == ("Medium") && board[0][i]!!.getSize() == ("Big")
                && board[2][i]!!.getColor() == ("Blue") && board[1][i]!!.getColor() == ("Blue") && board[0][i]!!.getColor() == ("Blue")) {
                blueWin = true
            }
        }

        //Diagonal (Left to right)
        if(board[0][0]!!.getSize() == ("Peg") && board[1][1]!!.getSize() == ("Medium") && board[2][2]!!.getSize() == ("Big")
            && board[0][0]!!.getColor() == ("Red") && board[1][1]!!.getColor() == ("Red") && board[2][2]!!.getColor() == ("Red")) {
            redWin = true
        }
        else if(board[0][0]!!.getSize() == ("Peg") && board[1][1]!!.getSize() == ("Medium") && board[2][2]!!.getSize() == ("Big")
            && board[0][0]!!.getColor() == ("Blue") && board[1][1]!!.getColor() == ("Blue") && board[2][2]!!.getColor() == ("Blue")) {
            blueWin = true
        }

        //Diagonal (Right to left)
        if(board[0][2]!!.getSize() == ("Peg") && board[1][1]!!.getSize() == ("Medium") && board[0][2]!!.getSize() == ("Big")
            && board[0][2]!!.getColor() == ("Red") && board[1][1]!!.getColor() == ("Red") && board[0][2]!!.getColor() == ("Red")) {
            redWin = true
        }
        else if(board[0][2]!!.getSize() == ("Peg") && board[1][1]!!.getSize() == ("Medium") && board[0][2]!!.getSize() == ("Big")
            && board[0][2]!!.getColor() == ("Blue") && board[1][1]!!.getColor() == ("Blue") && board[0][2]!!.getColor() == ("Blue")) {
            blueWin = true
        }

        if(redWin) { redWins++ } else if(blueWin) { blueWins++ }
    }
}
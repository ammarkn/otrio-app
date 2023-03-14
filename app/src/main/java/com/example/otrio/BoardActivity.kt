package com.example.otrio

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class BoardActivity : AppCompatActivity(), View.OnClickListener {

    private var rounds = 0;

    private var blueWin = false
    private var redWin = false

    var blueWins = 0
    var redWins = 0

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

    private fun placePiece(piece: Piece, position: Int) {
        piece.setPiecePosition(position);
    }

    /*
    Method 1 of winning: https://otrio.com/pages/how-to-play
     */
    private fun samePieceWin() {
        var grid = Array(3) { arrayOfNulls<String>(3) }
        for (i in 0..2) {
            for (j in 0..2) {
                grid[i][j] = buttons[i][j]!!.text.toString()
            }
        }

        //Rows
        for (i in 0..2) {
            if (grid[i][0] == grid[i][1] && grid[i][0] == grid[i][2] && grid[i][0]!!.startsWith("Red")) {
                redWin = true
            }
            else if(grid[i][0] == grid[i][1] && grid[i][0] == grid[i][2] && grid[i][0]!!.startsWith("Blue")) {
                blueWin = true
            }
        }

        //Columns
        for (i in 0..2) {
            if (grid[0][i] == grid[1][i] && grid[0][i] == grid[2][i] && grid[0][i]!!.startsWith("Red")) {
                redWin = true
            }
            else if (grid[0][i] == grid[1][i] && grid[0][i] == grid[2][i] && grid[0][i]!!.startsWith("Blue")) {
                blueWin = true
            }
        }

        //Diagonal (Left to right)
        if (grid[0][0] == grid[1][1] && grid[0][0] == grid[2][2] && grid[0][0]!!.startsWith("Red")) {
            redWin = true
        }
        //Diagonal (Right to left)
        else if (grid[0][2] == grid[1][1] && grid[0][2] == grid[2][0] && grid[0][2]!!.startsWith("Blue")) {
            blueWin = true
        }

        if(redWin) {
            redWins++
        }
        else if(blueWin) {
            blueWins++
        }
    }

    /*
    Method 2 of winning: https://otrio.com/pages/how-to-play
     */
    private fun sameSpaceWin() {
        if((redPeg0.getPiecePosition() == redMedium0.getPiecePosition() && redPeg0.getPiecePosition() == redBig0.getPiecePosition()) ||
                (redPeg1.getPiecePosition() == redMedium1.getPiecePosition() && redPeg1.getPiecePosition() == redBig1.getPiecePosition()) ||
                (redPeg2.getPiecePosition() == redMedium2.getPiecePosition() && redPeg2.getPiecePosition() == redBig2.getPiecePosition()) ||
                (redPeg1.getPiecePosition() == redMedium0.getPiecePosition() && redPeg1.getPiecePosition() == redBig0.getPiecePosition()) ||
                (redPeg0.getPiecePosition() == redMedium1.getPiecePosition() && redPeg0.getPiecePosition() == redBig0.getPiecePosition()) ||
                (redPeg0.getPiecePosition() == redMedium1.getPiecePosition() && redPeg0.getPiecePosition() == redBig1.getPiecePosition())) {
            blueWin = true
        }
        else if((bluePeg0.getPiecePosition() == blueMedium0.getPiecePosition() && bluePeg0.getPiecePosition() == blueBig0.getPiecePosition()) ||
            (bluePeg1.getPiecePosition() == blueMedium1.getPiecePosition() && bluePeg1.getPiecePosition() == blueBig1.getPiecePosition()) ||
            (bluePeg2.getPiecePosition() == blueMedium2.getPiecePosition() && bluePeg2.getPiecePosition() == blueBig2.getPiecePosition()) ||
            (bluePeg1.getPiecePosition() == blueMedium0.getPiecePosition() && bluePeg1.getPiecePosition() == blueBig0.getPiecePosition()) ||
            (bluePeg0.getPiecePosition() == blueMedium1.getPiecePosition() && bluePeg0.getPiecePosition() == blueBig0.getPiecePosition()) ||
            (bluePeg0.getPiecePosition() == blueMedium1.getPiecePosition() && bluePeg0.getPiecePosition() == blueBig1.getPiecePosition())) {
            blueWin = true
        }

        if(redWin) {
            redWins++
        }
        else if(blueWin) {
            blueWins++
        }
    }

    /*
    Method 3 of winning: https://otrio.com/pages/how-to-play
     */
    private fun ascendingDescendingWin() {
        var grid = Array(3) { arrayOfNulls<String>(3) }
        for (i in 0..2) {
            for (j in 0..2) {
                grid[i][j] = buttons[i][j]!!.text.toString()
            }
        }

        // Ascending/descending row
        for (i in 0..2) {
            if(grid[i][0]!!.startsWith("RedPeg") && grid[i][1]!!.startsWith("RedMedium") && grid[i][2]!!.startsWith("RedBig")) {
                redWin = true
            }
            else if(grid[i][2]!!.startsWith("RedPeg") && grid[i][1]!!.startsWith("RedMedium") && grid[i][0]!!.startsWith("RedBig")) {
                redWin = true
            }
        }

        for (i in 0..2) {
            if(grid[i][0]!!.startsWith("BluePeg") && grid[i][1]!!.startsWith("BlueMedium") && grid[i][2]!!.startsWith("BlueBig")) {
                blueWin = true
            }
            else if(grid[i][2]!!.startsWith("BluePeg") && grid[i][1]!!.startsWith("BlueMedium") && grid[i][0]!!.startsWith("BlueBig")) {
                blueWin = true
            }
        }

        // Ascending/descending column
        for (i in 0..2) {
            if(grid[0][i]!!.startsWith("RedPeg") && grid[1][i]!!.startsWith("RedMedium") && grid[2][i]!!.startsWith("RedBig")) {
                redWin = true
            }
            else if(grid[2][i]!!.startsWith("RedPeg") && grid[1][i]!!.startsWith("RedMedium") && grid[0][i]!!.startsWith("RedBig")) {
                redWin = true
            }
        }

        for (i in 0..2) {
            if(grid[0][i]!!.startsWith("BluePeg") && grid[1][i]!!.startsWith("BlueMedium") && grid[2][i]!!.startsWith("BlueBig")) {
                blueWin = true
            }
            else if(grid[2][i]!!.startsWith("BluePeg") && grid[1][i]!!.startsWith("BlueMedium") && grid[0][i]!!.startsWith("BlueBig")) {
                blueWin = true
            }
        }

        //Diagonal (Left to right)
        if(grid[0][0]!!.startsWith("RedPeg") && grid[1][1]!!.startsWith("RedMedium") && grid[2][2]!!.startsWith("RedBig")) {
            redWin = true
        }
        //Diagonal (Right to left)
        else if(grid[0][2]!!.startsWith("RedPeg") && grid[1][1]!!.startsWith("RedMedium") && grid[0][2]!!.startsWith("RedBig")) {
            redWin = true
        }

        //Diagonal (Left to right)
        if(grid[0][0]!!.startsWith("BluePeg") && grid[1][1]!!.startsWith("BlueMedium") && grid[2][2]!!.startsWith("BlueBig")) {
            blueWin = false
        }
        //Diagonal (Right to left)
        else if(grid[0][2]!!.startsWith("BluePeg") && grid[1][1]!!.startsWith("BlueMedium") && grid[0][2]!!.startsWith("BlueBig")) {
            blueWin = false
        }

        if(redWin) {
            redWins++
        }
        else if(blueWin) {
            blueWins++
        }
    }
}
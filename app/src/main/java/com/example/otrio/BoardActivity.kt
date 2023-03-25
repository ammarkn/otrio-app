package com.example.otrio

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import java.lang.System.arraycopy

class BoardActivity : AppCompatActivity(), View.OnClickListener {

    private var rounds = 0;

    private var pickedPiece = false

    private var redWin = false
    private var blueWin = false

    var redWins = 0
    var blueWins = 0

    private lateinit var sharedPreferences: SharedPreferences

    var redPieces = ArrayList<ArrayList<Piece>>()
    var bluePieces = ArrayList<ArrayList<Piece>>()

    var redPeg0 = Piece("red", "Peg")
    var redMedium0 = Piece("Red", "Medium")
    var redBig0 = Piece("red", "Big")

    var redPeg1 = Piece("red", "Peg")
    var redMedium1 = Piece("Red", "Medium")
    var redBig1 = Piece("red", "Big")

    var redPeg2 = Piece("Red", "Peg")
    var redMedium2 = Piece("red", "Medium")
    var redBig2 = Piece("Rred", "Big")

    var bluePeg0 = Piece("blue", "Peg")
    var blueMedium0 = Piece("Blue", "Medium")
    var blueBig0 = Piece("blue", "Big")

    var bluePeg1 = Piece("blue", "Peg")
    var blueMedium1 = Piece("Blue", "Medium")
    var blueBig1 = Piece("blue", "Big")

    var bluePeg2 = Piece("blue", "Peg")
    var blueMedium2 = Piece("Blue", "Medium")
    var blueBig2 = Piece("blue", "Big")

    var turn = 0 //the current turn number
    var pieceType = Piece("null","null") //the piece current picked up by one of players

    var p1 = Player("player1", "red", redWins, redPieces)
    var p2 = Player("player2", "blue", blueWins, bluePieces)

    var playerList = ArrayList<Player>() //save all play in one list

    private lateinit var turnplayer: TextView
    private lateinit var pegnumber: TextView
    private lateinit var mediumnumber: TextView
    private lateinit var bignumber: TextView
    private lateinit var picked: TextView
    private lateinit var redWinText : TextView
    private lateinit var blueWinText : TextView

    var redPeg = ArrayList<Piece>()
    var redMedium = ArrayList<Piece>()
    var redBig = ArrayList<Piece>()

    var bluePeg = ArrayList<Piece>()
    var blueMedium = ArrayList<Piece>()
    var blueBig = ArrayList<Piece>()

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

        val resetButtonClick = findViewById<Button>(R.id.resetButton)
        resetButtonClick.setOnClickListener {
            resetBoard()
        }

        redPeg.add(redPeg0)
        redPeg.add(redPeg1)
        redPeg.add(redPeg2)

        redMedium.add(redMedium0)
        redMedium.add(redMedium1)
        redMedium.add(redMedium2)

        redBig.add(redBig0)
        redBig.add(redBig1)
        redBig.add(redBig2)

        bluePeg.add(bluePeg0)
        bluePeg.add(bluePeg1)
        bluePeg.add(bluePeg2)

        blueMedium.add(blueMedium0)
        blueMedium.add(blueMedium1)
        blueMedium.add(blueMedium2)

        blueBig.add(blueBig0)
        blueBig.add(blueBig1)
        blueBig.add(blueBig2)

        redPieces.add(redPeg)
        redPieces.add(redMedium)
        redPieces.add(redBig)

        bluePieces.add(bluePeg)
        bluePieces.add(blueMedium)
        bluePieces.add(blueBig)

        //set player list
        playerList.add(p1)
        playerList.add(p2)

        //when users pick up a piece
        val buttonPeg = findViewById<Button>(R.id.peg)
        buttonPeg.setOnClickListener(this)

        val buttonMedium = findViewById<Button>(R.id.medium)
        buttonMedium.setOnClickListener(this)

        val buttonBig = findViewById<Button>(R.id.big)
        buttonBig.setOnClickListener(this)

        val button00 = findViewById<RelativeLayout>(R.id.grid00)
        button00.setOnClickListener(this)
        val button01 = findViewById<RelativeLayout>(R.id.grid01)
        button01.setOnClickListener(this)
        val button02 = findViewById<RelativeLayout>(R.id.grid02)
        button02.setOnClickListener(this)

        val button10 = findViewById<RelativeLayout>(R.id.grid10)
        button10.setOnClickListener(this)
        val button11 = findViewById<RelativeLayout>(R.id.grid11)
        button11.setOnClickListener(this)
        val button12 = findViewById<RelativeLayout>(R.id.grid12)
        button12.setOnClickListener(this)

        val button20 = findViewById<RelativeLayout>(R.id.grid20)
        button20.setOnClickListener(this)
        val button21 = findViewById<RelativeLayout>(R.id.grid21)
        button21.setOnClickListener(this)
        val button22 = findViewById<RelativeLayout>(R.id.grid22)
        button22.setOnClickListener(this)

        picked = findViewById(R.id.currentPiece) //will be used to show the update of current picked piece

        turnplayer = findViewById(R.id.playerTurn)
        turnplayer.text = "Player1"

        pegnumber = findViewById(R.id.pegNumber)
        pegnumber.text = p1.getPieces()[0].size.toString()

        mediumnumber = findViewById(R.id.mediumNumber)
        mediumnumber.text = p1.getPieces()[1].size.toString()

        bignumber = findViewById(R.id.bigNumber)
        bignumber.text = p1.getPieces()[2].size.toString()

        redWinText = findViewById(R.id.p1Text)
        blueWinText = findViewById(R.id.p2Text)

        sharedPreferences = getSharedPreferences("Wins", Context.MODE_PRIVATE)
        redWins = sharedPreferences.getInt("redWins", 0)
        blueWins = sharedPreferences.getInt("blueWins", 0)
    }


    override fun onClick(v: View) {
        when (v.id) {
            //R.id.peg -> buttonPegSelected = true
            R.id.peg -> handleButtonPegClick()
            R.id.medium -> handleButtonMediumClick()
            R.id.big -> handleButtonBigClick()

            R.id.grid00 -> handleLayoutOnClick("circle00",0,0)
            R.id.grid01 -> handleLayoutOnClick("circle01",0,1)
            R.id.grid02 -> handleLayoutOnClick("circle02",0,2)

            R.id.grid10 -> handleLayoutOnClick("circle10",1,0)
            R.id.grid11 -> handleLayoutOnClick("circle11",1,1)
            R.id.grid12 -> handleLayoutOnClick("circle12",1,2)

            R.id.grid20 -> handleLayoutOnClick("circle20",2,0)
            R.id.grid21 -> handleLayoutOnClick("circle21",2,1)
            R.id.grid22 -> handleLayoutOnClick("circle22",2,2)
        }

        if(sameSpaceWin() || samePieceWin()) {
            if(sameSpaceWin()) {
                if(redWin) {
//                    Toast.makeText(this, "Red win by same space win", Toast.LENGTH_SHORT).show()
                    println("Red win by same space win")
                    redWins++
                    resetBoard()
                }
                else if(blueWin) {
//                    Toast.makeText(this, "Red win by same space win", Toast.LENGTH_SHORT).show()
                    println("Blue win by same space win")
                    blueWins++
                    resetBoard()
                }
            }
            else if(samePieceWin()) {
                if(redWin) {
//                    Toast.makeText(this, "Red win by same space win", Toast.LENGTH_SHORT).show()
                    println("Red win by same piece win")
                    redWins++
                    resetBoard()
                }
                else if(blueWin) {
//                    Toast.makeText(this, "Red win by same space win", Toast.LENGTH_SHORT).show()
                    println("Blue win by same piece win")
                    blueWins++
                    resetBoard()
                }
            }
        }

        redWinText.text = "Player 1: " + redWins.toString() + " Wins"
        blueWinText.text = "Player 2: " + blueWins.toString() + " Wins"
    }

    private fun handleLayoutOnClick(vIdstart : String, Xpos : Int,Ypos : Int) {

        val newTag = resources.getIdentifier(pieceType.getColor()+pieceType.getSize().lowercase(),"drawable",packageName)
        //get id of image name of the piece base on the picked piece
        val vId = resources.getIdentifier(vIdstart+pieceType.getSize().lowercase(),"id",packageName)
        //get the id of the image view
        if(pickedPiece){ //check which button selected (Layout as a button)
            val imageView: ImageView = findViewById(vId)
            //check if peg already placed here
            var imageName = imageView.tag.toString()
            //get the name of the image in the imageview
            if(imageName.contains("blank")){
                imageView.setImageResource(newTag)
                imageView.setTag(pieceType.getColor()+pieceType.getSize().lowercase())//re-set the name for check late
                var removedElement = Piece("null","null")
                if(pieceType.getColor()==("red")){
                    when(pieceType.getSize()){
                        "Peg"->{
                            removedElement = redPeg.removeAt(redPeg.size - 1)
                        }
                        "Medium"->{
                            removedElement = redMedium.removeAt(redMedium.size - 1)
                        }
                        "Big"->{
                            removedElement = redBig.removeAt(redBig.size - 1)
                        }
                        else->{
                            removedElement = Piece("error","error")
                        }
                    }
                }
                else if(pieceType.getColor()==("blue")){
                    when(pieceType.getSize()){
                        "Peg"->{
                            removedElement = bluePeg.removeAt(bluePeg.size - 1)
                        }
                        "Medium"->{
                            removedElement = blueMedium.removeAt(blueMedium.size - 1)
                        }
                        "Big"->{
                            removedElement = blueBig.removeAt(blueBig.size - 1)
                        }
                        else->{
                            removedElement = Piece("error","error")
                        }
                    }
                }
                placePiece(removedElement, Xpos, Ypos)
            }
            pickedPiece = false
        }
    }

    private fun resetBoard() {
        recreate()

        Toast.makeText(this, "Game board was reset.", Toast.LENGTH_SHORT).show()

        val wins = sharedPreferences.edit()
        wins.putInt("redWins", redWins)
        wins.putInt("blueWins", blueWins)
        wins.apply()
    }

    private fun handleButtonPegClick() {
        if(playerList[turn%2].getPieces()[0].size > 0){
            pieceType = Piece(playerList[turn%2].getColor(),"Peg")
            picked.text = pieceType.getColor() + " " + pieceType.getSize()
            pickedPiece = true
        }
    }

    private fun handleButtonMediumClick() {
        if(playerList[turn%2].getPieces()[1].size > 0){
            pieceType = Piece(playerList[turn%2].getColor(),"Medium")
            picked.text = pieceType.getColor()+" "+pieceType.getSize()
            pickedPiece = true
        }
    }

    private fun handleButtonBigClick() {
        if(playerList[turn%2].getPieces()[2].size > 0){
            pieceType = Piece(playerList[turn%2].getColor(),"Big")
            picked.text = pieceType.getColor()+" "+pieceType.getSize()
            pickedPiece = true
        }
    }

    private fun showNextPlayerInfo() {
        turn ++
        val nextPlayer = playerList[turn%2]

        turnplayer.text = nextPlayer.getName()
        pegnumber.text = nextPlayer.getPieces()[0].size.toString()
        mediumnumber.text = nextPlayer.getPieces()[1].size.toString()
        bignumber.text = nextPlayer.getPieces()[2].size.toString()
        picked.text = ""
    }

    private fun placePiece(piece: Piece, xPos: Int, yPos: Int) {
        println("Place piece")
        piece.setPiecePosition(xPos, yPos)

        showNextPlayerInfo()
    }

    /*
    Method 1 of winning: https://otrio.com/pages/how-to-play
     */
    @SuppressLint("DiscouragedApi")
    private fun samePieceWin() : Boolean {
        val pieces = arrayOf("peg", "medium", "big")
        val players = arrayOf("red", "blue")

        // Checks all rows
        for(i in 0..2) {
            for(piece in pieces) {
                for(player in players) {
                    val img = ContextCompat.getDrawable(this, resources.getIdentifier("${player}${piece}", "drawable", packageName))

                    val layout1 = findViewById<RelativeLayout>(resources.getIdentifier("grid${i}0", "id", packageName))
                    val layout2 = findViewById<RelativeLayout>(resources.getIdentifier("grid${i}1", "id", packageName))
                    val layout3 = findViewById<RelativeLayout>(resources.getIdentifier("grid${i}2", "id", packageName))

                    val piece1 = layout1.findViewById<ImageView>(resources.getIdentifier("circle${i}0${piece}", "id", packageName))
                    val piece2 = layout2.findViewById<ImageView>(resources.getIdentifier("circle${i}1${piece}", "id", packageName))
                    val piece3 = layout3.findViewById<ImageView>(resources.getIdentifier("circle${i}2${piece}", "id", packageName))

                    if (piece1.drawable.constantState == img?.constantState && piece1.drawable.constantState == piece2.drawable.constantState && piece1.drawable.constantState == piece3.drawable.constantState) {
                        if(player == "red") {
                            redWin = true
                            return true
                        }
                        else if(player == "blue") {
                            blueWin = true
                            return true
                        }
                    }
                }
            }
        }

        // Checks all columns
        for(i in 0..2) {
            for(piece in pieces) {
                for(player in players) {
                    val img = ContextCompat.getDrawable(this, resources.getIdentifier("${player}${piece}", "drawable", packageName))

                    val layout1 = findViewById<RelativeLayout>(resources.getIdentifier("grid0${i}", "id", packageName))
                    val layout2 = findViewById<RelativeLayout>(resources.getIdentifier("grid1${i}", "id", packageName))
                    val layout3 = findViewById<RelativeLayout>(resources.getIdentifier("grid2${i}", "id", packageName))

                    val piece1 = layout1.findViewById<ImageView>(resources.getIdentifier("circle0${i}${piece}", "id", packageName))
                    val piece2 = layout2.findViewById<ImageView>(resources.getIdentifier("circle1${i}${piece}", "id", packageName))
                    val piece3 = layout3.findViewById<ImageView>(resources.getIdentifier("circle2${i}${piece}", "id", packageName))

                    if (piece1.drawable.constantState == img?.constantState && piece1.drawable.constantState == piece2.drawable.constantState && piece1.drawable.constantState == piece3.drawable.constantState) {
                        if(player == "red") {
                            redWin = true
                            return true
                        }
                        else if(player == "blue") {
                            blueWin = true
                            return true
                        }
                    }
                }
            }
        }

        // Checks diagonal (top left to bottom right)
        for(piece in pieces) {
            for(player in players) {
                val img = ContextCompat.getDrawable(this, resources.getIdentifier("${player}${piece}", "drawable", packageName))

                val layout1 = findViewById<RelativeLayout>(resources.getIdentifier("grid00", "id", packageName))
                val layout2 = findViewById<RelativeLayout>(resources.getIdentifier("grid11", "id", packageName))
                val layout3 = findViewById<RelativeLayout>(resources.getIdentifier("grid22", "id", packageName))

                val piece1 = layout1.findViewById<ImageView>(resources.getIdentifier("circle00${piece}", "id", packageName))
                val piece2 = layout2.findViewById<ImageView>(resources.getIdentifier("circle11${piece}", "id", packageName))
                val piece3 = layout3.findViewById<ImageView>(resources.getIdentifier("circle22${piece}", "id", packageName))

                if (piece1.drawable.constantState == img?.constantState && piece1.drawable.constantState == piece2.drawable.constantState && piece1.drawable.constantState == piece3.drawable.constantState) {
                    if(player == "red") {
                        redWin = true
                        return true
                    }
                    else if(player == "blue") {
                        blueWin = true
                        return true
                    }
                }
            }
        }

        // Checks diagonal (top right to bottom left)
        for(piece in pieces) {
            for(player in players) {
                val img = ContextCompat.getDrawable(this, resources.getIdentifier("${player}${piece}", "drawable", packageName))

                val layout1 = findViewById<RelativeLayout>(resources.getIdentifier("grid02", "id", packageName))
                val layout2 = findViewById<RelativeLayout>(resources.getIdentifier("grid11", "id", packageName))
                val layout3 = findViewById<RelativeLayout>(resources.getIdentifier("grid20", "id", packageName))

                val piece1 = layout1.findViewById<ImageView>(resources.getIdentifier("circle02${piece}", "id", packageName))
                val piece2 = layout2.findViewById<ImageView>(resources.getIdentifier("circle11${piece}", "id", packageName))
                val piece3 = layout3.findViewById<ImageView>(resources.getIdentifier("circle20${piece}", "id", packageName))

                if (piece1.drawable.constantState == img?.constantState && piece1.drawable.constantState == piece2.drawable.constantState && piece1.drawable.constantState == piece3.drawable.constantState) {
                    if(player == "red") {
                        redWin = true
                        return true
                    }
                    else if(player == "blue") {
                        blueWin = true
                        return true
                    }
                }
            }
        }

        return false
    }

    /*
    Method 2 of winning: https://otrio.com/pages/how-to-play
     */
    @SuppressLint("DiscouragedApi")
    private fun sameSpaceWin() : Boolean {
        for(i in 0..2) {
            for(j in 0..2) {
                val gridID = "grid$i$j"
                val circleID = "circle$i$j"

                val layout = findViewById<RelativeLayout>(resources.getIdentifier(gridID, "id", packageName))

                val peg = layout.findViewById<ImageView>(resources.getIdentifier(circleID + "peg", "id", packageName))
                val medium = layout.findViewById<ImageView>(resources.getIdentifier(circleID + "medium", "id", packageName))
                val big = layout.findViewById<ImageView>(resources.getIdentifier(circleID + "big", "id", packageName))

                if(peg.drawable.constantState == ContextCompat.getDrawable(this, R.drawable.redpeg)?.constantState) {
                    if(medium.drawable.constantState == ContextCompat.getDrawable(this, R.drawable.redmedium)?.constantState) {
                        if(big.drawable.constantState == ContextCompat.getDrawable(this, R.drawable.redbig)?.constantState) {
                            redWin = true
                            return true
                        }
                    }
                }
                else if(peg.drawable.constantState == ContextCompat.getDrawable(this, R.drawable.bluepeg)?.constantState) {
                    if(medium.drawable.constantState == ContextCompat.getDrawable(this, R.drawable.bluemedium)?.constantState) {
                        if(big.drawable.constantState == ContextCompat.getDrawable(this, R.drawable.bluebig)?.constantState) {
                            blueWin = true
                            return true
                        }
                    }
                }
            }
        }
        return false
    }

    /*
    Method 3 of winning: https://otrio.com/pages/how-to-play
     */
//    private fun ascendingDescendingWin() {
//        var temp = Array(3) { arrayOfNulls <String>(3) }
//        var board = Array(3) { arrayOfNulls <Piece?>(3) }
//
//        for (i in 0..2) {
//            for (j in 0..2) {
//                temp[i][j] = buttons[i][j]!!.text.toString()
//            }
//        }
//
//        for(i in temp.indices) {
//            arraycopy(temp[i], 0, board[i], 0, temp[0].size)
//        }
//
//        // Ascending/descending row
//        for (i in 0..2) {
//            if(board[i][0]!!.getSize() == ("Peg") && board[i][1]!!.getSize() == ("Medium") && board[i][2]!!.getSize() == ("Big")
//                && board[i][0]!!.getColor() == ("Red") && board[i][1]!!.getColor() == ("Red") && board[i][2]!!.getColor() == ("Red")) {
//                redWin = true
//            }
//            else if(board[i][2]!!.getSize() == ("Peg") && board[i][1]!!.getSize() == ("Medium") && board[i][0]!!.getSize() == ("Big")
//                && board[i][2]!!.getColor() == ("Red") && board[i][1]!!.getColor() == ("Red") && board[i][0]!!.getColor() == ("Red")) {
//                redWin = true
//            }
//        }
//
//        for (i in 0..2) {
//            if(board[i][0]!!.getSize() == ("Peg") && board[i][1]!!.getSize() == ("Medium") && board[i][2]!!.getSize() == ("Big")
//                && board[i][0]!!.getColor() == ("Blue") && board[i][1]!!.getColor() == ("Blue") && board[i][2]!!.getColor() == ("Blue")) {
//                blueWin = true
//            }
//            else if(board[i][2]!!.getSize() == ("Peg") && board[i][1]!!.getSize() == ("Medium") && board[i][0]!!.getSize() == ("Big")
//                && board[i][2]!!.getColor() == ("Blue") && board[i][1]!!.getColor() == ("Blue") && board[i][0]!!.getColor() == ("Blue")) {
//                blueWin = true
//            }
//        }
//
//        // Ascending/descending column
//        for (i in 0..2) {
//            if(board[0][i]!!.getSize() == ("Peg") && board[1][i]!!.getSize() == ("Medium") && board[2][i]!!.getSize() == ("Big")
//                && board[0][i]!!.getColor() == ("Red") && board[1][i]!!.getColor() == ("Red") && board[2][i]!!.getColor() == ("Red")) {
//                redWin = true
//            }
//            else if(board[2][i]!!.getSize() == ("Peg") && board[1][i]!!.getSize() == ("Medium") && board[0][i]!!.getSize() == ("Big")
//                && board[2][i]!!.getColor() == ("Red") && board[1][i]!!.getColor() == ("Red") && board[0][i]!!.getColor() == ("Red")) {
//                redWin = true
//            }
//        }
//
//        for (i in 0..2) {
//            if(board[0][i]!!.getSize() == ("Peg") && board[1][i]!!.getSize() == ("Medium") && board[2][i]!!.getSize() == ("Big")
//                && board[0][i]!!.getColor() == ("Blue") && board[1][i]!!.getColor() == ("Blue") && board[2][i]!!.getColor() == ("Blue")) {
//                blueWin = true
//            }
//            else if(board[2][i]!!.getSize() == ("Peg") && board[1][i]!!.getSize() == ("Medium") && board[0][i]!!.getSize() == ("Big")
//                && board[2][i]!!.getColor() == ("Blue") && board[1][i]!!.getColor() == ("Blue") && board[0][i]!!.getColor() == ("Blue")) {
//                blueWin = true
//            }
//        }
//
//        //Diagonal (Left to right)
//        if(board[0][0]!!.getSize() == ("Peg") && board[1][1]!!.getSize() == ("Medium") && board[2][2]!!.getSize() == ("Big")
//            && board[0][0]!!.getColor() == ("Red") && board[1][1]!!.getColor() == ("Red") && board[2][2]!!.getColor() == ("Red")) {
//            redWin = true
//        }
//        else if(board[0][0]!!.getSize() == ("Peg") && board[1][1]!!.getSize() == ("Medium") && board[2][2]!!.getSize() == ("Big")
//            && board[0][0]!!.getColor() == ("Blue") && board[1][1]!!.getColor() == ("Blue") && board[2][2]!!.getColor() == ("Blue")) {
//            blueWin = true
//        }
//
//        //Diagonal (Right to left)
//        if(board[0][2]!!.getSize() == ("Peg") && board[1][1]!!.getSize() == ("Medium") && board[0][2]!!.getSize() == ("Big")
//            && board[0][2]!!.getColor() == ("Red") && board[1][1]!!.getColor() == ("Red") && board[0][2]!!.getColor() == ("Red")) {
//            redWin = true
//        }
//        else if(board[0][2]!!.getSize() == ("Peg") && board[1][1]!!.getSize() == ("Medium") && board[0][2]!!.getSize() == ("Big")
//            && board[0][2]!!.getColor() == ("Blue") && board[1][1]!!.getColor() == ("Blue") && board[0][2]!!.getColor() == ("Blue")) {
//            blueWin = true
//        }
//
//        //if we're tracking wins, we'll need to reset the game??
//        if(redWin) { redWins++ } else if(blueWin) { blueWins++ }
//    }
}
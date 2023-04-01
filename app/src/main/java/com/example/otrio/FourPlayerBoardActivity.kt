package com.example.otrio

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.media.Image
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import java.lang.System.arraycopy


class FourPlayerBoardActivity : AppCompatActivity(), View.OnClickListener {

    private var playCount = 0;

    private var pickedPiece = false

    //variables to store each player's number of wins
    private var redWin = false
    private var blueWin = false
    private var yellowWin = false
    private var greenWin = false

    //variables to save each player's win count
    var redWins = 0
    var blueWins = 0
    var yellowWins = 0
    var greenWins = 0

    private lateinit var winData: SharedPreferences


    //array to store buttons
    private val buttons = Array(3) { arrayOfNulls<Button>(3) }

    //variables for text - NEED THESE STILL????
    private lateinit var textP1 : TextView
    private lateinit var textP2 : TextView
    private lateinit var textP3 : TextView
    private lateinit var textP4 : TextView

    //arraylists to store each player's pieces
    var redPieces = ArrayList<ArrayList<Piece>>()
    var bluePieces = ArrayList<ArrayList<Piece>>()
    var yellowPieces = ArrayList<ArrayList<Piece>>()
    var greenPieces = ArrayList<ArrayList<Piece>>()

    //create pieces for each player (3 of each size)
    var redPeg0 = Piece("red", "Peg")
    var redMedium0 = Piece("red", "Medium")
    var redBig0 = Piece("red", "Big")

    var redPeg1 = Piece("red", "Peg")
    var redMedium1 = Piece("red", "Medium")
    var redBig1 = Piece("red", "Big")

    var redPeg2 = Piece("red", "Peg")
    var redMedium2 = Piece("red", "Medium")
    var redBig2 = Piece("red", "Big")

    var bluePeg0 = Piece("blue", "Peg")
    var blueMedium0 = Piece("blue", "Medium")
    var blueBig0 = Piece("blue", "Big")

    var bluePeg1 = Piece("blue", "Peg")
    var blueMedium1 = Piece("blue", "Medium")
    var blueBig1 = Piece("blue", "Big")

    var bluePeg2 = Piece("blue", "Peg")
    var blueMedium2 = Piece("blue", "Medium")
    var blueBig2 = Piece("blue", "Big")

    var yellowPeg0 = Piece("yellow", "Peg")
    var yellowMedium0 = Piece("yellow", "Medium")
    var yellowBig0 = Piece("yellow", "Big")

    var yellowPeg1 = Piece("yellow", "Peg")
    var yellowMedium1 = Piece("yellow", "Medium")
    var yellowBig1 = Piece("yellow", "Big")

    var yellowPeg2 = Piece("yellow", "Peg")
    var yellowMedium2 = Piece("yellow", "Medium")
    var yellowBig2 = Piece("yellow", "Big")

    var greenPeg0 = Piece("green", "Peg")
    var greenMedium0 = Piece("green", "Medium")
    var greenBig0 = Piece("green", "Big")

    var greenPeg1 = Piece("green", "Peg")
    var greenMedium1 = Piece("green", "Medium")
    var greenBig1 = Piece("green", "Big")

    var greenPeg2 = Piece("green", "Peg")
    var greenMedium2 = Piece("green", "Medium")
    var greenBig2 = Piece("green", "Big")

    //set turn count to 0
    var turn = 0 //the current turn number
    //the piece current picked up by one of players
    var pieceType = Piece("null","null")

    //create the players, storing their pieces and win counts
    var p1 = Player("player1", "red", redWins, redPieces)
    var p2 = Player("player2", "blue", blueWins, bluePieces)
    var p3 = Player("player3", "yellow", yellowWins, yellowPieces)
    var p4 = Player("player4", "green", greenWins, greenPieces)

    //arraylist to save all players in
    var playerList = ArrayList<Player>()

    //text view variables that will track player info on the board
    private lateinit var turnplayer: TextView
    private lateinit var pegnumber: TextView
    private lateinit var mediumnumber: TextView
    private lateinit var bignumber: TextView
    private lateinit var picked: TextView
    private lateinit var redWinText : TextView
    private lateinit var blueWinText : TextView
    private lateinit var yellowWinText : TextView
    private lateinit var greenWinText : TextView


    //create arraylist for each piece size+color
    var redPeg = ArrayList<Piece>()
    var redMedium = ArrayList<Piece>()
    var redBig = ArrayList<Piece>()

    var bluePeg = ArrayList<Piece>()
    var blueMedium = ArrayList<Piece>()
    var blueBig = ArrayList<Piece>()

    var yellowPeg = ArrayList<Piece>()
    var yellowMedium = ArrayList<Piece>()
    var yellowBig = ArrayList<Piece>()

    var greenPeg = ArrayList<Piece>()
    var greenMedium = ArrayList<Piece>()
    var greenBig = ArrayList<Piece>()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_four_player_board)

        //create button to return to home page
        val homeButtonClick = findViewById<Button>(R.id.homeButton)
        homeButtonClick.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        //create button to access instructions
        val instructionsButtonClick = findViewById<Button>(R.id.instructionsButton)
        instructionsButtonClick.setOnClickListener {
            val intent = Intent(this, InstructionsActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        //create button to reset board
        val resetButtonClick = findViewById<Button>(R.id.resetButton)
        resetButtonClick.setOnClickListener {
            resetBoard()
        }


        //add each individual pieces to arraylists of pieces of same size+color
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

        yellowPeg.add(yellowPeg0)
        yellowPeg.add(yellowPeg1)
        yellowPeg.add(yellowPeg2)

        yellowMedium.add(yellowMedium0)
        yellowMedium.add(yellowMedium1)
        yellowMedium.add(yellowMedium2)

        yellowBig.add(yellowBig0)
        yellowBig.add(yellowBig1)
        yellowBig.add(yellowBig2)

        greenPeg.add(greenPeg0)
        greenPeg.add(greenPeg1)
        greenPeg.add(greenPeg2)

        greenMedium.add(greenMedium0)
        greenMedium.add(greenMedium1)
        greenMedium.add(greenMedium2)

        greenBig.add(greenBig0)
        greenBig.add(greenBig1)
        greenBig.add(greenBig2)

        //add each size piece arraylist to arraylists for each color
        redPieces.add(redPeg)
        redPieces.add(redMedium)
        redPieces.add(redBig)

        bluePieces.add(bluePeg)
        bluePieces.add(blueMedium)
        bluePieces.add(blueBig)

        yellowPieces.add(yellowPeg)
        yellowPieces.add(yellowMedium)
        yellowPieces.add(yellowBig)

        greenPieces.add(greenPeg)
        greenPieces.add(greenMedium)
        greenPieces.add(greenBig)

        //add players to player list
        playerList.add(p1)
        playerList.add(p2)
        playerList.add(p3)
        playerList.add(p4)

        //set onclick listeners for player pieces
        val buttonPeg = findViewById<Button>(R.id.peg)
        buttonPeg.setOnClickListener(this)

        val buttonMedium = findViewById<Button>(R.id.medium)
        buttonMedium.setOnClickListener(this)

        val buttonBig = findViewById<Button>(R.id.big)
        buttonBig.setOnClickListener(this)

        //set onclick listeners for each board piece
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

        //will be used to show the update of current picked piece
        picked = findViewById(R.id.currentPiece)

        //set variable to track current player's turn on the board
        turnplayer = findViewById(R.id.playerTurn)
        turnplayer.text = "Player1"

        //set variables to track number of pieces player has left to use
        pegnumber = findViewById(R.id.pegNumber)
        pegnumber.text = p1.getPieces()[0].size.toString()

        mediumnumber = findViewById(R.id.mediumNumber)
        mediumnumber.text = p1.getPieces()[1].size.toString()

        bignumber = findViewById(R.id.bigNumber)
        bignumber.text = p1.getPieces()[2].size.toString()

        //set win text for each player
        //textP1 = findViewById(R.id.p1Text)
        //textP2 = findViewById(R.id.p2Text)
        //textP3 = findViewById(R.id.p3Text)
        //textP4 = findViewById(R.id.p4Text)
        redWinText = findViewById(R.id.p1Text)
        blueWinText = findViewById(R.id.p2Text)
        yellowWinText = findViewById(R.id.p3Text)
        greenWinText = findViewById(R.id.p4Text)

        winData = getSharedPreferences("Wins", Context.MODE_PRIVATE)
        redWins = winData.getInt("redWins", 0)
        blueWins = winData.getInt("blueWins", 0)
        yellowWins = winData.getInt("yellowWins", 0)
        greenWins = winData.getInt("greenWins", 0)


    }

    override fun onClick(v: View) {
        //call functions for onclick
        when (v.id) {
            //if user clicks a piece to play
            R.id.peg -> handleButtonPegClick()
            R.id.medium -> handleButtonMediumClick()
            R.id.big -> handleButtonBigClick()

            //if user clicks top row on board
            R.id.grid00 -> handleLayoutOnClick("circle00",0,0)
            R.id.grid01 -> handleLayoutOnClick("circle01",0,1)
            R.id.grid02 -> handleLayoutOnClick("circle02",0,2)

            //if user clicks middle row on board
            R.id.grid10 -> handleLayoutOnClick("circle10",1,0)
            R.id.grid11 -> handleLayoutOnClick("circle11",1,1)
            R.id.grid12 -> handleLayoutOnClick("circle12",1,2)

            //if user
            R.id.grid20 -> handleLayoutOnClick("circle20",2,0)
            R.id.grid21 -> handleLayoutOnClick("circle21",2,1)
            R.id.grid22 -> handleLayoutOnClick("circle22",2,2)
        }

        if(sameSpaceWin() || samePieceWin() || ascendingDescendingWin()) {
            if(sameSpaceWin()) {
                if(redWin) {
//                    Toast.makeText(this, "Red win by same space win", Toast.LENGTH_SHORT).show()
                    println("Red win by same space win")
                    redWins++
                    resetBoard()
                }
                else if(blueWin) {
//                    Toast.makeText(this, "Blue win by same space win", Toast.LENGTH_SHORT).show()
                    println("Blue win by same space win")
                    blueWins++
                    resetBoard()
                }
                else if(yellowWin) {
//                    Toast.makeText(this, "Yellow win by same space win", Toast.LENGTH_SHORT).show()
                    println("Yellow win by same space win")
                    yellowWins++
                    resetBoard()
                }
                else if(greenWin) {
//                    Toast.makeText(this, "Green win by same space win", Toast.LENGTH_SHORT).show()
                    println("Green win by same space win")
                    greenWins++
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
//                    Toast.makeText(this, "Blue win by same space win", Toast.LENGTH_SHORT).show()
                    println("Blue win by same piece win")
                    blueWins++
                    resetBoard()
                }
                else if(yellowWin) {
//                    Toast.makeText(this, "Yellow win by same space win", Toast.LENGTH_SHORT).show()
                    println("Yellow win by same piece win")
                    yellowWins++
                    resetBoard()
                }
                else if(greenWin) {
//                    Toast.makeText(this, "Green win by same space win", Toast.LENGTH_SHORT).show()
                    println("Green win by same piece win")
                    greenWins++
                    resetBoard()
                }
            }
            else if(ascendingDescendingWin()) {
                if(redWin) {
//                    Toast.makeText(this, "Red win by ascending descending win", Toast.LENGTH_SHORT).show()
                    println("Red win by ascending descending win")
                    redWins++
                    resetBoard()
                }
                else if(blueWin) {
//                    Toast.makeText(this, "Blue win by ascending descending win", Toast.LENGTH_SHORT).show()
                    println("Blue win by ascending descending win")
                    blueWins++
                    resetBoard()
                }
                else if(yellowWin) {
//                    Toast.makeText(this, "Yellow win by ascending descending win", Toast.LENGTH_SHORT).show()
                    println("Yellow win by ascending descending win")
                    yellowWins++
                    resetBoard()
                }
                else if(greenWin) {
//                    Toast.makeText(this, "Green win by ascending descending win", Toast.LENGTH_SHORT).show()
                    println("Green win by ascending descending win")
                    greenWins++
                    resetBoard()
                }
            }
        }
        else if(draw()) {
//                    Toast.makeText(this, "Draw", Toast.LENGTH_SHORT).show()
            println("Draw")
            resetBoard()
        }

        redWinText.text = "Player 1: " + redWins.toString() + " Wins"
        blueWinText.text = "Player 2: " + blueWins.toString() + " Wins"
        yellowWinText.text = "Player 3: " + yellowWins.toString() + " Wins"
        greenWinText.text = "Player 4: " + greenWins.toString() + " Wins"

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
                //if piece color is red
                if(pieceType.getColor()==("red")){
                    //get size of piece
                    when(pieceType.getSize()){
                        //remove red peg from list
                        "Peg"->{
                            removedElement = redPeg.removeAt(redPeg.size - 1)
                            playCount++
                        }
                        //remove red medium from list
                        "Medium"->{
                            removedElement = redMedium.removeAt(redMedium.size - 1)
                            playCount++
                        }
                        //remove red big from list
                        "Big"->{
                            removedElement = redBig.removeAt(redBig.size - 1)
                            playCount++
                        }
                        //set removed piece to error, to handle error cases
                        else->{
                            removedElement = Piece("error","error")
                        }
                    }
                }
                //if piece color is blue
                else if(pieceType.getColor()==("blue")){
                    //get size of piece
                    when(pieceType.getSize()){
                        //remove blue peg from list
                        "Peg"->{
                            removedElement = bluePeg.removeAt(bluePeg.size - 1)
                            playCount++
                        }
                        //remove blue medium from list
                        "Medium"->{
                            removedElement = blueMedium.removeAt(blueMedium.size - 1)
                            playCount++
                        }
                        //remove blue big from list
                        "Big"->{
                            removedElement = blueBig.removeAt(blueBig.size - 1)
                            playCount++
                        }
                        //set removed piece to error, to handle error cases
                        else->{
                            removedElement = Piece("error","error")
                        }
                    }
                }
                //if piece color is yellow
                else if(pieceType.getColor()==("yellow")){
                    //get size of piece
                    when(pieceType.getSize()){
                        //remove yellow peg from list
                        "Peg"->{
                            removedElement = yellowPeg.removeAt(yellowPeg.size - 1)
                            playCount++
                        }
                        //remove yellow medium from list
                        "Medium"->{
                            removedElement = yellowMedium.removeAt(yellowMedium.size - 1)
                            playCount++
                        }
                        //remove yellow big from list
                        "Big"->{
                            removedElement = yellowBig.removeAt(yellowBig.size - 1)
                            playCount++
                        }
                        //set removed piece to error, to handle error cases
                        else->{
                            removedElement = Piece("error","error")
                        }
                    }
                }
                //if piece color is green
                else if(pieceType.getColor()==("green")){
                    //get size of piece
                    when(pieceType.getSize()){
                        //remove green peg from list
                        "Peg"->{
                            removedElement = greenPeg.removeAt(greenPeg.size - 1)
                            playCount++
                        }
                        //remove green medium from list
                        "Medium"->{
                            removedElement = greenMedium.removeAt(greenMedium.size - 1)
                            playCount++
                        }
                        //remove green big from list
                        "Big"->{
                            removedElement = greenBig.removeAt(greenBig.size - 1)
                            playCount++
                        }
                        //set removed piece to error, to handle error cases
                        else->{
                            removedElement = Piece("error","error")
                        }
                    }
                }
                //call placepiece function to finish placing the piece on the board
                placePiece(removedElement, Xpos, Ypos)
            }
            //reset picked piece as the piece to play should no longer be selected
            pickedPiece = false
        }
        else{
            Toast.makeText(this, "Select a piece then select a location on the board", Toast.LENGTH_SHORT).show()
        }
    }

    private fun resetBoard() {
        val builder = AlertDialog.Builder(this)

        if(!blueWin && !redWin && !yellowWin && !greenWin) {
            builder.setTitle("Reset Board")
            builder.setMessage("Are you sure you want to reset the board? This action cannot be undone.")
            builder.setPositiveButton("Yes") { _, _ ->
                val wins = winData.edit()
                wins.putInt("redWins", redWins)
                wins.putInt("blueWins", blueWins)
                wins.putInt("yellowWins", yellowWins)
                wins.putInt("greenWins", greenWins)
                wins.apply()

                recreate()
                Toast.makeText(this, "Game board was reset.", Toast.LENGTH_SHORT).show()
            }
            builder.setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
        }
        else {
            if(redWin) {
                builder.setTitle("Winner: Player 1 (Red)")
            }
            else if(blueWin){
                builder.setTitle("Winner: Player 2 (Blue)")
            }
            else if(yellowWin){
                builder.setTitle("Winner: Player 3 (Yellow)")
            }
            else if(greenWin){
                builder.setTitle("Winner: Player 4 (Green)")
            }
            builder.setMessage("Would you like to play again?")
            builder.setPositiveButton("Yes") { _, _ ->
                val wins = winData.edit()
                wins.putInt("redWins", redWins)
                wins.putInt("blueWins", blueWins)
                wins.putInt("yellowWins", yellowWins)
                wins.putInt("greenWins", greenWins)
                wins.apply()

                recreate()
                Toast.makeText(this, "Game board was reset.", Toast.LENGTH_SHORT).show()
            }
            builder.setNegativeButton("No") { dialog, id ->
                dialog.dismiss()
            }
            builder.setNeutralButton("Take Me Home") { _, _ ->
                val intent = Intent(this, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
                startActivity(intent)
            }
        }

        val alert = builder.create()
        alert.show()
    }


    private fun handleButtonPegClick() {
        //check if user has any peg pieces left
        if(playerList[turn%4].getPieces()[0].size > 0){
            //set piece type if they have remaining piece to play
            pieceType = Piece(playerList[turn%4].getColor(),"Peg")
            //set text for piece to be displayed on screen
            picked.text = pieceType.getColor() + " " + pieceType.getSize()
            //confirm a piece has been picked
            pickedPiece = true
        }
        else{
            Toast.makeText(this, "There are no Peg pieces left, please choose another piece", Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleButtonMediumClick() {
        //check if user has any medium pieces left
        if(playerList[turn%4].getPieces()[1].size > 0){
            //set piece type if they have remaining piece to play
            pieceType = Piece(playerList[turn%4].getColor(),"Medium")
            //set text for piece to be displayed on screen
            picked.text = pieceType.getColor()+" "+pieceType.getSize()
            //confirm a piece has been picked
            pickedPiece = true
        }
        else{
            Toast.makeText(this, "There are no Medium pieces left, please choose another piece", Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleButtonBigClick() {
        //check if user has any big pieces left
        if(playerList[turn%4].getPieces()[2].size > 0){
            //set piece type if they have remaining piece to play
            pieceType = Piece(playerList[turn%4].getColor(),"Big")
            //set text for piece to be displayed on screen
            picked.text = pieceType.getColor()+" "+pieceType.getSize()
            //confirm a piece has been picked
            pickedPiece = true
        }
        else{
            Toast.makeText(this, "There are no Big pieces left, please choose another piece", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showNextPlayerInfo() {
        //increase number of turns played
        turn ++
        //use modulaus to determine next player based on number of turns
        val nextPlayer = playerList[turn%4]

        //get new player's name, and number of pieces
        turnplayer.text = nextPlayer.getName()
        pegnumber.text = nextPlayer.getPieces()[0].size.toString()
        mediumnumber.text = nextPlayer.getPieces()[1].size.toString()
        bignumber.text = nextPlayer.getPieces()[2].size.toString()
        picked.text = ""
    }

    private fun placePiece(piece: Piece, xPos: Int, yPos: Int) {
        //set the piece location for win conditions
        piece.setPiecePosition(xPos, yPos);
        //call function to show next player's information
        showNextPlayerInfo()
    }

    /*
    Method 1 of winning: https://otrio.com/pages/how-to-play
     */
    @SuppressLint("DiscouragedApi")
    private fun samePieceWin() : Boolean {
        val pieces = arrayOf("peg", "medium", "big")
        val players = arrayOf("red", "blue", "yellow", "green")

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
                        else if(player == "yellow") {
                            yellowWin = true
                            return true
                        }
                        else if(player == "green") {
                            greenWin = true
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
                        else if(player == "yellow") {
                            yellowWin = true
                            return true
                        }
                        else if(player == "green") {
                            greenWin = true
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
                    else if(player == "yellow") {
                        yellowWin = true
                        return true
                    }
                    else if(player == "green") {
                        greenWin = true
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
                    else if(player == "yellow") {
                        yellowWin = true
                        return true
                    }
                    else if(player == "green") {
                        greenWin = true
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
                else if(peg.drawable.constantState == ContextCompat.getDrawable(this, R.drawable.yellowpeg)?.constantState) {
                    if(medium.drawable.constantState == ContextCompat.getDrawable(this, R.drawable.yellowmedium)?.constantState) {
                        if(big.drawable.constantState == ContextCompat.getDrawable(this, R.drawable.yellowbig)?.constantState) {
                            yellowWin = true
                            return true
                        }
                    }
                }
                else if(peg.drawable.constantState == ContextCompat.getDrawable(this, R.drawable.greenpeg)?.constantState) {
                    if(medium.drawable.constantState == ContextCompat.getDrawable(this, R.drawable.greenmedium)?.constantState) {
                        if(big.drawable.constantState == ContextCompat.getDrawable(this, R.drawable.greenbig)?.constantState) {
                            greenWin = true
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
    @SuppressLint("DiscouragedApi")
    private fun ascendingDescendingWin() : Boolean {
        val pieces = arrayOf("peg", "medium", "big")
        val players = arrayOf("red", "blue", "yellow", "green")

        // Checks all rows
        for(i in 0..2) {
            for(piece in pieces) {
                for(player in players) {
                    val peg = ContextCompat.getDrawable(this, resources.getIdentifier("${player}peg", "drawable", packageName))
                    val medium = ContextCompat.getDrawable(this, resources.getIdentifier("${player}medium", "drawable", packageName))
                    val big = ContextCompat.getDrawable(this, resources.getIdentifier("${player}big", "drawable", packageName))

                    val layout1 = findViewById<RelativeLayout>(resources.getIdentifier("grid${i}0", "id", packageName))
                    val layout2 = findViewById<RelativeLayout>(resources.getIdentifier("grid${i}1", "id", packageName))
                    val layout3 = findViewById<RelativeLayout>(resources.getIdentifier("grid${i}2", "id", packageName))

                    val piece1 = layout1.findViewById<ImageView>(resources.getIdentifier("circle${i}0peg", "id", packageName))
                    val piece2 = layout2.findViewById<ImageView>(resources.getIdentifier("circle${i}1medium", "id", packageName))
                    val piece3 = layout3.findViewById<ImageView>(resources.getIdentifier("circle${i}2big", "id", packageName))

                    val piece4 = layout3.findViewById<ImageView>(resources.getIdentifier("circle${i}2peg", "id", packageName))
                    val piece5 = layout2.findViewById<ImageView>(resources.getIdentifier("circle${i}1medium", "id", packageName))
                    val piece6 = layout1.findViewById<ImageView>(resources.getIdentifier("circle${i}0big", "id", packageName))

                    if(piece1.drawable.constantState == peg?.constantState && piece2.drawable.constantState == medium?.constantState && piece3.drawable.constantState == big?.constantState) {
                        if(player == "red") {
                            redWin = true
                            return true
                        }
                        else if(player == "blue") {
                            blueWin = true
                            return true
                        }
                        else if(player == "yellow") {
                            yellowWin = true
                            return true
                        }
                        else if(player == "green") {
                            greenWin = true
                            return true
                        }
                    }
                    else if(piece4.drawable.constantState == peg?.constantState && piece5.drawable.constantState == medium?.constantState && piece6.drawable.constantState == big?.constantState) {
                        if(player == "red") {
                            redWin = true
                            return true
                        }
                        else if(player == "blue") {
                            blueWin = true
                            return true
                        }
                        else if(player == "yellow") {
                            yellowWin = true
                            return true
                        }
                        else if(player == "green") {
                            greenWin = true
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
                    val peg = ContextCompat.getDrawable(this, resources.getIdentifier("${player}peg", "drawable", packageName))
                    val medium = ContextCompat.getDrawable(this, resources.getIdentifier("${player}medium", "drawable", packageName))
                    val big = ContextCompat.getDrawable(this, resources.getIdentifier("${player}big", "drawable", packageName))

                    val layout1 = findViewById<RelativeLayout>(resources.getIdentifier("grid0${i}", "id", packageName))
                    val layout2 = findViewById<RelativeLayout>(resources.getIdentifier("grid1${i}", "id", packageName))
                    val layout3 = findViewById<RelativeLayout>(resources.getIdentifier("grid2${i}", "id", packageName))

                    val piece1 = layout1.findViewById<ImageView>(resources.getIdentifier("circle0${i}peg", "id", packageName))
                    val piece2 = layout2.findViewById<ImageView>(resources.getIdentifier("circle1${i}medium", "id", packageName))
                    val piece3 = layout3.findViewById<ImageView>(resources.getIdentifier("circle2${i}big", "id", packageName))

                    val piece4 = layout3.findViewById<ImageView>(resources.getIdentifier("circle2${i}peg", "id", packageName))
                    val piece5 = layout2.findViewById<ImageView>(resources.getIdentifier("circle1${i}medium", "id", packageName))
                    val piece6 = layout1.findViewById<ImageView>(resources.getIdentifier("circle0${i}big", "id", packageName))

                    if(piece1.drawable.constantState == peg?.constantState && piece2.drawable.constantState == medium?.constantState && piece3.drawable.constantState == big?.constantState) {
                        if(player == "red") {
                            redWin = true
                            return true
                        }
                        else if(player == "blue") {
                            blueWin = true
                            return true
                        }
                        else if(player == "yellow") {
                            yellowWin = true
                            return true
                        }
                        else if(player == "green") {
                            greenWin = true
                            return true
                        }
                    }
                    else if(piece4.drawable.constantState == peg?.constantState && piece5.drawable.constantState == medium?.constantState && piece6.drawable.constantState == big?.constantState) {
                        if(player == "red") {
                            redWin = true
                            return true
                        }
                        else if(player == "blue") {
                            blueWin = true
                            return true
                        }
                        else if(player == "yellow") {
                            yellowWin = true
                            return true
                        }
                        else if(player == "green") {
                            greenWin = true
                            return true
                        }
                    }
                }
            }
        }

        // Checks diagonals (top left to bottom right)
        for(piece in pieces) {
            for(player in players) {
                val peg = ContextCompat.getDrawable(this, resources.getIdentifier("${player}peg", "drawable", packageName))
                val medium = ContextCompat.getDrawable(this, resources.getIdentifier("${player}medium", "drawable", packageName))
                val big = ContextCompat.getDrawable(this, resources.getIdentifier("${player}big", "drawable", packageName))

                val layout1 = findViewById<RelativeLayout>(resources.getIdentifier("grid00", "id", packageName))
                val layout2 = findViewById<RelativeLayout>(resources.getIdentifier("grid11", "id", packageName))
                val layout3 = findViewById<RelativeLayout>(resources.getIdentifier("grid22", "id", packageName))

                val piece1 = layout1.findViewById<ImageView>(resources.getIdentifier("circle00peg", "id", packageName))
                val piece2 = layout2.findViewById<ImageView>(resources.getIdentifier("circle11medium", "id", packageName))
                val piece3 = layout3.findViewById<ImageView>(resources.getIdentifier("circle22big", "id", packageName))

                val piece4 = layout3.findViewById<ImageView>(resources.getIdentifier("circle22peg", "id", packageName))
                val piece5 = layout2.findViewById<ImageView>(resources.getIdentifier("circle11medium", "id", packageName))
                val piece6 = layout1.findViewById<ImageView>(resources.getIdentifier("circle00big", "id", packageName))

                if(piece1.drawable.constantState == peg?.constantState && piece2.drawable.constantState == medium?.constantState && piece3.drawable.constantState == big?.constantState) {
                    if(player == "red") {
                        redWin = true
                        return true
                    }
                    else if(player == "blue") {
                        blueWin = true
                        return true
                    }
                    else if(player == "yellow") {
                        yellowWin = true
                        return true
                    }
                    else if(player == "green") {
                        greenWin = true
                        return true
                    }
                }
                else if(piece4.drawable.constantState == peg?.constantState && piece5.drawable.constantState == medium?.constantState && piece6.drawable.constantState == big?.constantState) {
                    if(player == "red") {
                        redWin = true
                        return true
                    }
                    else if(player == "blue") {
                        blueWin = true
                        return true
                    }
                    else if(player == "yellow") {
                        yellowWin = true
                        return true
                    }
                    else if(player == "green") {
                        greenWin = true
                        return true
                    }
                }
            }
        }

        // Checks diagonals (top right to bottom left)
        for(piece in pieces) {
            for(player in players) {
                val peg = ContextCompat.getDrawable(this, resources.getIdentifier("${player}peg", "drawable", packageName))
                val medium = ContextCompat.getDrawable(this, resources.getIdentifier("${player}medium", "drawable", packageName))
                val big = ContextCompat.getDrawable(this, resources.getIdentifier("${player}big", "drawable", packageName))

                val layout1 = findViewById<RelativeLayout>(resources.getIdentifier("grid02", "id", packageName))
                val layout2 = findViewById<RelativeLayout>(resources.getIdentifier("grid11", "id", packageName))
                val layout3 = findViewById<RelativeLayout>(resources.getIdentifier("grid20", "id", packageName))

                val piece1 = layout1.findViewById<ImageView>(resources.getIdentifier("circle02peg", "id", packageName))
                val piece2 = layout2.findViewById<ImageView>(resources.getIdentifier("circle11medium", "id", packageName))
                val piece3 = layout3.findViewById<ImageView>(resources.getIdentifier("circle20big", "id", packageName))

                val piece4 = layout3.findViewById<ImageView>(resources.getIdentifier("circle20peg", "id", packageName))
                val piece5 = layout2.findViewById<ImageView>(resources.getIdentifier("circle11medium", "id", packageName))
                val piece6 = layout1.findViewById<ImageView>(resources.getIdentifier("circle02big", "id", packageName))

                if(piece1.drawable.constantState == peg?.constantState && piece2.drawable.constantState == medium?.constantState && piece3.drawable.constantState == big?.constantState) {
                    if(player == "red") {
                        redWin = true
                        return true
                    }
                    else if(player == "blue") {
                        blueWin = true
                        return true
                    }
                    else if(player == "yellow") {
                        yellowWin = true
                        return true
                    }
                    else if(player == "green") {
                        greenWin = true
                        return true
                    }
                }
                else if(piece4.drawable.constantState == peg?.constantState && piece5.drawable.constantState == medium?.constantState && piece6.drawable.constantState == big?.constantState) {
                    if(player == "red") {
                        redWin = true
                        return true
                    }
                    else if(player == "blue") {
                        blueWin = true
                        return true
                    }
                    else if(player == "yellow") {
                        yellowWin = true
                        return true
                    }
                    else if(player == "green") {
                        greenWin = true
                        return true
                    }
                }
            }
        }

        return false
    }

    private fun draw() : Boolean {
        if(playCount == 27) {
            return true
        }
        //need to add another condition if board fills up
        //or 27 pieces have been played
        //6 red, 6 blue, 6 yellow, 5 green
        return false
    }


}

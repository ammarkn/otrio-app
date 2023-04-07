package com.example.otrio

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.*
import androidx.core.content.ContextCompat

class ThreePlayerBoardActivity : AppCompatActivity(), View.OnClickListener {

    private var pickedPiece = false

    // Used to check if player has won
    private var redWin = false
    private var blueWin = false
    private var yellowWin = false

    // Stores each player's number of wins
    private var redWins = 0
    private var blueWins = 0
    private var yellowWins = 0

    private lateinit var winData: SharedPreferences

    // ArrayLists to store each players pieces
    private var redPieces = ArrayList<ArrayList<Piece>>()
    private var bluePieces = ArrayList<ArrayList<Piece>>()
    private var yellowPieces = ArrayList<ArrayList<Piece>>()

    // Create pieces for each player (3 of each size)
    private var redPeg0 = Piece("red", "Peg")
    private var redMedium0 = Piece("Red", "Medium")
    private var redBig0 = Piece("red", "Big")

    private var redPeg1 = Piece("red", "Peg")
    private var redMedium1 = Piece("Red", "Medium")
    private var redBig1 = Piece("red", "Big")

    private var redPeg2 = Piece("Red", "Peg")
    private var redMedium2 = Piece("red", "Medium")
    private var redBig2 = Piece("Rred", "Big")

    private var bluePeg0 = Piece("blue", "Peg")
    private var blueMedium0 = Piece("Blue", "Medium")
    private var blueBig0 = Piece("blue", "Big")

    private var bluePeg1 = Piece("blue", "Peg")
    private var blueMedium1 = Piece("Blue", "Medium")
    private var blueBig1 = Piece("blue", "Big")

    private var bluePeg2 = Piece("blue", "Peg")
    private var blueMedium2 = Piece("Blue", "Medium")
    private var blueBig2 = Piece("blue", "Big")

    private var yellowPeg0 = Piece("yellow", "Peg")
    private var yellowMedium0 = Piece("yellow", "Medium")
    private var yellowBig0 = Piece("yellow", "Big")

    private var yellowPeg1 = Piece("yellow", "Peg")
    private var yellowMedium1 = Piece("yellow", "Medium")
    private var yellowBig1 = Piece("yellow", "Big")

    private var yellowPeg2 = Piece("yellow", "Peg")
    private var yellowMedium2 = Piece("yellow", "Medium")
    private var yellowBig2 = Piece("yellow", "Big")

    // Turn starts at 0
    private var turn = 0
    // Init piece to be null at first
    private var pieceType = Piece("null","null") //the piece current picked up by one of players

    // Create the players with their name, color, wins, and pieces
    private var p1 = Player("player1", "red", redWins, redPieces)
    private var p2 = Player("player2", "blue", blueWins, bluePieces)
    private var p3 = Player("player3", "yellow", yellowWins, yellowPieces)

    // Save all the players in one ArrayList
    private var playerList = ArrayList<Player>()

    // TextView variables that will track player info on the board
    private lateinit var turnplayer: TextView
    private lateinit var picked: TextView

    private var redPeg = ArrayList<Piece>()
    private var redMedium = ArrayList<Piece>()
    private var redBig = ArrayList<Piece>()

    private var bluePeg = ArrayList<Piece>()
    private var blueMedium = ArrayList<Piece>()
    private var blueBig = ArrayList<Piece>()

    private var yellowPeg = ArrayList<Piece>()
    private var yellowMedium = ArrayList<Piece>()
    private var yellowBig = ArrayList<Piece>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board)

        // Button to return user to homepage
        val homeButtonClick = findViewById<Button>(R.id.homeButton)
        homeButtonClick.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // Button to take user to instructions page
        val instructionsButtonClick = findViewById<Button>(R.id.instructionsButton)
        instructionsButtonClick.setOnClickListener {
            val intent = Intent(this, InstructionsActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        // Button to show the user the number of wins
        val openDialogButton = findViewById<Button>(R.id.open_dialog)
        openDialogButton.setOnClickListener {
            showCustomDialog()
        }

        // Button to reset the board
        val resetButtonClick = findViewById<Button>(R.id.resetButton)
        resetButtonClick.setOnClickListener {
            resetBoard()
        }

        // Add each individual pieces to arraylists of pieces of same size+color
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

        // Add each size piece arraylist to arraylists for each color
        redPieces.add(redPeg)
        redPieces.add(redMedium)
        redPieces.add(redBig)

        bluePieces.add(bluePeg)
        bluePieces.add(blueMedium)
        bluePieces.add(blueBig)

        yellowPieces.add(yellowPeg)
        yellowPieces.add(yellowMedium)
        yellowPieces.add(yellowBig)

        // Add players to playerList
        playerList.add(p1)
        playerList.add(p2)
        playerList.add(p3)

        //when users pick up a piece
        val buttonPeg = findViewById<RelativeLayout>(R.id.peg)
        buttonPeg.setOnClickListener(this)

        val buttonMedium = findViewById<RelativeLayout>(R.id.medium)
        buttonMedium.setOnClickListener(this)

        val buttonBig = findViewById<RelativeLayout>(R.id.big)
        buttonBig.setOnClickListener(this)

        // Set onClick listeners for each board pieces
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

        // Used to show the update of picked piece
        picked = findViewById(R.id.currentPiece)

        // Track the current player's turn (starts with player1)
        turnplayer = findViewById(R.id.playerTurn)
        turnplayer.text = "Player1"

        //initialization of piece pick button
        for (i in 0..2){
            for (pieceLeft in playerList[0].getPieces()[i]){

                val pieceSize = pieceLeft.getSize().lowercase()
                val pieceColor = playerList[0].getColor().lowercase()

                val componentId = resources.getIdentifier(pieceSize+playerList[0].getPieces()[i].indexOf(pieceLeft),"id",packageName)
                //get id of the button which is a relativelayout component
                val imageId = resources.getIdentifier(pieceColor+pieceSize,"drawable",packageName)
                //get the id of the image view for player1 pieces

                val pieceButton = findViewById<ImageView>(componentId)
                pieceButton.visibility = View.VISIBLE
                pieceButton.setImageResource(imageId)
                pieceButton.tag = pieceColor+pieceSize
            }
        }

        winData = getSharedPreferences("Wins", Context.MODE_PRIVATE)
        redWins = winData.getInt("redWins", 0)
        blueWins = winData.getInt("blueWins", 0)
        yellowWins = winData.getInt("yellowWins", 0)
    }

    private fun showCustomDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.win_dialog)
        val player1Wins = dialog.findViewById<TextView>(R.id.red_wins)
        val player2Wins = dialog.findViewById<TextView>(R.id.blue_wins)
        val player3Wins = dialog.findViewById<TextView>(R.id.yellow_wins)
        player3Wins.visibility = View.VISIBLE

        player1Wins.text = "Player 1: $redWins Wins"
        player2Wins.text = "Player 2: $blueWins Wins"
        player3Wins.text = "Player 3: $yellowWins Wins"
        dialog.show()
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

        if(sameSpaceWin() || samePieceWin() || ascendingDescendingWin()) {
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
                else if (yellowWin) {
                    println("Yellow win by same space win")
                    yellowWins++
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
                else if (yellowWin) {
                    println("Yellow win by same space win")
                    yellowWins++
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
                else if (yellowWin) {
                    println("Yellow win by ascending descending win")
                    yellowWins++
                    resetBoard()
                }
            }
        }
        else if(draw()) {
//                    Toast.makeText(this, "Draw", Toast.LENGTH_SHORT).show()
            println("Draw")
            resetBoard()
        }

    }

    private fun handleLayoutOnClick(vIdstart : String, Xpos : Int,Ypos : Int) {

        val newTag = resources.getIdentifier(pieceType.getColor()+pieceType.getSize().lowercase(),"drawable",packageName)
        //get id of image name of the piece base on the picked piece
        val vId = resources.getIdentifier(vIdstart+pieceType.getSize().lowercase(),"id",packageName)
        //get the id of the image view
        if(pickedPiece){ //check which button selected (Layout as a button)
            val imageView: ImageView = findViewById(vId)
            //check if peg already placed here
            val imageName = imageView.tag.toString()
            //get the name of the image in the imageview
            if(imageName.contains("blank")){
                imageView.setImageResource(newTag)
                imageView.tag = pieceType.getColor()+pieceType.getSize().lowercase()//re-set the name for check late
                var removedElement = Piece("null","null")
                if(pieceType.getColor()==("red")){
                    removedElement = when(pieceType.getSize()){
                        "Peg"->{
                            redPeg.removeAt(redPeg.size - 1)
                        }
                        "Medium"->{
                            redMedium.removeAt(redMedium.size - 1)
                        }
                        "Big"->{
                            redBig.removeAt(redBig.size - 1)
                        }
                        else->{
                            Piece("error","error")
                        }
                    }
                }
                else if(pieceType.getColor()==("blue")){
                    removedElement = when(pieceType.getSize()){
                        "Peg"->{
                            bluePeg.removeAt(bluePeg.size - 1)
                        }
                        "Medium"->{
                            blueMedium.removeAt(blueMedium.size - 1)
                        }
                        "Big"->{
                            blueBig.removeAt(blueBig.size - 1)
                        }
                        else->{
                            Piece("error","error")
                        }
                    }
                }
                else if(pieceType.getColor()==("yellow")){
                    removedElement = when(pieceType.getSize()){
                        "Peg"->{
                            yellowPeg.removeAt(yellowPeg.size - 1)
                        }
                        "Medium"->{
                            yellowMedium.removeAt(yellowMedium.size - 1)
                        }
                        "Big"->{
                            yellowBig.removeAt(yellowBig.size - 1)
                        }
                        else->{
                            Piece("error","error")
                        }
                    }
                }
                // Call placePiece function to finish placing the piece on the board
                placePiece(removedElement, Xpos, Ypos)
                // Set pickedPiece to false as piece has now been placed
                pickedPiece = false
            }
        }
        else {
            Toast.makeText(this, "Select a piece then select a location on the board", Toast.LENGTH_SHORT).show()
        }
    }

    private fun resetBoard() {
        val builder = AlertDialog.Builder(this)

        if(!blueWin && !redWin && !yellowWin) {
            builder.setTitle("Reset Board")
            builder.setMessage("Are you sure you want to reset the board? This action cannot be undone.")
            builder.setPositiveButton("Yes") { _, _ ->
                val wins = winData.edit()
                wins.putInt("redWins", redWins)
                wins.putInt("blueWins", blueWins)
                wins.putInt("yellowWins", yellowWins)
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
            else if (blueWin) {
                builder.setTitle("Winner: Player 2 (Blue)")
            }
            else {
                builder.setTitle("Winner: Player 3 (Yellow)")
            }
            builder.setMessage("Would you like to play again?")
            builder.setPositiveButton("Yes") { _, _ ->
                val wins = winData.edit()
                wins.putInt("redWins", redWins)
                wins.putInt("blueWins", blueWins)
                wins.putInt("yellowWins", yellowWins)
                wins.apply()

                recreate()
                Toast.makeText(this, "Game board was reset.", Toast.LENGTH_SHORT).show()
            }
            builder.setNegativeButton("No") { dialog, _ ->
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
        if(playerList[turn % 3].getPieces()[0].size > 0){
            // Set piece type if they have remaining piece to play
            pieceType = Piece(playerList[turn % 3].getColor(),"Peg")
            // Set text for piece to be displayed on screen
            picked.text = pieceType.getColor() + " " + pieceType.getSize()
            // Confirm a piece has been picked
            pickedPiece = true
        }
        else{
            Toast.makeText(this, "There are no Peg pieces left, please choose another piece", Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleButtonMediumClick() {
        if(playerList[turn % 3].getPieces()[1].size > 0){
            pieceType = Piece(playerList[turn % 3].getColor(),"Medium")
            picked.text = pieceType.getColor() + " "+ pieceType.getSize()
            pickedPiece = true
        }
        else{
            Toast.makeText(this, "There are no Medium pieces left, please choose another piece", Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleButtonBigClick() {
        if(playerList[turn % 3].getPieces()[2].size > 0){
            pieceType = Piece(playerList[turn % 3].getColor(),"Big")
            picked.text = pieceType.getColor()+ " " + pieceType.getSize()
            pickedPiece = true
        }
        else{
            Toast.makeText(this, "There are no Big pieces left, please choose another piece", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showNextPlayerInfo() {
        turn++
        val nextPlayer = playerList[turn % 3]
        turnplayer.text = nextPlayer.getName()

        for (i in 0..2){
            for (pieceLeft in nextPlayer.getPieces()[i]){// set all pieces which player still have to visible
                val pieceSize = pieceLeft.getSize().lowercase()
                val pieceColor = nextPlayer.getColor().lowercase()

                val componentId = resources.getIdentifier(pieceSize+nextPlayer.getPieces()[i].indexOf(pieceLeft),"id",packageName)
                //get id of the button which is a relativelayout component
                val imageId = resources.getIdentifier(pieceColor+pieceSize,"drawable",packageName)
                //get the id of the image view for image change

                val pieceButton = findViewById<ImageView>(componentId)
                pieceButton.visibility = View.VISIBLE
                pieceButton.setImageResource(imageId)
                pieceButton.tag = pieceColor+pieceSize
            }
        }

        picked.text = ""
    }

    private fun placePiece(piece: Piece, xPos: Int, yPos: Int) {
        println("Place piece")
        piece.setPiecePosition(xPos, yPos)

        resetPieceButton()
        showNextPlayerInfo()
    }

    private fun resetPieceButton(){
        for (i in 0..2){//reset all buttons to invisible to avid incorrect deployment
            val pegId = resources.getIdentifier("peg$i","id",packageName)
            val mediumId = resources.getIdentifier("medium$i","id",packageName)
            val bigId = resources.getIdentifier("big$i","id",packageName)
            //get id of the buttons which is a relativelayout component and reset them to invisible

            val piecePegButton = findViewById<ImageView>(pegId)
            val pieceMediumButton = findViewById<ImageView>(mediumId)
            val pieceBigButton = findViewById<ImageView>(bigId)

            piecePegButton.visibility = View.INVISIBLE
            pieceMediumButton.visibility = View.INVISIBLE
            pieceBigButton.visibility = View.INVISIBLE
        }
    }

    /*
     Method 1 of winning: https://otrio.com/pages/how-to-play
      */
    @SuppressLint("DiscouragedApi")
    private fun samePieceWin() : Boolean {
        val pieces = arrayOf("peg", "medium", "big")
        val players = arrayOf("red", "blue", "yellow")

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
                        else if (player == "yellow") {
                            yellowWin = true
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
                        else if (player == "yellow") {
                            yellowWin = true
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
                    else if (player == "yellow") {
                        yellowWin = true
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
                    else if (player == "yellow") {
                        yellowWin = true
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
                else if (peg.drawable.constantState == ContextCompat.getDrawable(this, R.drawable.yellowpeg)?.constantState) {
                    if(medium.drawable.constantState == ContextCompat.getDrawable(this, R.drawable.yellowmedium)?.constantState) {
                        if(big.drawable.constantState == ContextCompat.getDrawable(this, R.drawable.yellowbig)?.constantState) {
                            yellowWin = true
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
        val players = arrayOf("red", "blue", "yellow")

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
                        else if (player == "yellow") {
                            yellowWin = true
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
                        else if (player == "yellow") {
                            yellowWin = true
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
                        else if (player == "yellow") {
                            yellowWin = true
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
                        else if (player == "yellow") {
                            yellowWin = true
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
                    else if (player == "yellow") {
                        yellowWin = true
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
                    else if (player == "yellow") {
                        yellowWin = true
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
                    else if (player == "yellow") {
                        yellowWin = true
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
                    else if (player == "yellow") {
                        yellowWin = true
                        return true
                    }
                }
            }
        }
        return false
    }

    private fun draw() : Boolean {
        if(redPieces.isEmpty() && bluePieces.isEmpty() && yellowPieces.isEmpty()) {
            return true
        }
        return false
    }
}
package com.example.otrio

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import java.lang.System.arraycopy

class ThreePlayerBoardActivity : AppCompatActivity(), View.OnClickListener {

    private var rounds = 0;

    private var pickedPiece = false

    private var redWin = false
    private var blueWin = false
    private var yellowWin = false

    var redWins = 0
    var blueWins = 0
    var yellowWins = 0

    private val buttons = Array(3) { arrayOfNulls<Button>(3) }

    var redPieces = ArrayList<ArrayList<Piece>>()
    var bluePieces = ArrayList<ArrayList<Piece>>()
    var yellowPieces = ArrayList<ArrayList<Piece>>()


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

    var yellowPeg0 = Piece("yellow", "Peg")
    var yellowMedium0 = Piece("yellow", "Medium")
    var yellowBig0 = Piece("yellow", "Big")

    var yellowPeg1 = Piece("yellow", "Peg")
    var yellowMedium1 = Piece("yellow", "Medium")
    var yellowBig1 = Piece("yellow", "Big")

    var yellowPeg2 = Piece("yellow", "Peg")
    var yellowMedium2 = Piece("yellow", "Medium")
    var yellowBig2 = Piece("yellow", "Big")

    var turn = 0 //the current turn number
    var pieceType = Piece("null","null") //the piece current picked up by one of players

    var p1 = Player("player1", "red", redWins, redPieces)
    var p2 = Player("player2", "blue", blueWins, bluePieces)
    var p3 = Player("player3", "yellow", yellowWins, yellowPieces)

    var playerList = ArrayList<Player>() //save all play in one list

    private lateinit var turnplayer: TextView
    private lateinit var pegnumber: TextView
    private lateinit var mediumnumber: TextView
    private lateinit var bignumber: TextView
    private lateinit var picked: TextView

    var redPeg = ArrayList<Piece>()
    var redMedium = ArrayList<Piece>()
    var redBig = ArrayList<Piece>()

    var bluePeg = ArrayList<Piece>()
    var blueMedium = ArrayList<Piece>()
    var blueBig = ArrayList<Piece>()

    var yellowPeg = ArrayList<Piece>()
    var yellowMedium = ArrayList<Piece>()
    var yellowBig = ArrayList<Piece>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.three_player_activity_board)

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

        val openDialogButton = findViewById<Button>(R.id.open_dialog)
        openDialogButton.setOnClickListener {
            showCustomDialog()
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

        yellowPeg.add(yellowPeg0)
        yellowPeg.add(yellowPeg1)
        yellowPeg.add(yellowPeg2)

        yellowMedium.add(yellowMedium0)
        yellowMedium.add(yellowMedium1)
        yellowMedium.add(yellowMedium2)

        yellowBig.add(yellowBig0)
        yellowBig.add(yellowBig1)
        yellowBig.add(yellowBig2)

        redPieces.add(redPeg)
        redPieces.add(redMedium)
        redPieces.add(redBig)

        bluePieces.add(bluePeg)
        bluePieces.add(blueMedium)
        bluePieces.add(blueBig)

        yellowPieces.add(yellowPeg)
        yellowPieces.add(yellowMedium)
        yellowPieces.add(yellowBig)

        //set player list
        playerList.add(p1)
        playerList.add(p2)
        playerList.add(p3)

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


/*        for (i in 0..2) {
            for (j in 0..2) {
                val buttonID = "grid" + i + j
                val resID = resources.getIdentifier(buttonID, "id", packageName)
                buttons[i][j] = findViewById(resID)
                buttons[i][j]!!.setOnClickListener(this)
            }
        }*/
    }

    private fun showCustomDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.win_dialog)
        val player1Wins = dialog.findViewById<TextView>(R.id.player1_wins)
        val player2Wins = dialog.findViewById<TextView>(R.id.player2_wins)
        val player3Wins = dialog.findViewById<TextView>(R.id.player3_wins)
        player3Wins.visibility = View.VISIBLE

        player1Wins.text = "Player 1 Wins: 1"
        player2Wins.text = "Player 2 Wins: 2"
        player3Wins.text = "Player 3 Wins: 1"
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
    }

    private fun handleLayoutOnClick(vIdstart : String, Xpos : Int,Ypos : Int) {

        val newTag = resources.getIdentifier(pieceType.getColor()+pieceType.getSize().lowercase(),"drawable",packageName)
        //get id of image name of the piece base on the picked piece
        val vId = resources.getIdentifier(vIdstart+pieceType.getSize(),"id",packageName)
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
                else if(pieceType.getColor()==("yellow")){
                    when(pieceType.getSize()){
                        "Peg"->{
                            removedElement = yellowPeg.removeAt(yellowPeg.size - 1)
                        }
                        "Medium"->{
                            removedElement = yellowMedium.removeAt(yellowMedium.size - 1)
                        }
                        "Big"->{
                            removedElement = yellowBig.removeAt(yellowBig.size - 1)
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

    private fun handleButtonPegClick() {
        if(playerList[turn%3].getPieces()[0].size > 0){
            pieceType = Piece(playerList[turn%3].getColor(),"Peg")
            picked.text = pieceType.getColor() + " " + pieceType.getSize()
            pickedPiece = true
        }
    }

    private fun handleButtonMediumClick() {
        if(playerList[turn%3].getPieces()[1].size > 0){
            pieceType = Piece(playerList[turn%3].getColor(),"Medium")
            picked.text = pieceType.getColor()+" "+pieceType.getSize()
            pickedPiece = true
        }
    }

    private fun handleButtonBigClick() {
        if(playerList[turn%3].getPieces()[2].size > 0){
            pieceType = Piece(playerList[turn%3].getColor(),"Big")
            picked.text = pieceType.getColor()+" "+pieceType.getSize()
            pickedPiece = true
        }
    }

    private fun showNextPlayerInfo() {
        turn ++
        val nextPlayer = playerList[turn%3]

        turnplayer.text = nextPlayer.getName()
        pegnumber.text = nextPlayer.getPieces()[0].size.toString()
        mediumnumber.text = nextPlayer.getPieces()[1].size.toString()
        bignumber.text = nextPlayer.getPieces()[2].size.toString()
        picked.text = ""
    }

    private fun placePiece(piece: Piece, xPos: Int, yPos: Int) {
        println("Place piece")
        piece.setPiecePosition(xPos, yPos);

        showNextPlayerInfo()
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

        //if we're tracking wins, we'll need to reset the game??
        if(redWin) { redWins++ } else if(blueWin) { blueWins++ }
    }
}
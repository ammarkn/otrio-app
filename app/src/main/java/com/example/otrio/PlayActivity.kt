package com.example.otrio

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class PlayActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)

        val twoPlayerButton = findViewById<Button>(R.id.go_to_two_player)
        val threePlayerButton = findViewById<Button>(R.id.go_to_three_player)

        twoPlayerButton.setOnClickListener {
            val intent = Intent(this, TwoPlayerBoardActivity::class.java)
            startActivity(intent)
        }
        threePlayerButton.setOnClickListener {
            val intent = Intent(this, ThreePlayerBoardActivity::class.java)
            startActivity(intent)
        }
    }
}
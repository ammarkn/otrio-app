package com.example.otrio

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class PlayActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        // Set toolbar as action bar
        setSupportActionBar(toolbar)

        // Enable the back button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val singlePlayerButton = findViewById<Button>(R.id.go_to_single_player)
        val twoPlayerButton = findViewById<Button>(R.id.go_to_two_player)
        val threePlayerButton = findViewById<Button>(R.id.go_to_three_player)
        val fourPlayerButton = findViewById<Button>(R.id.go_to_four_player)

        singlePlayerButton.setOnClickListener {
            val intent = Intent(this, SinglePlayerBoardActivity::class.java)
            startActivity(intent)
        }
        twoPlayerButton.setOnClickListener {
            val intent = Intent(this, TwoPlayerBoardActivity::class.java)
            startActivity(intent)
        }
        threePlayerButton.setOnClickListener {
            val intent = Intent(this, ThreePlayerBoardActivity::class.java)
            startActivity(intent)
        }
        fourPlayerButton.setOnClickListener {
            val intent = Intent(this, FourPlayerBoardActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        val sharedPreferences = getSharedPreferences("Music", Context.MODE_PRIVATE)
        val shouldPlay = sharedPreferences.getBoolean("musicSwitchState", true)
        if (shouldPlay && !MediaPlayerManager.isPlaying) {
            MediaPlayerManager.createMediaPlayer(this)
        }
    }

    override fun onStop() {
        super.onStop()
        if (!MainActivity.isAppInForeground(this)) {
            MediaPlayerManager.stopMediaPlayer()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
/*
File contributors: Katie Arsenault, Deniz Kaptan
 */

package com.example.otrio.pages

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.otrio.media.MediaPlayerManager
import com.example.otrio.R

class InstructionsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_instructions)

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        // Set toolbar as action bar
        setSupportActionBar(toolbar)

        // Enable the back button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    // Upon resuming the activity, check if the music should be played
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
        // Stop the media player when the activity is no longer visible
        if (!MainActivity.isAppInForeground(this)) {
            MediaPlayerManager.stopMediaPlayer()
        }
    }

    // User can go back on back button click
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
/*
File contributors: Katie Arsenault
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

        //user should be able to return to previous page
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        // Set toolbar as action bar
        setSupportActionBar(toolbar)
        // Enable the back button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    //if music is turned back on, it should play for the user on the current page
    override fun onResume() {
        super.onResume()
        val sharedPreferences = getSharedPreferences("Music", Context.MODE_PRIVATE)
        val shouldPlay = sharedPreferences.getBoolean("musicSwitchState", true)
        if (shouldPlay && !MediaPlayerManager.isPlaying) {
            MediaPlayerManager.createMediaPlayer(this)
        }
    }

    //if activity is no longer visible, turn off music
    override fun onStop() {
        super.onStop()
        if (!MainActivity.isAppInForeground(this)) {
            MediaPlayerManager.stopMediaPlayer()
        }
    }

    //user should be able to return to previous page
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
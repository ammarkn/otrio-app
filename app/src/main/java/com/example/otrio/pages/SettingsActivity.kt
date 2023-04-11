/*
File contributors: Katie Arsenault, Deniz Kaptan, Ammar Khan, Yijiu Zhao
 */

package com.example.otrio.pages

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import com.example.otrio.media.MediaPlayerManager
import com.example.otrio.R


class SettingsActivity: AppCompatActivity() {
    private lateinit var musicSwitch: SwitchCompat
    private lateinit var sharedPreferences: SharedPreferences

    private lateinit var modeSwitch: SwitchCompat

    private lateinit var resetWinsButton : Button

    private var isDarkMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        //user should be able to return to previous page
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        // Set toolbar as action bar
        setSupportActionBar(toolbar)
        // Enable the back button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)


        musicSwitch = findViewById(R.id.musicSwitch)
        // Get the SharedPreferences instance
        sharedPreferences = getSharedPreferences("Music", Context.MODE_PRIVATE)

        // Set the initial state of the SwitchCompat based on the last saved state
        val switchState = sharedPreferences.getBoolean("musicSwitchState", true)

        musicSwitch.isChecked = switchState

        // Set a listener for the SwitchCompat to toggle the music on/off
        musicSwitch.setOnCheckedChangeListener { _, isChecked ->
            // Turn on the music if it's not already playing
            if (isChecked && !MediaPlayerManager.isPlaying) {
                MediaPlayerManager.createMediaPlayer(this)
            } else {
                // Turn off the music
                MediaPlayerManager.stopMediaPlayer()
            }
            // Save the state of the SwitchCompat
            sharedPreferences.edit().putBoolean("musicSwitchState", isChecked).apply()
        }

        //the switch feature for dark/light mode
        //the associate knowledge learned from URL: https://www.geeksforgeeks.org/how-to-implement-dark-night-mode-in-android-app/
        modeSwitch = findViewById(R.id.mode_switch)

        //Dark model Author: Yijiu
        //get the dark mode setting changed by user
        isDarkMode = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES
        //change the switch turn on/off base on the user setting
        modeSwitch.isChecked = isDarkMode

        //get the dark setting from user and save the changed
        modeSwitch.setOnCheckedChangeListener {_, isChecked ->
            if(isChecked) { //use the color style in values-night\colors.xml when turn on the dark mode
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else { // use the color style in values\colors.xml when turn off the dark mode
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        resetWinsButton = findViewById(R.id.resetWins)
        // Set a listener for the Reset Wins Button to reset all win data
        resetWinsButton.setOnClickListener {
            sharedPreferences = getSharedPreferences("Wins", Context.MODE_PRIVATE)
            val wins = sharedPreferences.edit() //edit the local data stored in wins
            wins.putInt("redWins", 0)
            wins.putInt("blueWins", 0)
            wins.putInt("yellowWins", 0)
            wins.putInt("greenWins", 0)
            wins.apply()

            Toast.makeText(this, "Player wins reset.", Toast.LENGTH_SHORT).show()
        }
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

    //if activity is no longer visible, turn off music
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
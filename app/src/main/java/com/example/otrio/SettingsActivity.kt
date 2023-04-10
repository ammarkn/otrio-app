package com.example.otrio

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat


class SettingsActivity: AppCompatActivity() {
    private lateinit var musicSwitch: SwitchCompat
    private lateinit var sharedPreferences: SharedPreferences

    private lateinit var modeSwitch: SwitchCompat

    private lateinit var resetWinsButton : Button

    private var isDarkMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

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
        resetWinsButton.setOnClickListener {
            sharedPreferences = getSharedPreferences("Wins", Context.MODE_PRIVATE)
            val wins = sharedPreferences.edit()
            wins.putInt("redWins", 0)
            wins.putInt("blueWins", 0)
            wins.putInt("yellowWins", 0)
            wins.putInt("greenWins", 0)
            wins.apply()

            Toast.makeText(this, "Wins.", Toast.LENGTH_SHORT).show()
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
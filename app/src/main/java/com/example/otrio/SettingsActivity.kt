package com.example.otrio

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat

class SettingsActivity: AppCompatActivity() {
    private lateinit var musicSwitch: SwitchCompat
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        musicSwitch = findViewById(R.id.musicSwitch)

        // Get the SharedPreferences instance
        sharedPreferences = getSharedPreferences("Music", Context.MODE_PRIVATE)

        // Set the initial state of the SwitchCompat based on the last saved state
        val switchState = sharedPreferences.getBoolean("musicSwitchState", true)

        musicSwitch.isChecked = switchState

        // Set a listener for the SwitchCompat to toggle the music on/off
        musicSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // Turn on the music
                MediaPlayerManager.createMediaPlayer(this)
            } else {
                // Turn off the music
                MediaPlayerManager.stopMediaPlayer()
            }
            // Save the state of the SwitchCompat
            sharedPreferences.edit().putBoolean("musicSwitchState", isChecked).apply()
        }
    }
}
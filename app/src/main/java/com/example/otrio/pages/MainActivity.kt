/*
File contributors: Katie Arsenault, Deniz Kaptan
 */

package com.example.otrio.pages

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import com.example.otrio.*
import com.example.otrio.databinding.ActivityMainBinding
import com.example.otrio.media.MediaPlayerManager
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var greetingText : TextView

    private var greetings = arrayOf(
        "Welcome! It's always a pleasure to see you.",
        "Hope you're doing well and ready to conquer the day!",
        "Rise and shine, it's a new day full of possibilities.",
        "May this day be as wonderful as you are.",
        "Sending you positive vibes for a productive day!",
        "You got this, go out there and crush it!",
        "Let's make today a day to remember!",
        "Hope you're ready for a day full of new opportunities!",
        "Hey there, it's nice to see you again!",
        "Bonjour! May your day be filled with positivity. "
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        greetingText = findViewById(R.id.greeting_text)
        greetingText.text = randomGreeting()

        val sharedPreferences = getSharedPreferences("Music", Context.MODE_PRIVATE)
        val shouldPlay = sharedPreferences.getBoolean("musicSwitchState", true)
        if (shouldPlay && !MediaPlayerManager.isPlaying) {
            MediaPlayerManager.createMediaPlayer(this)
        }
        //variable for button to take user to instructions
        val instButtonClick = findViewById<Button>(R.id.go_to_instructions)

        //event listener to change page when button is clicked
        instButtonClick.setOnClickListener {
            val intent = Intent(this, InstructionsActivity::class.java)
            startActivity(intent)
        }

        //variable for button to take user to instructions
        val gameButtonClick = findViewById<Button>(R.id.go_to_play)

        //event listener to change page when button is clicked
        gameButtonClick.setOnClickListener {
            val intent = Intent(this, PlayActivity::class.java)
            startActivity(intent)
        }

        val settingsButton = findViewById<Button>(R.id.go_to_settings)

        settingsButton.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
    }

    private fun randomGreeting() : String {
        return greetings[Random.nextInt(greetings.size)]
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
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

    override fun onStop() {
        super.onStop()
        // Stop the media player when the activity is no longer visible
        if (!isAppInForeground(this)) {
            MediaPlayerManager.stopMediaPlayer()
        }
    }
    // Logic inspired by: https://stackoverflow.com/questions/43378841/check-if-app-is-running-in-foreground-or-background-with-sync-adapter
    // Use a a companion object to make the method "static"
    companion object {
       // Used to check if app is in foreground to disable music
        fun isAppInForeground(context: Context): Boolean {
            val activityManager =
                context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            val runningAppProcesses = activityManager.runningAppProcesses ?: return false
            return runningAppProcesses.any { it.processName == context.packageName && it.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND }
        }
    }
}
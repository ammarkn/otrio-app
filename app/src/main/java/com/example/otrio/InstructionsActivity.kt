package com.example.otrio

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class InstructionsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_instructions)

        //variable for button to take user to instructions
        val gameButtonClick = findViewById<Button>(R.id.go_to_gameplay)

        //event listener to change page when button is clicked
        gameButtonClick.setOnClickListener {
            val intent = Intent(this, BoardActivity::class.java)
            startActivity(intent)
        }
    }
}
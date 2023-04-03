package com.example.otrio

import android.content.Context
import android.media.MediaPlayer
// MediaPlayerManager uses the Singleton pattern to instantiate one global MediaPlayerManager
object MediaPlayerManager {
    private var mediaPlayer: MediaPlayer? = null

    fun createMediaPlayer(context: Context) {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(context, R.raw.background_music)
            mediaPlayer?.isLooping = true
            mediaPlayer?.start()
        }
    }

    fun stopMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer?.pause()
            mediaPlayer?.release()
            mediaPlayer = null
        }
    }
}
/*
File contributors: Deniz Kaptan
 */

package com.example.otrio.media

import android.content.Context
import android.media.MediaPlayer
import com.example.otrio.R

// MediaPlayerManager uses the Singleton pattern to instantiate one global MediaPlayerManager
object MediaPlayerManager {
    private var mediaPlayer: MediaPlayer? = null
    var isPlaying = false

    fun createMediaPlayer(context: Context) {
        if (mediaPlayer == null || !isPlaying) {
            mediaPlayer = MediaPlayer.create(context, R.raw.background_music)
            mediaPlayer?.isLooping = true
            mediaPlayer?.start()
            isPlaying = true
        }
    }
    fun stopMediaPlayer() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
        isPlaying = false
    }
}

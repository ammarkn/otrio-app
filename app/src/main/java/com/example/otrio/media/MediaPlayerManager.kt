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

    //mediaplayer should be created for user to listen to music
    fun createMediaPlayer(context: Context) {
        if (mediaPlayer == null || !isPlaying) {
            mediaPlayer = MediaPlayer.create(context, R.raw.background_music)
            mediaPlayer?.isLooping = true
            mediaPlayer?.start()
            isPlaying = true
        }
    }

    //mediaplayer should be stopped when music no longer needs to play
    fun stopMediaPlayer() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
        isPlaying = false
    }
}

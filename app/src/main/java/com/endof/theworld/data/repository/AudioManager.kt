package com.endof.theworld.data.repository

import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import androidx.annotation.RawRes
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SoundManager @Inject constructor(
    @ApplicationContext
    private val context: Context
) {

    var mediaPlayer: MediaPlayer? = null

    fun playSound(
        @RawRes
        soundId: Int
    ) {
        try {
            if (mediaPlayer != null) {
                mediaPlayer?.stop()
            }
            mediaPlayer = MediaPlayer.create(context, soundId)
            mediaPlayer?.start()
        } catch (e: Exception) {
            Log.e("GameScreen", "can't play sound", e)
        }

    }
}
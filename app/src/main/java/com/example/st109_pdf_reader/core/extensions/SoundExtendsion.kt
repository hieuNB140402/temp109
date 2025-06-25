package com.example.st109_pdf_reader.core.extensions

import android.media.MediaPlayer
import android.util.Log

fun safeSetVolume(mediaPlayer: MediaPlayer?, volume: Float) {
    if (mediaPlayer != null && !mediaPlayer.isReleased()) {
        mediaPlayer.setVolume(volume, volume)
        Log.d("nbhieu","setVolume: $volume")
    }
}

fun releaseMediaPlayer(mediaPlayer: MediaPlayer?) {
    if (mediaPlayer != null && !mediaPlayer.isReleased()) {
        mediaPlayer.stop()
        mediaPlayer.release()
    }
}

fun MediaPlayer?.isReleased(): Boolean {
    return try {
        this?.isPlaying
        false
    } catch (e: IllegalStateException) {
        true
    }
}
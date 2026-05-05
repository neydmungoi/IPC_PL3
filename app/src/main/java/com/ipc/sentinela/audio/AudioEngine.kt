package com.ipc.sentinela.audio

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.annotation.RawRes
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer

/**
 * Motor de Áudio para o VoxNoti.
 * Gere Spearcons (SoundPool) e Earcons/Podcasts (ExoPlayer).
 */
class AudioEngine(private val context: Context) {

    private val exoPlayer: ExoPlayer = ExoPlayer.Builder(context).build()
    
    private val soundPool: SoundPool = SoundPool.Builder()
        .setMaxStreams(5)
        .setAudioAttributes(
            AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ASSISTANCE_ACCESSIBILITY)
                .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                .build()
        )
        .build()

    private val soundMap = mutableMapOf<String, Int>()

    init {
        // Exemplo: carregar sons de res/raw
        // soundMap["seca_severa"] = soundPool.load(context, R.raw.seca_severa, 1)
    }

    /**
     * Spearcons: Alertas de voz curtos e rápidos.
     */
    fun playSpearcon(soundKey: String, volume: Float = 1.0f) {
        soundMap[soundKey]?.let { soundId ->
            soundPool.play(soundId, volume, volume, 1, 0, 1f)
        }
    }

    /**
     * Earcons: Sons de ambiente em loop.
     */
    fun playEarconLoop(@RawRes resId: Int, volume: Float = 0.3f) {
        val uri = "android.resource://${context.packageName}/$resId"
        val mediaItem = MediaItem.fromUri(uri)
        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.repeatMode = Player.REPEAT_MODE_ALL
        exoPlayer.volume = volume
        exoPlayer.prepare()
        exoPlayer.play()
    }

    /**
     * Podcasts: Reprodução de ficheiros longos.
     */
    fun playPodcast(uriString: String) {
        val mediaItem = MediaItem.fromUri(uriString)
        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.repeatMode = Player.REPEAT_MODE_OFF
        exoPlayer.prepare()
        exoPlayer.play()
    }

    fun pauseAudio() {
        exoPlayer.pause()
    }

    fun stopAllAudio() {
        exoPlayer.stop()
        soundPool.autoPause()
    }

    /**
     * Vibração Hática Extra para ambientes ruidosos.
     */
    fun triggerCriticalHaptic() {
        val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val effect = VibrationEffect.createWaveform(longArrayOf(0, 500, 200, 500), -1)
            vibrator.vibrate(effect)
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(1000)
        }
    }

    /**
     * Placeholder para Comandos de Voz.
     */
    fun startVoiceCommandListener() {
        // Implementar SpeechRecognizer aqui no futuro
    }

    fun release() {
        exoPlayer.release()
        soundPool.release()
    }
}

package com.deenlearn.app.services

import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import android.os.Handler
import android.os.Looper

/**
 * Service for playing Quran audio using ExoPlayer
 * Provides verse-by-verse and continuous surah playback
 * with progress tracking and playback controls
 */
class QuranAudioService private constructor(private val context: Context) {
    
    companion object {
        @Volatile
        private var instance: QuranAudioService? = null
        
        fun getInstance(context: Context): QuranAudioService {
            return instance ?: synchronized(this) {
                instance ?: QuranAudioService(context.applicationContext).also { instance = it }
            }
        }
    }
    
    // ExoPlayer instance
    private var exoPlayer: ExoPlayer? = null
    
    // Playback state
    private val _playbackState = MutableStateFlow(PlaybackState())
    val playbackState: StateFlow<PlaybackState> = _playbackState.asStateFlow()
    
    // Current playing info
    private val _currentPlaying = MutableStateFlow<CurrentPlaying?>(null)
    val currentPlaying: StateFlow<CurrentPlaying?> = _currentPlaying.asStateFlow()
    
    // Progress tracking
    private val _progress = MutableStateFlow(0f)
    val progress: StateFlow<Float> = _progress.asStateFlow()
    
    private val _currentPosition = MutableStateFlow(0L)
    val currentPosition: StateFlow<Long> = _currentPosition.asStateFlow()
    
    private val _duration = MutableStateFlow(0L)
    val duration: StateFlow<Long> = _duration.asStateFlow()
    
    // Progress update handler
    private val progressHandler = Handler(Looper.getMainLooper())
    private val progressRunnable = object : Runnable {
        override fun run() {
            exoPlayer?.let { player ->
                if (player.isPlaying) {
                    _currentPosition.value = player.currentPosition
                    _duration.value = player.duration.coerceAtLeast(0L)
                    
                    val progress = if (player.duration > 0) {
                        (player.currentPosition.toFloat() / player.duration.toFloat())
                    } else {
                        0f
                    }
                    _progress.value = progress.coerceIn(0f, 1f)
                }
            }
            progressHandler.postDelayed(this, 100) // Update every 100ms
        }
    }
    
    init {
        initializePlayer()
    }
    
    private fun initializePlayer() {
        exoPlayer = ExoPlayer.Builder(context).build().apply {
            addListener(object : Player.Listener {
                override fun onPlaybackStateChanged(playbackState: Int) {
                    when (playbackState) {
                        Player.STATE_READY -> {
                            _playbackState.value = _playbackState.value.copy(
                                isLoading = false
                            )
                        }
                        Player.STATE_BUFFERING -> {
                            _playbackState.value = _playbackState.value.copy(
                                isLoading = true
                            )
                        }
                        Player.STATE_ENDED -> {
                            handlePlaybackEnded()
                        }
                        Player.STATE_IDLE -> {
                            _playbackState.value = _playbackState.value.copy(
                                isLoading = false
                            )
                        }
                    }
                }
                
                override fun onIsPlayingChanged(isPlaying: Boolean) {
                    _playbackState.value = _playbackState.value.copy(
                        isPlaying = isPlaying,
                        isPaused = !isPlaying && exoPlayer?.playbackState != Player.STATE_IDLE
                    )
                    
                    if (isPlaying) {
                        progressHandler.post(progressRunnable)
                    } else {
                        progressHandler.removeCallbacks(progressRunnable)
                    }
                }
            })
        }
    }
    
    /**
     * Play a single verse
     */
    fun playVerse(audioUrl: String, surahId: Int, verseNumber: Int, surahName: String) {
        try {
            exoPlayer?.let { player ->
                val mediaItem = MediaItem.fromUri(audioUrl)
                player.setMediaItem(mediaItem)
                player.prepare()
                player.play()
                
                _currentPlaying.value = CurrentPlaying(
                    surahId = surahId,
                    verseNumber = verseNumber,
                    surahName = surahName,
                    audioUrl = audioUrl
                )
                
                _playbackState.value = _playbackState.value.copy(
                    isPlaying = true,
                    isPaused = false,
                    repeatMode = RepeatMode.ONCE
                )
            }
        } catch (e: Exception) {
            _playbackState.value = _playbackState.value.copy(
                error = "Failed to play audio: ${e.message}"
            )
        }
    }
    
    /**
     * Play continuous surah (multiple verses)
     */
    fun playSurah(audioUrls: List<VerseAudio>, surahId: Int, surahName: String, startFrom: Int = 0) {
        try {
            exoPlayer?.let { player ->
                player.clearMediaItems()
                
                val mediaItems = audioUrls.drop(startFrom).map { verseAudio ->
                    MediaItem.fromUri(verseAudio.audioUrl)
                }
                
                player.setMediaItems(mediaItems)
                player.prepare()
                player.play()
                
                _currentPlaying.value = CurrentPlaying(
                    surahId = surahId,
                    verseNumber = audioUrls.getOrNull(startFrom)?.verseNumber ?: 1,
                    surahName = surahName,
                    audioUrl = audioUrls.getOrNull(startFrom)?.audioUrl ?: "",
                    totalVerses = audioUrls.size,
                    playlistMode = true
                )
                
                _playbackState.value = _playbackState.value.copy(
                    isPlaying = true,
                    isPaused = false,
                    repeatMode = RepeatMode.SURAH
                )
            }
        } catch (e: Exception) {
            _playbackState.value = _playbackState.value.copy(
                error = "Failed to play surah: ${e.message}"
            )
        }
    }
    
    /**
     * Pause playback
     */
    fun pause() {
        exoPlayer?.pause()
        _playbackState.value = _playbackState.value.copy(
            isPlaying = false,
            isPaused = true
        )
    }
    
    /**
     * Resume playback
     */
    fun resume() {
        exoPlayer?.play()
        _playbackState.value = _playbackState.value.copy(
            isPlaying = true,
            isPaused = false
        )
    }
    
    /**
     * Stop playback and clear current playing
     */
    fun stop() {
        exoPlayer?.stop()
        exoPlayer?.clearMediaItems()
        
        _playbackState.value = PlaybackState()
        _currentPlaying.value = null
        _progress.value = 0f
        _currentPosition.value = 0L
        _duration.value = 0L
        
        progressHandler.removeCallbacks(progressRunnable)
    }
    
    /**
     * Seek to position (in milliseconds)
     */
    fun seekTo(positionMs: Long) {
        exoPlayer?.seekTo(positionMs)
    }
    
    /**
     * Set playback speed
     */
    fun setSpeed(speed: Float) {
        exoPlayer?.setPlaybackSpeed(speed)
        _playbackState.value = _playbackState.value.copy(speed = speed)
    }
    
    /**
     * Set repeat mode
     */
    fun setRepeatMode(mode: RepeatMode) {
        val exoRepeatMode = when (mode) {
            RepeatMode.ONCE -> Player.REPEAT_MODE_OFF
            RepeatMode.VERSE -> Player.REPEAT_MODE_ONE
            RepeatMode.SURAH -> Player.REPEAT_MODE_ALL
        }
        exoPlayer?.repeatMode = exoRepeatMode
        _playbackState.value = _playbackState.value.copy(repeatMode = mode)
    }
    
    /**
     * Go to next verse (if in playlist mode)
     */
    fun next() {
        exoPlayer?.let { player ->
            if (player.hasNextMediaItem()) {
                player.seekToNext()
                updateCurrentVerseInfo()
            }
        }
    }
    
    /**
     * Go to previous verse (if in playlist mode)
     */
    fun previous() {
        exoPlayer?.let { player ->
            if (player.hasPreviousMediaItem()) {
                player.seekToPrevious()
                updateCurrentVerseInfo()
            }
        }
    }
    
    /**
     * Check if player is currently playing
     */
    fun isPlaying(): Boolean = exoPlayer?.isPlaying ?: false
    
    /**
     * Check if player is paused
     */
    fun isPaused(): Boolean = _playbackState.value.isPaused
    
    private fun handlePlaybackEnded() {
        when (_playbackState.value.repeatMode) {
            RepeatMode.ONCE -> {
                stop()
            }
            RepeatMode.VERSE -> {
                exoPlayer?.seekTo(0)
                exoPlayer?.play()
            }
            RepeatMode.SURAH -> {
                // ExoPlayer handles this automatically with REPEAT_MODE_ALL
            }
        }
    }
    
    private fun updateCurrentVerseInfo() {
        exoPlayer?.let { player ->
            _currentPlaying.value?.let { current ->
                if (current.playlistMode) {
                    _currentPlaying.value = current.copy(
                        verseNumber = current.verseNumber + 1
                    )
                }
            }
        }
    }
    
    /**
     * Release resources
     */
    fun release() {
        progressHandler.removeCallbacks(progressRunnable)
        exoPlayer?.release()
        exoPlayer = null
        instance = null
    }
}

// MARK: - Data Classes

/**
 * Playback state information
 */
data class PlaybackState(
    val isPlaying: Boolean = false,
    val isPaused: Boolean = false,
    val isLoading: Boolean = false,
    val speed: Float = 1.0f,
    val repeatMode: RepeatMode = RepeatMode.ONCE,
    val error: String? = null
)

/**
 * Currently playing information
 */
data class CurrentPlaying(
    val surahId: Int,
    val verseNumber: Int,
    val surahName: String,
    val audioUrl: String,
    val totalVerses: Int = 1,
    val playlistMode: Boolean = false
)

/**
 * Verse audio information for playlist
 */
data class VerseAudio(
    val verseNumber: Int,
    val audioUrl: String
)

/**
 * Repeat mode options
 */
enum class RepeatMode {
    ONCE,    // Play once and stop
    VERSE,   // Repeat current verse
    SURAH    // Repeat entire surah
}

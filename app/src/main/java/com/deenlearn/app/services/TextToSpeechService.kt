package com.deenlearn.app.services

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.*

/**
 * Text-to-Speech service for Arabic and English text
 * Supports both TTS synthesis and streaming audio from URLs (Quran recitation)
 */
class TextToSpeechService private constructor(private val context: Context) {
    
    private var tts: TextToSpeech? = null
    private var mediaPlayer: MediaPlayer? = null
    
    private val _isSpeaking = MutableStateFlow(false)
    val isSpeaking: StateFlow<Boolean> = _isSpeaking.asStateFlow()
    
    private val _currentText = MutableStateFlow<String?>(null)
    val currentText: StateFlow<String?> = _currentText.asStateFlow()
    
    private val _isPlayingAudio = MutableStateFlow(false)
    val isPlayingAudio: StateFlow<Boolean> = _isPlayingAudio.asStateFlow()
    
    private val _isInitialized = MutableStateFlow(false)
    val isInitialized: StateFlow<Boolean> = _isInitialized.asStateFlow()
    
    companion object {
        @Volatile
        private var instance: TextToSpeechService? = null
        
        fun getInstance(context: Context): TextToSpeechService {
            return instance ?: synchronized(this) {
                instance ?: TextToSpeechService(context.applicationContext).also { instance = it }
            }
        }
    }
    
    init {
        initializeTTS()
    }
    
    private fun initializeTTS() {
        tts = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                _isInitialized.value = true
                setupTTS()
            }
        }
    }
    
    private fun setupTTS() {
        tts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
            override fun onStart(utteranceId: String?) {
                _isSpeaking.value = true
            }
            
            override fun onDone(utteranceId: String?) {
                _isSpeaking.value = false
                _currentText.value = null
            }
            
            @Deprecated("Deprecated in Java")
            override fun onError(utteranceId: String?) {
                _isSpeaking.value = false
                _currentText.value = null
            }
        })
    }
    
    /**
     * Speak the given text in the specified language
     */
    fun speak(text: String, language: Locale = Locale.US, rate: Float = 0.4f) {
        if (!_isInitialized.value) return
        
        stop()
        
        tts?.apply {
            this.language = language
            setSpeechRate(rate)
            setPitch(1.0f)
            
            _currentText.value = text
            speak(text, TextToSpeech.QUEUE_FLUSH, null, text.hashCode().toString())
        }
    }
    
    /**
     * Speak Arabic text with optimized settings
     */
    fun speakArabic(text: String, rate: Float = 0.35f) {
        speak(text, Locale("ar", "SA"), rate)
    }
    
    /**
     * Speak English text
     */
    fun speakEnglish(text: String, rate: Float = 0.45f) {
        speak(text, Locale.US, rate)
    }
    
    /**
     * Auto-detect language and speak
     */
    fun speakAutoDetect(text: String, rate: Float = 0.4f) {
        val language = detectLanguage(text)
        val detectedRate = if (language.language == "ar") 0.35f else rate
        speak(text, language, detectedRate)
    }
    
    /**
     * Stop any current speech or audio playback
     */
    fun stop() {
        tts?.stop()
        stopAudioPlayer()
        _isSpeaking.value = false
        _isPlayingAudio.value = false
        _currentText.value = null
    }
    
    /**
     * Play Quran recitation audio from a URL
     */
    fun playAudioURL(urlString: String, identifier: String) {
        stop()
        
        try {
            mediaPlayer = MediaPlayer().apply {
                setAudioAttributes(
                    android.media.AudioAttributes.Builder()
                        .setContentType(android.media.AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(android.media.AudioAttributes.USAGE_MEDIA)
                        .build()
                )
                setDataSource(urlString)
                
                setOnPreparedListener {
                    _currentText.value = identifier
                    _isSpeaking.value = true
                    _isPlayingAudio.value = true
                    start()
                }
                
                setOnCompletionListener {
                    _isSpeaking.value = false
                    _isPlayingAudio.value = false
                    _currentText.value = null
                }
                
                setOnErrorListener { _, _, _ ->
                    _isSpeaking.value = false
                    _isPlayingAudio.value = false
                    _currentText.value = null
                    true
                }
                
                prepareAsync()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            _isSpeaking.value = false
            _isPlayingAudio.value = false
        }
    }
    
    /**
     * Pause current speech
     */
    fun pause() {
        mediaPlayer?.pause()
    }
    
    /**
     * Resume paused audio
     */
    fun resume() {
        mediaPlayer?.start()
    }
    
    /**
     * Detect if text is Arabic or English
     */
    private fun detectLanguage(text: String): Locale {
        val arabicCharRange = '\u0600'..'\u06FF'
        val hasArabic = text.any { it in arabicCharRange }
        return if (hasArabic) Locale("ar", "SA") else Locale.US
    }
    
    private fun stopAudioPlayer() {
        mediaPlayer?.apply {
            stop()
            release()
        }
        mediaPlayer = null
    }
    
    /**
     * Clean up resources
     */
    fun shutdown() {
        stop()
        tts?.shutdown()
        tts = null
    }
}

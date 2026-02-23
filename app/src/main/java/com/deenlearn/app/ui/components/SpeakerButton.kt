package com.deenlearn.app.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.VolumeOff
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.deenlearn.app.DeenLearnApplication
import kotlinx.coroutines.launch

/**
 * A speaker button that uses TextToSpeech to read text aloud
 * Shows different icon when speaking
 */
@Composable
fun SpeakerButton(
    text: String,
    contentDescription: String = "Listen",
    isArabic: Boolean = false,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val app = context.applicationContext as DeenLearnApplication
    val ttsService = app.textToSpeechService
    
    // Observe speaking state
    val isSpeaking by ttsService.isSpeaking.collectAsState()
    val currentText by ttsService.currentText.collectAsState()
    val isThisTextSpeaking = isSpeaking && currentText == text
    
    val scope = rememberCoroutineScope()
    
    IconButton(
        onClick = {
            scope.launch {
                if (isThisTextSpeaking) {
                    ttsService.stop()
                } else {
                    if (isArabic) {
                        ttsService.speakArabic(text)
                    } else {
                        ttsService.speakAutoDetect(text)
                    }
                }
            }
        },
        modifier = modifier
    ) {
        Icon(
            imageVector = if (isThisTextSpeaking) Icons.Default.VolumeOff else Icons.Default.VolumeUp,
            contentDescription = contentDescription,
            tint = if (isThisTextSpeaking) 
                MaterialTheme.colorScheme.primary 
            else 
                MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(24.dp)
        )
    }
}

/**
 * A smaller speaker icon for inline use
 */
@Composable
fun SmallSpeakerButton(
    text: String,
    contentDescription: String = "Listen",
    isArabic: Boolean = false,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val app = context.applicationContext as DeenLearnApplication
    val ttsService = app.textToSpeechService
    
    val isSpeaking by ttsService.isSpeaking.collectAsState()
    val currentText by ttsService.currentText.collectAsState()
    val isThisTextSpeaking = isSpeaking && currentText == text
    
    val scope = rememberCoroutineScope()
    
    FilledIconButton(
        onClick = {
            scope.launch {
                if (isThisTextSpeaking) {
                    ttsService.stop()
                } else {
                    if (isArabic) {
                        ttsService.speakArabic(text)
                    } else {
                        ttsService.speakAutoDetect(text)
                    }
                }
            }
        },
        modifier = modifier.size(32.dp),
        colors = IconButtonDefaults.filledIconButtonColors(
            containerColor = if (isThisTextSpeaking)
                MaterialTheme.colorScheme.primaryContainer
            else
                MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Icon(
            imageVector = if (isThisTextSpeaking) Icons.Default.VolumeOff else Icons.Default.VolumeUp,
            contentDescription = contentDescription,
            modifier = Modifier.size(18.dp)
        )
    }
}

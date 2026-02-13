package com.deenlearn.app.ui.components

import androidx.compose.animation.core.*
import androidx.compose.animation.core.RepeatMode as AnimationRepeatMode
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.deenlearn.app.DeenLearnApplication
import com.deenlearn.app.services.RepeatMode
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

/**
 * Mini audio player that appears at bottom of screen
 */
@Composable
fun MiniAudioPlayer(
    modifier: Modifier = Modifier,
    onExpand: () -> Unit = {}
) {
    val context = LocalContext.current
    val audioService = remember { 
        (context.applicationContext as DeenLearnApplication).quranAudioService 
    }
    
    val currentPlaying by audioService.currentPlaying.collectAsState()
    val playbackState by audioService.playbackState.collectAsState()
    val progress by audioService.progress.collectAsState()
    
    if (currentPlaying != null) {
        OutlinedDeenCard(
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp),
            onClick = onExpand
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                // Progress bar
                LinearProgressIndicator(
                    progress = progress,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(3.dp)
                        .clip(MaterialTheme.shapes.small),
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Surah info
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = currentPlaying?.surahName ?: "",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = if (currentPlaying?.playlistMode == true) {
                                "Verse ${currentPlaying?.verseNumber} of ${currentPlaying?.totalVerses}"
                            } else {
                                "Verse ${currentPlaying?.verseNumber}"
                            },
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    
                    // Controls
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Previous button (if in playlist)
                        if (currentPlaying?.playlistMode == true) {
                            IconButton(
                                onClick = { audioService.previous() },
                                modifier = Modifier.size(36.dp)
                            ) {
                                Icon(
                                    Icons.Default.SkipPrevious,
                                    contentDescription = "Previous"
                                )
                            }
                        }
                        
                        // Play/Pause button
                        FilledIconButton(
                            onClick = {
                                if (playbackState.isPlaying) {
                                    audioService.pause()
                                } else {
                                    audioService.resume()
                                }
                            },
                            modifier = Modifier.size(48.dp)
                        ) {
                            if (playbackState.isLoading) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(24.dp),
                                    strokeWidth = 2.dp
                                )
                            } else {
                                Icon(
                                    if (playbackState.isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                                    contentDescription = if (playbackState.isPlaying) "Pause" else "Play"
                                )
                            }
                        }
                        
                        // Next button (if in playlist)
                        if (currentPlaying?.playlistMode == true) {
                            IconButton(
                                onClick = { audioService.next() },
                                modifier = Modifier.size(36.dp)
                            ) {
                                Icon(
                                    Icons.Default.SkipNext,
                                    contentDescription = "Next"
                                )
                            }
                        }
                        
                        // Stop button
                        IconButton(
                            onClick = { audioService.stop() },
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(
                                Icons.Default.Stop,
                                contentDescription = "Stop"
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * Full screen audio player with all controls
 */
@Composable
fun FullAudioPlayer(
    isKidsMode: Boolean,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val audioService = remember { 
        (context.applicationContext as DeenLearnApplication).quranAudioService 
    }
    
    val currentPlaying by audioService.currentPlaying.collectAsState()
    val playbackState by audioService.playbackState.collectAsState()
    val progress by audioService.progress.collectAsState()
    val currentPosition by audioService.currentPosition.collectAsState()
    val duration by audioService.duration.collectAsState()
    
    if (currentPlaying == null) {
        onDismiss()
        return
    }
    
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Close button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = onDismiss) {
                    Icon(Icons.Default.Close, "Close")
                }
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // Surah name and info
            Text(
                text = currentPlaying?.surahName ?: "",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            if (currentPlaying?.playlistMode == true) {
                Text(
                    text = "Verse ${currentPlaying?.verseNumber} of ${currentPlaying?.totalVerses}",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            } else {
                Text(
                    text = "Verse ${currentPlaying?.verseNumber}",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Spacer(modifier = Modifier.weight(1f))
            
            // Animated visual for kids mode
            if (isKidsMode) {
                AnimatedPlayingIndicator(isPlaying = playbackState.isPlaying)
                Spacer(modifier = Modifier.height(32.dp))
            }
            
            // Progress slider
            Column(modifier = Modifier.fillMaxWidth()) {
                Slider(
                    value = progress,
                    onValueChange = { newProgress ->
                        val newPosition = (newProgress * duration).toLong()
                        audioService.seekTo(newPosition)
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = formatTime(currentPosition),
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        text = formatTime(duration),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Main controls
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Previous (if playlist)
                if (currentPlaying?.playlistMode == true) {
                    IconButton(
                        onClick = { audioService.previous() },
                        modifier = Modifier.size(64.dp)
                    ) {
                        Icon(
                            Icons.Default.SkipPrevious,
                            "Previous",
                            modifier = Modifier.size(40.dp)
                        )
                    }
                } else {
                    Spacer(modifier = Modifier.size(64.dp))
                }
                
                // Play/Pause
                FilledIconButton(
                    onClick = {
                        if (playbackState.isPlaying) {
                            audioService.pause()
                        } else {
                            audioService.resume()
                        }
                    },
                    modifier = Modifier.size(80.dp)
                ) {
                    if (playbackState.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(40.dp),
                            strokeWidth = 3.dp
                        )
                    } else {
                        Icon(
                            if (playbackState.isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                            if (playbackState.isPlaying) "Pause" else "Play",
                            modifier = Modifier.size(48.dp)
                        )
                    }
                }
                
                // Next (if playlist)
                if (currentPlaying?.playlistMode == true) {
                    IconButton(
                        onClick = { audioService.next() },
                        modifier = Modifier.size(64.dp)
                    ) {
                        Icon(
                            Icons.Default.SkipNext,
                            "Next",
                            modifier = Modifier.size(40.dp)
                        )
                    }
                } else {
                    Spacer(modifier = Modifier.size(64.dp))
                }
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // Additional controls (not for kids mode)
            if (!isKidsMode) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    // Repeat mode
                    IconButton(onClick = {
                        val nextMode = when (playbackState.repeatMode) {
                            RepeatMode.ONCE -> RepeatMode.VERSE
                            RepeatMode.VERSE -> RepeatMode.SURAH
                            RepeatMode.SURAH -> RepeatMode.ONCE
                        }
                        audioService.setRepeatMode(nextMode)
                    }) {
                        Icon(
                            when (playbackState.repeatMode) {
                                RepeatMode.ONCE -> Icons.Default.Repeat
                                RepeatMode.VERSE -> Icons.Default.RepeatOne
                                RepeatMode.SURAH -> Icons.Default.RepeatOn
                            },
                            "Repeat: ${playbackState.repeatMode}",
                            tint = if (playbackState.repeatMode != RepeatMode.ONCE) 
                                MaterialTheme.colorScheme.primary 
                            else 
                                MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    
                    // Speed control
                    var showSpeedMenu by remember { mutableStateOf(false) }
                    Box {
                        TextButton(onClick = { showSpeedMenu = true }) {
                            Text("${playbackState.speed}x")
                        }
                        
                        DropdownMenu(
                            expanded = showSpeedMenu,
                            onDismissRequest = { showSpeedMenu = false }
                        ) {
                            listOf(0.5f, 0.75f, 1.0f, 1.25f, 1.5f, 2.0f).forEach { speed ->
                                DropdownMenuItem(
                                    text = { Text("${speed}x") },
                                    onClick = {
                                        audioService.setSpeed(speed)
                                        showSpeedMenu = false
                                    }
                                )
                            }
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

/**
 * Animated visual indicator for kids mode
 */
@Composable
private fun AnimatedPlayingIndicator(isPlaying: Boolean) {
    val infiniteTransition = rememberInfiniteTransition(label = "playing")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(600),
            repeatMode = AnimationRepeatMode.Reverse
        ),
        label = "scale"
    )
    
    Box(
        modifier = Modifier
            .size(120.dp)
            .scale(if (isPlaying) scale else 1f)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primaryContainer),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = if (isPlaying) "🎵" else "⏸️",
            style = MaterialTheme.typography.displayLarge
        )
    }
}

/**
 * Format milliseconds to MM:SS
 */
private fun formatTime(millis: Long): String {
    val totalSeconds = (millis / 1000).toInt()
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return String.format("%d:%02d", minutes, seconds)
}

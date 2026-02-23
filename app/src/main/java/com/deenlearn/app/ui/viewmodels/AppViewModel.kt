package com.deenlearn.app.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.deenlearn.app.DeenLearnApplication
import com.deenlearn.app.data.PreferencesManager
import com.deenlearn.app.models.AppState
import com.deenlearn.app.models.Profile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.*

class AppViewModel(application: Application) : AndroidViewModel(application) {
    
    private val preferencesManager = PreferencesManager(application)
    private val app = application as DeenLearnApplication
    
    private val _appState = MutableStateFlow(createDefaultAppState())
    val appState: StateFlow<AppState> = _appState.asStateFlow()
    
    private val _currentProfile = MutableStateFlow<Profile?>(null)
    val currentProfile: StateFlow<Profile?> = _currentProfile.asStateFlow()
    
    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    init {
        loadAppData()
    }
    
    private fun loadAppData() {
        viewModelScope.launch {
            try {
                // Load app state
                val savedState = preferencesManager.getAppState()
                if (savedState != null) {
                    _appState.value = savedState
                }
                
                // Load current profile
                val savedProfile = preferencesManager.getCurrentProfile()
                if (savedProfile != null) {
                    _currentProfile.value = savedProfile
                }
                
                _isLoading.value = false
            } catch (e: Exception) {
                e.printStackTrace()
                _isLoading.value = false
            }
        }
    }
    
    fun setUserMode(mode: AppState.UserMode) {
        viewModelScope.launch {
            _appState.value = _appState.value.copy(currentMode = mode)
            preferencesManager.saveAppState(_appState.value)
            preferencesManager.saveUserMode(mode)
        }
    }
    
    fun createProfile(name: String, age: Int, isParent: Boolean = false): Profile {
        val profile = Profile(
            id = UUID.randomUUID(),
            name = name,
            age = age,
            isParent = isParent,
            createdDate = Date()
        )
        
        viewModelScope.launch {
            _currentProfile.value = profile
            preferencesManager.saveCurrentProfile(profile)
        }
        
        return profile
    }
    
    fun updateProgress(lessonId: String, completed: Boolean) {
        viewModelScope.launch {
            val updatedState = _appState.value.copy(
                completedLessons = if (completed) {
                    _appState.value.completedLessons + lessonId
                } else {
                    _appState.value.completedLessons - lessonId
                }
            )
            _appState.value = updatedState
            preferencesManager.saveAppState(updatedState)
        }
    }
    
    fun addPoints(points: Int) {
        viewModelScope.launch {
            val updatedState = _appState.value.copy(
                totalPoints = _appState.value.totalPoints + points
            )
            _appState.value = updatedState
            preferencesManager.saveAppState(updatedState)
        }
    }
    
    fun updateStreak() {
        viewModelScope.launch {
            val today = Date()
            val lastDate = _appState.value.lastActivityDate
            
            val newStreak = if (lastDate != null && isSameDay(lastDate, today)) {
                _appState.value.currentStreak
            } else if (lastDate != null && isYesterday(lastDate, today)) {
                _appState.value.currentStreak + 1
            } else {
                1
            }
            
            val updatedState = _appState.value.copy(
                currentStreak = newStreak,
                longestStreak = maxOf(_appState.value.longestStreak, newStreak),
                lastActivityDate = today
            )
            _appState.value = updatedState
            preferencesManager.saveAppState(updatedState)
        }
    }
    
    fun awardBadge(badge: AppState.Badge) {
        viewModelScope.launch {
            if (!_appState.value.earnedBadges.contains(badge)) {
                val updatedState = _appState.value.copy(
                    earnedBadges = _appState.value.earnedBadges + badge
                )
                _appState.value = updatedState
                preferencesManager.saveAppState(updatedState)
            }
        }
    }
    
    fun isFirstLaunch(): Boolean {
        return preferencesManager.isFirstLaunch()
    }
    
    fun setFirstLaunchComplete() {
        preferencesManager.setFirstLaunchComplete()
    }
    
    private fun createDefaultAppState(): AppState {
        return AppState(
            currentMode = AppState.UserMode.KIDS,
            selectedAge = null,
            totalPoints = 0,
            currentStreak = 0,
            longestStreak = 0,
            starsEarned = 0,
            completedLessons = emptySet(),
            earnedBadges = emptySet(),
            journalEntries = emptyList(),
            lastActivityDate = null,
            dailyGoals = AppState.DailyGoals()
        )
    }
    
    private fun isSameDay(date1: Date, date2: Date): Boolean {
        val cal1 = Calendar.getInstance().apply { time = date1 }
        val cal2 = Calendar.getInstance().apply { time = date2 }
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
    }
    
    private fun isYesterday(date1: Date, date2: Date): Boolean {
        val cal1 = Calendar.getInstance().apply { time = date1 }
        val cal2 = Calendar.getInstance().apply { time = date2 }
        cal1.add(Calendar.DAY_OF_YEAR, 1)
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
    }
}

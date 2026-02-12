package com.deenlearn.app.data

import android.content.Context
import android.content.SharedPreferences
import com.deenlearn.app.models.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString

class PreferencesManager(context: Context) {
    
    private val prefs: SharedPreferences = context.getSharedPreferences(
        "deenlearn_prefs",
        Context.MODE_PRIVATE
    )
    
    private val json = Json {
        ignoreUnknownKeys = true
        prettyPrint = true
    }
    
    // App State
    fun saveAppState(appState: AppState) {
        prefs.edit().putString("app_state", json.encodeToString(appState)).apply()
    }
    
    fun getAppState(): AppState? {
        val stateJson = prefs.getString("app_state", null) ?: return null
        return try {
            json.decodeFromString<AppState>(stateJson)
        } catch (e: Exception) {
            null
        }
    }
    
    // Profiles
    fun saveProfiles(profiles: List<Profile>) {
        prefs.edit().putString("profiles", json.encodeToString(profiles)).apply()
    }
    
    fun getProfiles(): List<Profile> {
        val profilesJson = prefs.getString("profiles", null) ?: return emptyList()
        return try {
            json.decodeFromString<List<Profile>>(profilesJson)
        } catch (e: Exception) {
            emptyList()
        }
    }
    
    // Current Profile
    fun saveCurrentProfile(profile: Profile) {
        prefs.edit().putString("current_profile", json.encodeToString(profile)).apply()
    }
    
    fun getCurrentProfile(): Profile? {
        val profileJson = prefs.getString("current_profile", null) ?: return null
        return try {
            json.decodeFromString<Profile>(profileJson)
        } catch (e: Exception) {
            null
        }
    }
    
    // User Mode
    fun saveUserMode(mode: AppState.UserMode) {
        prefs.edit().putString("user_mode", mode.name).apply()
    }
    
    fun getUserMode(): AppState.UserMode {
        val modeName = prefs.getString("user_mode", AppState.UserMode.KIDS.name)
        return try {
            AppState.UserMode.valueOf(modeName ?: AppState.UserMode.KIDS.name)
        } catch (e: Exception) {
            AppState.UserMode.KIDS
        }
    }
    
    // Theme
    fun saveTheme(theme: String) {
        prefs.edit().putString("theme", theme).apply()
    }
    
    fun getTheme(): String {
        return prefs.getString("theme", "system") ?: "system"
    }
    
    // First Launch
    fun isFirstLaunch(): Boolean {
        return prefs.getBoolean("first_launch", true)
    }
    
    fun setFirstLaunchComplete() {
        prefs.edit().putBoolean("first_launch", false).apply()
    }
    
    // Location
    fun saveLastLocation(latitude: Double, longitude: Double, cityName: String) {
        prefs.edit()
            .putString("last_location_lat", latitude.toString())
            .putString("last_location_lon", longitude.toString())
            .putString("last_location_city", cityName)
            .apply()
    }
    
    fun getLastLocation(): Triple<Double, Double, String>? {
        val lat = prefs.getString("last_location_lat", null)?.toDoubleOrNull()
        val lon = prefs.getString("last_location_lon", null)?.toDoubleOrNull()
        val city = prefs.getString("last_location_city", null)
        
        return if (lat != null && lon != null && city != null) {
            Triple(lat, lon, city)
        } else {
            null
        }
    }
    
    // Clear all data
    fun clearAll() {
        prefs.edit().clear().apply()
    }
}

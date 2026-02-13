package com.deenlearn.app.services

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Looper
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*
import com.google.android.gms.location.Priority
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.*
import kotlin.coroutines.resume

/**
 * Location service for getting user coordinates for prayer times.
 * Uses Google Play Services Location API for Android geolocation.
 */

data class LocationData(
    val latitude: Double,
    val longitude: Double
)

class LocationService private constructor(private val context: Context) {
    
    private val prefs: SharedPreferences = 
        context.getSharedPreferences("location_prefs", Context.MODE_PRIVATE)
    
    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)
    
    private val _currentLocation = MutableStateFlow<LocationData?>(null)
    val currentLocation: StateFlow<LocationData?> = _currentLocation.asStateFlow()
    
    private val _locationName = MutableStateFlow("Detecting location...")
    val locationName: StateFlow<String> = _locationName.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()
    
    private val _isUsingDefaultLocation = MutableStateFlow(true)
    val isUsingDefaultLocation: StateFlow<Boolean> = _isUsingDefaultLocation.asStateFlow()
    
    private var locationCallback: LocationCallback? = null
    
    companion object {
        // Default location (Mecca)
        val DEFAULT_LOCATION = LocationData(21.4225, 39.8262)
        
        private const val CACHED_LAT_KEY = "cachedLocationLat"
        private const val CACHED_LON_KEY = "cachedLocationLon"
        private const val CACHED_NAME_KEY = "cachedLocationName"
        private const val HAS_CACHED_LOCATION_KEY = "hasCachedLocation"
        
        @Volatile
        private var instance: LocationService? = null
        
        fun getInstance(context: Context): LocationService {
            return instance ?: synchronized(this) {
                instance ?: LocationService(context.applicationContext).also { instance = it }
            }
        }
    }
    
    init {
        loadCachedLocation()
    }
    
    /**
     * Check if location permission is granted
     */
    fun hasLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED ||
        ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }
    
    /**
     * Request current location
     */
    suspend fun requestLocation(): Boolean {
        if (!hasLocationPermission()) {
            _error.value = "Location permission not granted"
            _isLoading.value = false
            setDefaultLocation()
            return false
        }
        
        _isLoading.value = true
        _error.value = null
        
        return try {
            val location = getLastKnownLocation()
            if (location != null) {
                updateLocation(location)
                true
            } else {
                requestNewLocation()
            }
        } catch (e: Exception) {
            _error.value = "Failed to get location: ${e.message}"
            _isLoading.value = false
            setDefaultLocation()
            false
        }
    }
    
    @SuppressLint("MissingPermission")
    private suspend fun getLastKnownLocation(): Location? = suspendCancellableCoroutine { cont ->
        if (!hasLocationPermission()) {
            cont.resume(null)
            return@suspendCancellableCoroutine
        }
        
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                cont.resume(location)
            }
            .addOnFailureListener {
                cont.resume(null)
            }
    }
    
    /**
     * Helper method to safely request location updates after permission verification
     */
    @SuppressLint("MissingPermission")
    private fun safeRequestLocationUpdates(
        locationRequest: LocationRequest,
        locationCallback: LocationCallback
    ) {
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }
    
    private suspend fun requestNewLocation(): Boolean = suspendCancellableCoroutine { cont ->
        if (!hasLocationPermission()) {
            cont.resume(false)
            return@suspendCancellableCoroutine
        }
        
        val locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            10000L
        ).apply {
            setMinUpdateIntervalMillis(5000L)
            setMaxUpdates(1)
        }.build()
        
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                result.lastLocation?.let { location ->
                    updateLocation(location)
                    fusedLocationClient.removeLocationUpdates(this)
                    cont.resume(true)
                } ?: cont.resume(false)
            }
        }
        
        // Explicit permission check required by lint before sensitive API call
        if (hasLocationPermission()) {
            safeRequestLocationUpdates(locationRequest, locationCallback!!)
        } else {
            cont.resume(false)
        }
    }
    
    /**
     * Start monitoring for significant location changes
     */
    fun startMonitoringLocationChanges() {
        if (!hasLocationPermission()) return
        
        val locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_BALANCED_POWER_ACCURACY,
            300000L // 5 minutes
        ).build()
        
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                result.lastLocation?.let { location ->
                    updateLocation(location)
                }
            }
        }
        
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback!!,
            Looper.getMainLooper()
        )
    }
    
    /**
     * Stop monitoring location changes
     */
    fun stopMonitoringLocationChanges() {
        locationCallback?.let {
            fusedLocationClient.removeLocationUpdates(it)
        }
    }
    
    /**
     * Get the best available location (user's or default)
     */
    fun getBestLocation(): LocationData {
        return _currentLocation.value ?: DEFAULT_LOCATION
    }
    
    private fun updateLocation(location: Location) {
        val locationData = LocationData(location.latitude, location.longitude)
        _currentLocation.value = locationData
        _isLoading.value = false
        _isUsingDefaultLocation.value = false
        _error.value = null
        
        reverseGeocode(locationData)
    }
    
    private fun setDefaultLocation() {
        if (_currentLocation.value == null) {
            _currentLocation.value = DEFAULT_LOCATION
            _locationName.value = "Mecca (Default)"
            _isUsingDefaultLocation.value = true
        }
    }
    
    private fun reverseGeocode(location: LocationData) {
        try {
            val geocoder = Geocoder(context, Locale.getDefault())
            
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                geocoder.getFromLocation(location.latitude, location.longitude, 1) { addresses ->
                    handleGeocodedAddresses(addresses, location)
                }
            } else {
                @Suppress("DEPRECATION")
                val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                handleGeocodedAddresses(addresses, location)
            }
        } catch (e: Exception) {
            _locationName.value = "Location Found"
        }
    }
    
    private fun handleGeocodedAddresses(addresses: List<Address>?, location: LocationData) {
        addresses?.firstOrNull()?.let { address ->
            val city = address.locality ?: ""
            val country = address.countryName ?: ""
            
            val name = when {
                city.isNotEmpty() && country.isNotEmpty() -> "$city, $country"
                city.isNotEmpty() -> city
                country.isNotEmpty() -> country
                else -> "Location Found"
            }
            
            _locationName.value = name
            cacheLocation(location, name)
        } ?: run {
            _locationName.value = "Location Found"
        }
    }
    
    private fun cacheLocation(location: LocationData, name: String) {
        prefs.edit().apply {
            putString(CACHED_LAT_KEY, location.latitude.toString())
            putString(CACHED_LON_KEY, location.longitude.toString())
            putString(CACHED_NAME_KEY, name)
            putBoolean(HAS_CACHED_LOCATION_KEY, true)
            apply()
        }
    }
    
    private fun loadCachedLocation() {
        if (!prefs.getBoolean(HAS_CACHED_LOCATION_KEY, false)) {
            setDefaultLocation()
            return
        }
        
        val lat = prefs.getString(CACHED_LAT_KEY, null)?.toDoubleOrNull()
        val lon = prefs.getString(CACHED_LON_KEY, null)?.toDoubleOrNull()
        val name = prefs.getString(CACHED_NAME_KEY, null)
        
        if (lat != null && lon != null) {
            _currentLocation.value = LocationData(lat, lon)
            _locationName.value = name ?: "Cached Location"
            _isUsingDefaultLocation.value = false
        } else {
            setDefaultLocation()
        }
    }
}

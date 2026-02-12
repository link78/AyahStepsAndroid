package com.deenlearn.app.services

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.*

/**
 * Prayer time calculation service based on user's location
 * Uses astronomical calculations for accurate prayer times
 */

// MARK: - Prayer Time Model
data class PrayerTime(
    val name: String,
    val arabicName: String,
    val time: Date,
    val icon: String,
    val isPrayer: Boolean,
    val timezone: TimeZone
) {
    fun formattedTime(): String {
        val formatter = SimpleDateFormat("h:mm a", Locale.getDefault())
        formatter.timeZone = timezone
        return formatter.format(time)
    }
}

// MARK: - Calculation Method
enum class CalculationMethod(val displayName: String) {
    MWL("Muslim World League"),
    ISNA("ISNA (North America)"),
    EGYPT("Egyptian General Authority"),
    MAKKAH("Umm Al-Qura (Makkah)"),
    KARACHI("University of Islamic Sciences, Karachi"),
    TEHRAN("Institute of Geophysics, Tehran"),
    SINGAPORE("Singapore");
    
    val apiMethodNumber: Int
        get() = when (this) {
            MWL -> 3
            ISNA -> 2
            EGYPT -> 5
            MAKKAH -> 4
            KARACHI -> 1
            TEHRAN -> 7
            SINGAPORE -> 11
        }
    
    val fajrAngle: Double
        get() = when (this) {
            MWL -> 18.0
            ISNA -> 15.0
            EGYPT -> 19.5
            MAKKAH -> 18.5
            KARACHI -> 18.0
            TEHRAN -> 17.7
            SINGAPORE -> 20.0
        }
    
    val ishaAngle: Double
        get() = when (this) {
            MWL -> 17.0
            ISNA -> 15.0
            EGYPT -> 17.5
            MAKKAH -> -90.0 // 90 minutes after Maghrib
            KARACHI -> 18.0
            TEHRAN -> 14.0
            SINGAPORE -> 18.0
        }
}

// MARK: - Juristic Method for Asr
enum class AsrJuristicMethod(val displayName: String) {
    SHAFII("Shafi'i, Maliki, Hanbali"),
    HANAFI("Hanafi");
    
    val apiSchoolNumber: Int
        get() = when (this) {
            SHAFII -> 0
            HANAFI -> 1
        }
    
    val shadowFactor: Double
        get() = when (this) {
            SHAFII -> 1.0
            HANAFI -> 2.0
        }
}

// MARK: - Prayer Time Service
class PrayerTimeService(private val context: Context) {
    private val prefs: SharedPreferences = 
        context.getSharedPreferences("prayer_times", Context.MODE_PRIVATE)
    
    private val _prayerTimes = MutableStateFlow<List<PrayerTime>>(emptyList())
    val prayerTimes: StateFlow<List<PrayerTime>> = _prayerTimes.asStateFlow()
    
    private val _nextPrayer = MutableStateFlow<PrayerTime?>(null)
    val nextPrayer: StateFlow<PrayerTime?> = _nextPrayer.asStateFlow()
    
    private val _timeUntilNextPrayer = MutableStateFlow("")
    val timeUntilNextPrayer: StateFlow<String> = _timeUntilNextPrayer.asStateFlow()
    
    private val _currentPrayerIndex = MutableStateFlow(-1)
    val currentPrayerIndex: StateFlow<Int> = _currentPrayerIndex.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _hijriDate = MutableStateFlow("")
    val hijriDate: StateFlow<String> = _hijriDate.asStateFlow()
    
    private val _qiblaDirection = MutableStateFlow<Double?>(null)
    val qiblaDirection: StateFlow<Double?> = _qiblaDirection.asStateFlow()
    
    private val _isUsingAPI = MutableStateFlow(false)
    val isUsingAPI: StateFlow<Boolean> = _isUsingAPI.asStateFlow()
    
    private val _calculationMethod = MutableStateFlow(
        prefs.getString("calculationMethod", null)?.let {
            CalculationMethod.valueOf(it)
        } ?: CalculationMethod.ISNA
    )
    val calculationMethod: StateFlow<CalculationMethod> = _calculationMethod.asStateFlow()
    
    private val _asrMethod = MutableStateFlow(
        prefs.getString("asrMethod", null)?.let {
            AsrJuristicMethod.valueOf(it)
        } ?: AsrJuristicMethod.SHAFII
    )
    val asrMethod: StateFlow<AsrJuristicMethod> = _asrMethod.asStateFlow()
    
    private var timerJob: Job? = null
    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    
    init {
        startTimer()
        // Calculate initial prayer times
        scope.launch {
            delay(500)
            calculatePrayerTimes()
        }
    }
    
    // MARK: - Public Methods
    
    fun setCalculationMethod(method: CalculationMethod) {
        _calculationMethod.value = method
        prefs.edit().putString("calculationMethod", method.name).apply()
        scope.launch { calculatePrayerTimes() }
    }
    
    fun setAsrMethod(method: AsrJuristicMethod) {
        _asrMethod.value = method
        prefs.edit().putString("asrMethod", method.name).apply()
        scope.launch { calculatePrayerTimes() }
    }
    
    suspend fun calculatePrayerTimes() {
        _isLoading.value = true
        val location = LocationService.getInstance(context).getBestLocation()
        
        // Use local calculation immediately
        calculateLocalPrayerTimes(location.latitude, location.longitude, Date())
        
        // Fetch from API in background
        fetchAPIPrayerTimes(location.latitude, location.longitude, Date())
    }
    
    private suspend fun fetchAPIPrayerTimes(latitude: Double, longitude: Double, date: Date) {
        val apiData = IslamicAPIService.fetchPrayerTimes(
            latitude, longitude, date,
            _calculationMethod.value.apiMethodNumber,
            _asrMethod.value.apiSchoolNumber
        )
        
        if (apiData != null) {
            val deviceTimezone = TimeZone.getDefault()
            val timings = apiData.timings
            
            val prayers = listOf(
                "Fajr" to "الفجر" to timings.Fajr to "sun_horizon" to true,
                "Shuruq" to "الشروق" to timings.Sunrise to "sunrise" to false,
                "Dhuhr" to "الظهر" to timings.Dhuhr to "sun_max" to true,
                "Asr" to "العصر" to timings.Asr to "sun_min" to true,
                "Maghrib" to "المغرب" to timings.Maghrib to "sunset" to true,
                "Isha" to "العشاء" to timings.Isha to "moon_stars" to true
            ).mapNotNull { (name, arabicName, timeString, icon, isPrayer) ->
                parseAPITime(timeString, date, deviceTimezone)?.let { prayerDate ->
                    PrayerTime(name, arabicName, prayerDate, icon, isPrayer, deviceTimezone)
                }
            }
            
            if (prayers.isNotEmpty()) {
                _prayerTimes.value = prayers
                _isUsingAPI.value = true
                
                val hijri = apiData.date.hijri
                _hijriDate.value = "${hijri.day} ${hijri.month.en} ${hijri.year}"
                
                updateNextPrayer()
            }
            
            val qibla = IslamicAPIService.fetchQiblaDirection(latitude, longitude)
            _qiblaDirection.value = qibla
        }
        
        _isLoading.value = false
    }
    
    private fun parseAPITime(timeString: String, baseDate: Date, timezone: TimeZone): Date? {
        val cleanTime = timeString.split(" ").first()
        val parts = cleanTime.split(":")
        if (parts.size != 2) return null
        
        val hours = parts[0].toIntOrNull() ?: return null
        val minutes = parts[1].toIntOrNull() ?: return null
        
        val calendar = Calendar.getInstance(timezone)
        calendar.time = baseDate
        calendar.set(Calendar.HOUR_OF_DAY, hours)
        calendar.set(Calendar.MINUTE, minutes)
        calendar.set(Calendar.SECOND, 0)
        
        return calendar.time
    }
    
    private fun calculateLocalPrayerTimes(latitude: Double, longitude: Double, date: Date, timezone: TimeZone? = null) {
        val calendar = Calendar.getInstance()
        calendar.time = date
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        
        val deviceTimezone = timezone ?: TimeZone.getDefault()
        val timezoneOffset = deviceTimezone.getOffset(date.time) / 3600000.0
        
        val jd = julianDate(year, month, day)
        val times = computePrayerTimes(jd, latitude, longitude, timezoneOffset)
        
        val prayers = listOf(
            "Fajr" to "الفجر" to times.fajr to "sun_horizon" to true,
            "Shuruq" to "الشروق" to times.sunrise to "sunrise" to false,
            "Dhuhr" to "الظهر" to times.dhuhr to "sun_max" to true,
            "Asr" to "العصر" to times.asr to "sun_min" to true,
            "Maghrib" to "المغرب" to times.maghrib to "sunset" to true,
            "Isha" to "العشاء" to times.isha to "moon_stars" to true
        ).mapNotNull { (name, arabicName, time, icon, isPrayer) ->
            timeToDate(time, date, deviceTimezone)?.let { prayerDate ->
                PrayerTime(name, arabicName, prayerDate, icon, isPrayer, deviceTimezone)
            }
        }
        
        _prayerTimes.value = prayers
        updateNextPrayer()
        _isLoading.value = false
    }
    
    // MARK: - Prayer Time Calculations
    
    private data class PrayerTimesResult(
        var fajr: Double = 0.0,
        var sunrise: Double = 0.0,
        var dhuhr: Double = 0.0,
        var asr: Double = 0.0,
        var maghrib: Double = 0.0,
        var isha: Double = 0.0
    )
    
    private fun computePrayerTimes(jd: Double, latitude: Double, longitude: Double, timezone: Double): PrayerTimesResult {
        val result = PrayerTimesResult()
        
        val d = jd - 2451545.0
        val g = fixAngle(357.529 + 0.98560028 * d)
        val q = fixAngle(280.459 + 0.98564736 * d)
        val l = fixAngle(q + 1.915 * sin(degToRad(g)) + 0.020 * sin(degToRad(2 * g)))
        val e = 23.439 - 0.00000036 * d
        val ra = radToDeg(atan2(cos(degToRad(e)) * sin(degToRad(l)), cos(degToRad(l)))) / 15.0
        val dec = radToDeg(asin(sin(degToRad(e)) * sin(degToRad(l))))
        val eqt = q / 15.0 - fixHour(ra)
        
        result.dhuhr = 12 + timezone - longitude / 15.0 - eqt
        
        val sunriseAngle = 0.833
        result.sunrise = result.dhuhr - timeDiff(sunriseAngle, dec, latitude)
        result.maghrib = result.dhuhr + timeDiff(sunriseAngle, dec, latitude)
        
        result.fajr = result.dhuhr - timeDiff(_calculationMethod.value.fajrAngle, dec, latitude)
        
        val asrAngle = radToDeg(atan(1 / (_asrMethod.value.shadowFactor + tan(degToRad(abs(latitude - dec))))))
        result.asr = result.dhuhr + asrTimeDiff(asrAngle, dec, latitude)
        
        val ishaAngle = _calculationMethod.value.ishaAngle
        result.isha = if (ishaAngle < 0) {
            result.maghrib + abs(ishaAngle) / 60.0
        } else {
            result.dhuhr + timeDiff(ishaAngle, dec, latitude)
        }
        
        return result
    }
    
    private fun julianDate(year: Int, month: Int, day: Int): Double {
        var y = year
        var m = month
        
        if (m <= 2) {
            y -= 1
            m += 12
        }
        
        val a = (y / 100.0).toInt()
        val b = 2 - a + (a / 4.0).toInt()
        
        return floor(365.25 * (y + 4716)) + floor(30.6001 * (m + 1)) + day + b - 1524.5
    }
    
    private fun timeDiff(angle: Double, dec: Double, lat: Double): Double {
        val cosAngle = (-sin(degToRad(angle)) - sin(degToRad(dec)) * sin(degToRad(lat))) / (cos(degToRad(dec)) * cos(degToRad(lat)))
        return radToDeg(acos(cosAngle.coerceIn(-1.0, 1.0))) / 15.0
    }
    
    private fun asrTimeDiff(elevation: Double, dec: Double, lat: Double): Double {
        val cosAngle = (sin(degToRad(elevation)) - sin(degToRad(dec)) * sin(degToRad(lat))) / (cos(degToRad(dec)) * cos(degToRad(lat)))
        return radToDeg(acos(cosAngle.coerceIn(-1.0, 1.0))) / 15.0
    }
    
    private fun timeToDate(time: Double, baseDate: Date, timezone: TimeZone): Date? {
        val hours = time.toInt()
        val minutes = ((time - hours) * 60).toInt()
        
        val calendar = Calendar.getInstance(timezone)
        calendar.time = baseDate
        calendar.set(Calendar.HOUR_OF_DAY, hours)
        calendar.set(Calendar.MINUTE, minutes)
        calendar.set(Calendar.SECOND, 0)
        
        return calendar.time
    }
    
    // MARK: - Helper Math Functions
    
    private fun degToRad(d: Double) = d * Math.PI / 180.0
    private fun radToDeg(r: Double) = r * 180.0 / Math.PI
    private fun fixAngle(a: Double) = a - 360.0 * floor(a / 360.0)
    private fun fixHour(h: Double) = h - 24.0 * floor(h / 24.0)
    
    // MARK: - Next Prayer Tracking
    
    private fun startTimer() {
        timerJob = scope.launch {
            while (isActive) {
                updateNextPrayer()
                delay(1000)
            }
        }
    }
    
    private fun updateNextPrayer() {
        val now = Date()
        val prayers = _prayerTimes.value
        
        var foundNext = false
        for ((index, prayer) in prayers.withIndex()) {
            if (prayer.time.after(now) && prayer.isPrayer) {
                _nextPrayer.value = prayer
                _currentPrayerIndex.value = index - 1
                foundNext = true
                updateCountdown(prayer.time)
                break
            }
        }
        
        if (!foundNext) {
            prayers.firstOrNull { it.isPrayer }?.let { fajr ->
                _nextPrayer.value = fajr
                _currentPrayerIndex.value = prayers.size - 1
                
                val calendar = Calendar.getInstance()
                calendar.time = fajr.time
                calendar.add(Calendar.DAY_OF_MONTH, 1)
                updateCountdown(calendar.time)
            }
        }
    }
    
    private fun updateCountdown(prayerTime: Date) {
        val now = Date()
        val diff = (prayerTime.time - now.time) / 1000
        
        if (diff <= 0) {
            _timeUntilNextPrayer.value = "Now"
            return
        }
        
        val hours = (diff / 3600).toInt()
        val minutes = ((diff % 3600) / 60).toInt()
        val seconds = (diff % 60).toInt()
        
        _timeUntilNextPrayer.value = if (hours > 0) {
            String.format("%d:%02d:%02d", hours, minutes, seconds)
        } else {
            String.format("%d:%02d", minutes, seconds)
        }
    }
    
    fun cleanup() {
        timerJob?.cancel()
        scope.cancel()
    }
    
    companion object {
        @Volatile
        private var instance: PrayerTimeService? = null
        
        fun getInstance(context: Context): PrayerTimeService {
            return instance ?: synchronized(this) {
                instance ?: PrayerTimeService(context.applicationContext).also { instance = it }
            }
        }
    }
}

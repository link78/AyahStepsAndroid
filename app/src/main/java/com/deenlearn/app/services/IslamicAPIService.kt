package com.deenlearn.app.services

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.text.SimpleDateFormat
import java.util.*

/**
 * Islamic data API service using Aladhan API
 * Provides prayer times, Qibla direction, and Hijri date
 * No API key required - free and open
 */

// MARK: - API Response Models

data class AladhanTimingsResponse(
    val code: Int,
    val status: String,
    val data: AladhanTimingsData
)

data class AladhanTimingsData(
    val timings: AladhanTimings,
    val date: AladhanDate,
    val meta: AladhanMeta
)

data class AladhanTimings(
    val Fajr: String,
    val Sunrise: String,
    val Dhuhr: String,
    val Asr: String,
    val Maghrib: String,
    val Isha: String
)

data class AladhanDate(
    val readable: String,
    val hijri: AladhanHijriDate,
    val gregorian: AladhanGregorianDate
)

data class AladhanHijriDate(
    val date: String,
    val day: String,
    val month: AladhanMonth,
    val year: String,
    val designation: AladhanDesignation,
    val weekday: AladhanWeekday
)

data class AladhanGregorianDate(
    val date: String,
    val day: String,
    val month: AladhanMonth,
    val year: String,
    val weekday: AladhanWeekday
)

data class AladhanMonth(
    val number: Int,
    val en: String,
    val ar: String?
)

data class AladhanDesignation(
    val abbreviated: String,
    val expanded: String
)

data class AladhanWeekday(
    val en: String,
    val ar: String?
)

data class AladhanMeta(
    val latitude: Double,
    val longitude: Double,
    val timezone: String,
    val method: AladhanMethod,
    val school: String
)

data class AladhanMethod(
    val id: Int,
    val name: String
)

data class AladhanQiblaResponse(
    val code: Int,
    val status: String,
    val data: AladhanQiblaData
)

data class AladhanQiblaData(
    val latitude: Double,
    val longitude: Double,
    val direction: Double
)

// MARK: - Retrofit API Interface
interface IslamicAPI {
    @GET("timings/{date}")
    suspend fun getTimings(
        @Path("date") date: String,
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("method") method: Int,
        @Query("school") school: Int
    ): AladhanTimingsResponse
    
    @GET("qibla/{latitude}/{longitude}")
    suspend fun getQibla(
        @Path("latitude") latitude: Double,
        @Path("longitude") longitude: Double
    ): AladhanQiblaResponse
}

// MARK: - Islamic API Service
object IslamicAPIService {
    private const val BASE_URL = "https://api.aladhan.com/v1/"
    
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    
    private val api = retrofit.create(IslamicAPI::class.java)
    
    // MARK: - Prayer Times
    
    /**
     * Fetch prayer times from API for given coordinates and date
     */
    suspend fun fetchPrayerTimes(
        latitude: Double,
        longitude: Double,
        date: Date = Date(),
        method: Int = 2,
        school: Int = 0
    ): AladhanTimingsData? {
        return try {
            val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            val dateString = formatter.format(date)
            
            val response = api.getTimings(dateString, latitude, longitude, method, school)
            
            if (response.code == 200) {
                response.data
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
    
    // MARK: - Qibla Direction
    
    /**
     * Fetch Qibla direction for given coordinates
     */
    suspend fun fetchQiblaDirection(latitude: Double, longitude: Double): Double? {
        return try {
            val response = api.getQibla(latitude, longitude)
            if (response.code == 200) {
                response.data.direction
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}

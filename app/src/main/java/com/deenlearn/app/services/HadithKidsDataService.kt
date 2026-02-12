package com.deenlearn.app.services

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Manages kids hadith content with API-backed Arabic text and caching
 * Uses Islamic Network Hadith API via HadithAPIService
 */
object HadithKidsDataService {
    
    private val _enrichedArabicTexts = MutableStateFlow<Map<String, String>>(emptyMap())
    val enrichedArabicTexts: StateFlow<Map<String, String>> = _enrichedArabicTexts.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private var hasFetchedAll = false
    
    /**
     * Fetch Arabic text for a specific hadith from the API
     */
    suspend fun fetchArabicText(collection: String, number: Int): String? {
        val cacheKey = "$collection-$number"
        _enrichedArabicTexts.value[cacheKey]?.let { return it }
        
        val result = HadithAPIService.fetchHadith(collection, number)
        return result.getOrNull()?.let { content ->
            val updatedMap = _enrichedArabicTexts.value.toMutableMap()
            updatedMap[cacheKey] = content.arab
            _enrichedArabicTexts.value = updatedMap
            content.arab
        }
    }
    
    /**
     * Get the best Arabic text: API-fetched if available, otherwise local fallback
     */
    fun arabicText(collection: String, number: Int, fallback: String): String {
        val cacheKey = "$collection-$number"
        return _enrichedArabicTexts.value[cacheKey] ?: fallback
    }
    
    /**
     * Clear all cached data
     */
    fun clearCache() {
        _enrichedArabicTexts.value = emptyMap()
        hasFetchedAll = false
    }
}

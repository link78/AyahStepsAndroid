package com.deenlearn.app.services

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.android.billingclient.api.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Subscription service for DeenLearn Plus premium features
 * Manages Google Play Billing and subscription state
 */

// MARK: - Subscription Plans
enum class SubscriptionPlan(val productId: String, val displayName: String, val price: String) {
    FREE("free", "Basic (Free)", "Free"),
    MONTHLY("com.deenlearn.plus.monthly", "Plus Monthly", "$4.99/month"),
    YEARLY("com.deenlearn.plus.yearly", "Plus Yearly", "$39.99/year");
    
    val isPremium: Boolean
        get() = this != FREE
    
    val isBestValue: Boolean
        get() = this == YEARLY
    
    val savingsPercentage: Int?
        get() = if (this == YEARLY) 33 else null
}

// MARK: - Premium Features
enum class PremiumFeature(val key: String, val displayName: String, val description: String, val icon: String) {
    FULL_SALAH_TRAINER("full_salah_trainer", "Full Salah Trainer", "Learn all 5 daily prayers", "prayer"),
    FULL_PILLARS_LESSONS("full_pillars_lessons", "Full Pillars Lessons", "Complete lessons on all 5 Pillars", "star"),
    QURAN_AUDIO_WORD_BY_WORD("quran_audio_word_by_word", "Word-by-Word Audio", "Hear each word with pronunciation", "volume_up"),
    TAJWID_COLOR_OVERLAY("tajwid_color_overlay", "Tajwīd Colors", "Color-coded Tajwīd rules", "palette"),
    MEMORIZATION_MODE("memorization_mode", "Memorization Mode", "Looping and call-response", "repeat"),
    FULL_ARABIC_ALPHABET("full_arabic_alphabet", "Full Arabic Alphabet", "All 28 Arabic letters", "text_format"),
    ALPHABET_TRACING("alphabet_tracing", "Letter Tracing", "Practice writing Arabic letters", "edit"),
    VOCABULARY_PACKS("vocabulary_packs", "Vocabulary Packs", "Expand your Arabic vocabulary", "menu_book"),
    MINI_GAMES("mini_games", "Mini Games", "Fun learning games", "sports_esports"),
    UNLIMITED_CHILD_PROFILES("unlimited_child_profiles", "Unlimited Profiles", "Create profiles for all children", "people"),
    PARENT_CONTROLS("parent_controls", "Parent Controls", "Set screen time and learning goals", "shield"),
    OFFLINE_MODE("offline_mode", "Offline Mode", "Access content without internet", "download"),
    FAMILY_ACHIEVEMENTS("family_achievements", "Family Achievements", "Track family progress together", "emoji_events")
}

// MARK: - Subscription Service
class SubscriptionService private constructor(private val context: Context) {
    
    private val prefs: SharedPreferences = 
        context.getSharedPreferences("subscription_prefs", Context.MODE_PRIVATE)
    
    private val _currentPlan = MutableStateFlow(loadSavedPlan())
    val currentPlan: StateFlow<SubscriptionPlan> = _currentPlan.asStateFlow()
    
    private val _isSubscribed = MutableStateFlow(loadSubscriptionState())
    val isSubscribed: StateFlow<Boolean> = _isSubscribed.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()
    
    private var billingClient: BillingClient? = null
    private val purchasesUpdatedListener = PurchasesUpdatedListener { billingResult, purchases ->
        if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && purchases != null) {
            for (purchase in purchases) {
                handlePurchase(purchase)
            }
        }
    }
    
    companion object {
        @Volatile
        private var instance: SubscriptionService? = null
        
        fun getInstance(context: Context): SubscriptionService {
            return instance ?: synchronized(this) {
                instance ?: SubscriptionService(context.applicationContext).also { instance = it }
            }
        }
        
        private const val FREE_LETTER_LIMIT = 8
        private const val FREE_SURAH_LIMIT_START = 78
        private const val FREE_CHILD_PROFILE_LIMIT = 1
        private const val FREE_WORLDS_LIMIT = 1
    }
    
    init {
        setupBillingClient()
    }
    
    private fun setupBillingClient() {
        billingClient = BillingClient.newBuilder(context)
            .setListener(purchasesUpdatedListener)
            .enablePendingPurchases()
            .build()
        
        billingClient?.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    queryPurchases()
                }
            }
            
            override fun onBillingServiceDisconnected() {
                // Retry connection
            }
        })
    }
    
    private fun queryPurchases() {
        billingClient?.queryPurchasesAsync(BillingClient.ProductType.SUBS) { billingResult, purchases ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                var hasActiveSubscription = false
                purchases.forEach { purchase ->
                    if (purchase.purchaseState == Purchase.PurchaseState.PURCHASED) {
                        hasActiveSubscription = true
                        when {
                            purchase.products.contains(SubscriptionPlan.YEARLY.productId) -> 
                                _currentPlan.value = SubscriptionPlan.YEARLY
                            purchase.products.contains(SubscriptionPlan.MONTHLY.productId) -> 
                                _currentPlan.value = SubscriptionPlan.MONTHLY
                        }
                    }
                }
                
                if (!hasActiveSubscription) {
                    _currentPlan.value = SubscriptionPlan.FREE
                }
                
                _isSubscribed.value = hasActiveSubscription
                saveSubscriptionState()
            }
        }
    }
    
    private fun handlePurchase(purchase: Purchase) {
        if (purchase.purchaseState == Purchase.PurchaseState.PURCHASED) {
            if (!purchase.isAcknowledged) {
                val acknowledgePurchaseParams = AcknowledgePurchaseParams.newBuilder()
                    .setPurchaseToken(purchase.purchaseToken)
                    .build()
                billingClient?.acknowledgePurchase(acknowledgePurchaseParams) { }
            }
            queryPurchases()
        }
    }
    
    // MARK: - Feature Access
    
    fun hasAccess(to: PremiumFeature): Boolean = _isSubscribed.value
    
    val isPremium: Boolean
        get() = _isSubscribed.value
    
    // MARK: - Content Limits
    
    val availableLetterCount: Int
        get() = if (isPremium) 28 else FREE_LETTER_LIMIT
    
    fun isLetterAvailable(index: Int): Boolean = isPremium || index < FREE_LETTER_LIMIT
    
    fun isSurahAvailable(number: Int): Boolean = isPremium || number >= FREE_SURAH_LIMIT_START
    
    fun isPrayerAvailable(name: String): Boolean = isPremium || name.equals("fajr", true)
    
    val hasFullSalahAccess: Boolean
        get() = isPremium
    
    val availableWorldCount: Int
        get() = if (isPremium) 5 else FREE_WORLDS_LIMIT
    
    fun isWorldAvailable(index: Int): Boolean = isPremium || index < FREE_WORLDS_LIMIT
    
    val childProfileLimit: Int
        get() = if (isPremium) Int.MAX_VALUE else FREE_CHILD_PROFILE_LIMIT
    
    // MARK: - Quran Features
    
    val hasWordByWordAudio: Boolean
        get() = isPremium
    
    val hasTajwidColors: Boolean
        get() = isPremium
    
    val hasMemorizationMode: Boolean
        get() = isPremium
    
    // MARK: - Persistence
    
    private fun saveSubscriptionState() {
        prefs.edit().apply {
            putString("subscription_plan", _currentPlan.value.name)
            putBoolean("is_subscribed", _isSubscribed.value)
            apply()
        }
    }
    
    private fun loadSavedPlan(): SubscriptionPlan {
        val planName = prefs.getString("subscription_plan", null)
        return try {
            if (planName != null) SubscriptionPlan.valueOf(planName) else SubscriptionPlan.FREE
        } catch (e: Exception) {
            SubscriptionPlan.FREE
        }
    }
    
    private fun loadSubscriptionState(): Boolean {
        return prefs.getBoolean("is_subscribed", false)
    }
    
    // MARK: - Purchase
    
    fun launchPurchaseFlow(activity: Activity, plan: SubscriptionPlan) {
        if (!plan.isPremium) return
        
        val productList = listOf(
            QueryProductDetailsParams.Product.newBuilder()
                .setProductId(plan.productId)
                .setProductType(BillingClient.ProductType.SUBS)
                .build()
        )
        
        val params = QueryProductDetailsParams.newBuilder()
            .setProductList(productList)
            .build()
        
        billingClient?.queryProductDetailsAsync(params) { billingResult, productDetailsList ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && productDetailsList.isNotEmpty()) {
                val productDetails = productDetailsList[0]
                
                productDetails.subscriptionOfferDetails?.firstOrNull()?.let { offerDetails ->
                    val offerToken = offerDetails.offerToken
                    
                    val productDetailsParamsList = listOf(
                        BillingFlowParams.ProductDetailsParams.newBuilder()
                            .setProductDetails(productDetails)
                            .setOfferToken(offerToken)
                            .build()
                    )
                    
                    val billingFlowParams = BillingFlowParams.newBuilder()
                        .setProductDetailsParamsList(productDetailsParamsList)
                        .build()
                    
                    billingClient?.launchBillingFlow(activity, billingFlowParams)
                }
            }
        }
    }
    
    fun restorePurchases() {
        queryPurchases()
    }
}

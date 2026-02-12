package com.deenlearn.app

import android.app.Application
import com.deenlearn.app.services.*

class DeenLearnApplication : Application() {
    
    // Services
    lateinit var quranAPIService: QuranAPIService
        private set
    lateinit var prayerTimeService: PrayerTimeService
        private set
    lateinit var locationService: LocationService
        private set
    lateinit var islamicAPIService: IslamicAPIService
        private set
    lateinit var hadithAPIService: HadithAPIService
        private set
    lateinit var hadithKidsDataService: HadithKidsDataService
        private set
    lateinit var quranDataService: QuranDataService
        private set
    lateinit var textToSpeechService: TextToSpeechService
        private set
    lateinit var subscriptionService: SubscriptionService
        private set
    lateinit var aiTutorService: AITutorService
        private set
    lateinit var aiQuranCoachService: AIQuranCoachService
        private set
    lateinit var aiQuestionGeneratorService: AIQuestionGeneratorService
        private set
    lateinit var aiLearningAssistantService: AILearningAssistantService
        private set
    
    override fun onCreate() {
        super.onCreate()
        instance = this
        
        // Initialize services
        quranAPIService = QuranAPIService()
        locationService = LocationService(this)
        prayerTimeService = PrayerTimeService(this)
        islamicAPIService = IslamicAPIService()
        hadithAPIService = HadithAPIService()
        hadithKidsDataService = HadithKidsDataService()
        quranDataService = QuranDataService(this)
        textToSpeechService = TextToSpeechService(this)
        subscriptionService = SubscriptionService(this)
        aiTutorService = AITutorService()
        aiQuranCoachService = AIQuranCoachService()
        aiQuestionGeneratorService = AIQuestionGeneratorService()
        aiLearningAssistantService = AILearningAssistantService()
    }
    
    override fun onTerminate() {
        super.onTerminate()
        textToSpeechService.shutdown()
        locationService.stopLocationUpdates()
    }
    
    companion object {
        lateinit var instance: DeenLearnApplication
            private set
    }
}

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
    lateinit var quranAudioService: QuranAudioService
        private set
    
    override fun onCreate() {
        super.onCreate()
        instance = this
        
        // Initialize services
        // Object singletons - direct reference (no instantiation needed)
        quranAPIService = QuranAPIService
        islamicAPIService = IslamicAPIService
        hadithAPIService = HadithAPIService
        hadithKidsDataService = HadithKidsDataService
        aiTutorService = AITutorService
        aiQuranCoachService = AIQuranCoachService
        aiQuestionGeneratorService = AIQuestionGeneratorService
        aiLearningAssistantService = AILearningAssistantService
        
        // Services with private constructors - use getInstance factory method
        locationService = LocationService.getInstance(this)
        prayerTimeService = PrayerTimeService.getInstance(this)
        quranDataService = QuranDataService.getInstance(this)
        textToSpeechService = TextToSpeechService.getInstance(this)
        subscriptionService = SubscriptionService.getInstance(this)
        quranAudioService = QuranAudioService.getInstance(this)
    }
    
    override fun onTerminate() {
        super.onTerminate()
        textToSpeechService.shutdown()
        locationService.stopMonitoringLocationChanges()
        quranAudioService.release()
    }
    
    companion object {
        lateinit var instance: DeenLearnApplication
            private set
    }
}

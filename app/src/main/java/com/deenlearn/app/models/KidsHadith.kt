package com.deenlearn.app.models

import kotlinx.serialization.Serializable

@Serializable
data class KidsHadith(
    val id: String,
    val emoji: String,
    val title: String,
    val arabicText: String,
    val simpleMeaning: String,
    val funFact: String,
    val collection: String,
    val hadithNumber: Int,
    val reference: String
)

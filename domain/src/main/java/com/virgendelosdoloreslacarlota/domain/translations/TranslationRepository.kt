package com.virgendelosdoloreslacarlota.domain.translations

interface TranslationRepository {
    suspend fun getTranslations()
    fun getTranslation(key: String): String
}
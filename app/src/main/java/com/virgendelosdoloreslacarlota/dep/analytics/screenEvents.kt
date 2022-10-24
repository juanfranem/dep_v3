package com.virgendelosdoloreslacarlota.dep.analytics

sealed class ScreenEvent(val screenName: String, val params: Map<String, String> = mapOf()) {
    object Home : ScreenEvent(HOME_KEY)
    object BurialList : ScreenEvent(BURIAL_LIST_KEY)
    object MassList : ScreenEvent(MASS_LIST_KEY)
    object NewsList : ScreenEvent(NEWS_LIST_KEY)
    object LegalList : ScreenEvent(LEGAL_LIST_KEY)
    object Settings : ScreenEvent(SETTINGS_KEY)

    data class BurialDetail(val slug: String) : ScreenEvent(
        BURIAL_DETAIL_KEY,
        mapOf(SLUG_KEY to slug)
    )

    data class MassDetail(val slug: String) : ScreenEvent(
        MASS_DETAIL_KEY,
        mapOf(SLUG_KEY to slug)
    )

    data class NewsDetail(val slug: String) : ScreenEvent(
        NEWS_DETAIL_KEY,
        mapOf(SLUG_KEY to slug)
    )

    data class LegalDetail(val slug: String) : ScreenEvent(
        LEGAL_DETAIL_KEY,
        mapOf(SLUG_KEY to slug)
    )
}

private const val SLUG_KEY = "slug"

private const val HOME_KEY = "home"
private const val SETTINGS_KEY = "settings"

private const val BURIAL_LIST_KEY = "burial_list"
private const val BURIAL_DETAIL_KEY = "burial_detail"

private const val MASS_LIST_KEY = "mass_list"
private const val MASS_DETAIL_KEY = "mass_detail"

private const val NEWS_LIST_KEY = "news_list"
private const val NEWS_DETAIL_KEY = "news_detail"

private const val LEGAL_LIST_KEY = "legal_list"
private const val LEGAL_DETAIL_KEY = "legal_detail"
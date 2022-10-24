package com.virgendelosdoloreslacarlota.dep.analytics

import android.provider.Telephony.Mms.Part.CONTENT_ID
import com.google.firebase.analytics.FirebaseAnalytics

sealed class UserEvents(val eventName: String, val params: Map<String, String> = mapOf()) {
    data class ViewAllTap(val type: ItemType) : UserEvents(
        VIEW_ALL_TAP_KEY,
        mapOf(ITEM_TYPE to type.name)
    )

    data class ItemTap(val slug: String?) : UserEvents(
        ITEM_TAP_KEY, mapOf(
            ITEM_SLUG to (slug ?: NOT_SET_SLUG)
        )
    )

    data class ShareTap(val slug: String) : UserEvents(
        SHARE_TAP_KEY, mapOf(
            FirebaseAnalytics.Param.ITEM_ID to slug,
        )
    )

    object NotificationsSettingsTap : UserEvents(NOTIFICATION_SETTINGS_TAP)
    object LegalListTap : UserEvents(LEGAL_LIST_TAP)
    object DownloadTap : UserEvents(DOWNLOAD_TAP_KEY)
    object SettingsTap : UserEvents(SETTINGS_TAP_KEY)
}

private const val NOT_SET_SLUG = "item_not_slug"

private const val ITEM_TYPE = "type"
private const val ITEM_SLUG = "slug"

private const val VIEW_ALL_TAP_KEY = "view_all_tap"

private const val LEGAL_LIST_TAP = "legal_list_tap"
private const val NOTIFICATION_SETTINGS_TAP = "notification_settings_tap"

private const val ITEM_TAP_KEY = "item_tap"

private const val SHARE_TAP_KEY = "share"
private const val DOWNLOAD_TAP_KEY = "download_tap"

private const val SETTINGS_TAP_KEY = "settings_tap"

enum class ItemType {
    BURIAL, MASS, NEWS
}
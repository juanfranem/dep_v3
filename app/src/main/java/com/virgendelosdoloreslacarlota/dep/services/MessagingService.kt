package com.virgendelosdoloreslacarlota.dep.services

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.virgendelosdoloreslacarlota.dep.usecase.SaveTokenUseCase
import com.virgendelosdoloreslacarlota.domain.token.TokenRepository
import com.virgendelosdoloreslacarlota.domain.token.TokenRequest
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MessagingService: FirebaseMessagingService() {

    @Inject
    lateinit var saveTokenRequest: SaveTokenUseCase

    override fun onNewToken(token: String) {
        Timber.d("FirebaseToken: $token")
        super.onNewToken(token)
        saveTokenRequest(TokenRequest(token))
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
    }
}
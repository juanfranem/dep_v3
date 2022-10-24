package com.virgendelosdoloreslacarlota.dep.services

import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.virgendelosdoloreslacarlota.dep.usecase.SaveTokenUseCase
import com.virgendelosdoloreslacarlota.domain.token.TokenRequest
import timber.log.Timber
import javax.inject.Inject

class FirebaseTokenService @Inject constructor(
    private val saveTokenUseCase: SaveTokenUseCase
) {
    private val firebaseMessage = FirebaseMessaging.getInstance()

    fun sendCurrentTokenToServer() {
        firebaseMessage.token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                task.exception?.printStackTrace()
                return@OnCompleteListener
            }
            val token = task.result
            Timber.d("FirebaseMessageToken: ${token}")
            saveTokenUseCase(TokenRequest(token))
        })
    }

}
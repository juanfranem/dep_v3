package com.virgendelosdoloreslacarlota.domain.token

interface TokenRepository {
    suspend fun saveToken(tokenRequest: TokenRequest)
}
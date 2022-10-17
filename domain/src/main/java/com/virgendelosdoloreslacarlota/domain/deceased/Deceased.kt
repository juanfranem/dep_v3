package com.virgendelosdoloreslacarlota.domain.deceased

data class Deceased(
    val name: String,
    val lastName: String,
    val subName: String?,
    val birthDate: String?,
    val deathDate: String,
    val from: String?,
    val description: String?
) {
    val fullName = "$name $lastName"
}
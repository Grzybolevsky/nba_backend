package com.example.security

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.sessions.*
import io.ktor.util.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.io.File

data class UserSession(val id: String, val count: Int) : Principal

@Serializable
data class UserInfo(
    val id: String,
    val name: String,
    @SerialName("given_name") val givenName: String,
    @SerialName("family_name") val familyName: String,
    val picture: String,
    val locale: String
)

fun Application.configureSessions() {
    install(Sessions) {
        val secretEncryptKey = System.getenv("ENCRYPT_KEY") ?: "00112233445566778899aabbccddeeff"
        val secretSignKey = System.getenv("SIGN_KEY") ?: "6819b57a326945c1968f45236589"
        cookie<UserSession>("user_session", directorySessionStorage(File("build/.sessions"))) {
            cookie.path = "/"
            cookie.maxAgeInSeconds = 3600
            cookie.httpOnly = false
            cookie.domain = ".herokuapp.com"
            transform(SessionTransportTransformerEncrypt(hex(secretEncryptKey), hex(secretSignKey)))
        }
    }
}

package com.example.services

import com.example.http.applicationHttpClient
import com.example.security.UserInfo
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking

object UserInfoService {

    private val httpClient: HttpClient = applicationHttpClient

    fun getUserInfo(sessionId: String): UserInfo {
        return runBlocking {
            httpClient.get("https://www.googleapis.com/oauth2/v2/userinfo") {
                headers {
                    append(HttpHeaders.Authorization, "Bearer $sessionId")
                }
            }.body()
        }
    }
}

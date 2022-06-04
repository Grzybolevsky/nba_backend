package com.example.security

import com.example.http.applicationHttpClient
import io.ktor.client.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.sessions.*

fun Application.configureAuthentication(httpClient: HttpClient = applicationHttpClient) {
    install(Authentication) {
        session<UserSession>("auth-session") {
            validate { it }
            challenge {
                call.respondRedirect("/")
            }
        }
        oauth("auth-oauth-google") {
            skipWhen { call -> call.sessions.get<UserSession>() != null }
            urlProvider = { "/api/callback" }
            providerLookup = {
                OAuthServerSettings.OAuth2ServerSettings(
                    name = "google",
                    authorizeUrl = "https://accounts.google.com/o/oauth2/auth",
                    accessTokenUrl = "https://accounts.google.com/o/oauth2/token",
                    requestMethod = HttpMethod.Post,
                    clientId = System.getenv("GOOGLE_CLIENT_ID"),
                    clientSecret = System.getenv("GOOGLE_CLIENT_SECRET"),
                    defaultScopes = listOf("https://www.googleapis.com/auth/userinfo.profile")
                )
            }
            client = httpClient
        }
    }
}

package com.example.security

import com.example.http.applicationHttpClient
import com.example.routes.RoutingUtils.API_URL
import com.example.routes.RoutingUtils.AUTH_URL
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
                call.respondRedirect("$API_URL$AUTH_URL")
            }
        }
        oauth("auth-oauth-google") {
            skipWhen { call -> call.sessions.get<UserSession>() != null }
            urlProvider = { "http://localhost:8080/api/callback" }
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

package com.example.security

import com.example.http.applicationHttpClient
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.html.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import kotlinx.html.a
import kotlinx.html.body
import kotlinx.html.p

fun Application.configureSecurityRoutes(httpClient: HttpClient = applicationHttpClient) {
    routing {
        get("/api/") {
            call.respondHtml {
                body {
                    p {
                        a("/login") { +"Login with Google" }
                    }
                }
            }
        }
        authenticate("auth-oauth-google") {
            get("/api/login") {
            }

            get("/api/callback") {
                val principal: OAuthAccessTokenResponse.OAuth2? = call.authentication.principal()
                call.sessions.set(UserSession(principal?.accessToken.toString(), 0))
                call.respondRedirect("/api/hello")
            }
        }

        get("/api/hello") {
            val userSession: UserSession? = call.sessions.get()
            if (userSession != null) {
                val userInfo: UserInfo = httpClient.get("https://www.googleapis.com/oauth2/v2/userinfo") {
                    headers {
                        append(HttpHeaders.Authorization, "Bearer ${userSession.id}")
                    }
                }.body()
                call.respondText("Hello, ${userInfo.name}!")
            } else {
                call.respondRedirect("/")
            }
        }

        get("/api/logout") {
            call.sessions.clear<UserSession>()
            call.respondRedirect("/api/login")
        }
    }
}

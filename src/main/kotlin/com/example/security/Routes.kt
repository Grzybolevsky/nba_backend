package com.example.security

import com.example.http.applicationHttpClient
import com.example.routes.RoutingUtils.API_URL
import com.example.routes.RoutingUtils.AUTH_URL
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

fun Route.configureSecurityRoutes(httpClient: HttpClient = applicationHttpClient) {
    get("") {
        call.respondHtml {
            body {
                p {
                    a("$API_URL$AUTH_URL") { +"Login with Google" }
                }
            }
        }
    }

    authenticate("auth-oauth-google") {
        get(AUTH_URL) {
            call.respondRedirect("https://nba-nginx.herokuapp.com/favorites")
        }

        get("/callback") {
            val principal: OAuthAccessTokenResponse.OAuth2? = call.authentication.principal()
            call.sessions.set(UserSession(principal?.accessToken.toString(), 0))
            call.respondRedirect("https://nba-nginx.herokuapp.com/favorites")
        }
    }

    get("/auth/hello") {
        val userSession: UserSession? = call.sessions.get()
        if (userSession != null) {
            val userInfo: UserInfo = httpClient.get("https://www.googleapis.com/oauth2/v2/userinfo") {
                headers {
                    append(HttpHeaders.Authorization, "Bearer ${userSession.id}")
                }
            }.body()
            call.respondText("Hello, ${userInfo.name}!")
        } else {
            call.respond(HttpStatusCode.Forbidden)
        }
    }

    get("/auth/info") {
        val userSession: UserSession? = call.sessions.get()
        if (userSession != null) {
            val userInfo: UserInfo = httpClient.get("https://www.googleapis.com/oauth2/v2/userinfo") {
                headers {
                    append(HttpHeaders.Authorization, "Bearer ${userSession.id}")
                }
            }.body()
            call.respond(userInfo)
        } else {
            call.respond(HttpStatusCode.Forbidden)
        }
    }

    get("/auth/check") {
        if (call.sessions.get<UserSession>() != null) {
            call.respond(HttpStatusCode.OK)
        } else {
            call.respond(HttpStatusCode.Forbidden)
        }
    }

    get("/auth/logout") {
        if (call.sessions.get<UserSession>() != null) {
            call.sessions.clear<UserSession>()
            call.respondRedirect("https://nba-nginx.herokuapp.com/favorites")
        } else {
            call.respond(HttpStatusCode.Forbidden)
        }
    }
}

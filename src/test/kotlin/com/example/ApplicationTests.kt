package com.example

import com.example.routes.configureRouting
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTests {
    @Test
    fun testRoot() = testApplication {
        application {
            configureRouting()
        }
        client.get("/api").apply {
            assertEquals(HttpStatusCode.OK, status)
        }
    }
}

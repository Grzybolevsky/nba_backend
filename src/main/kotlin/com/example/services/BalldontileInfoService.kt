package com.example.services

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.jsonArray
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL

val format = Json { prettyPrint = true }

class BalldontileInfoService {

    fun getDataFromBalldontile(urlString: String): JsonArray {
        val url = URL(urlString)
        val connection = url.openConnection()
        var dataString = ""
        BufferedReader(InputStreamReader(connection.getInputStream())).use { inp ->
            var line: String?
            while (inp.readLine().also { line = it } != null) {
                dataString += line
            }
        }
        return format.parseToJsonElement(dataString).jsonArray
    }
}

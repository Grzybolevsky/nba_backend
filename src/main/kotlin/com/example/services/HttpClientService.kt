package com.example.services

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL

object HttpClientService {

    fun getFromUrl(urlString: String): String {
        val url = URL(urlString)
        val connection = url.openConnection()
        var data = ""
        BufferedReader(InputStreamReader(connection.getInputStream())).use { inp ->
            var line: String?
            while (inp.readLine().also { line = it } != null) {
                data += line
            }
        }

        return data
    }
}

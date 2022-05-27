package com.example

import com.example.dao.DatabaseFactory
import com.example.services.balldontile.BalldontileInfoService.fetchData

fun main() {
    DatabaseFactory.init()
    fetchData()
}

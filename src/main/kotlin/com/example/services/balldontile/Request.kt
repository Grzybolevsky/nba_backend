package com.example.services.balldontile

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.JsonNames

@OptIn(ExperimentalSerializationApi::class)
@kotlinx.serialization.Serializable
data class Metadata(
    @JsonNames("total_pages") val totalPages: Int,
    @JsonNames("current_page") val currentPage: Int,
    @JsonNames("next_page") val nextPage: Int?,
    @JsonNames("per_page") val perPage: Int,
    @JsonNames("total_count") val totalCount: Int
)

@kotlinx.serialization.Serializable
data class RequestData<T>(
    val data: T,
    val meta: Metadata
)

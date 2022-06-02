package com.example.services.balldontile

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Metadata(
    @SerialName("total_pages") val totalPages: Int,
    @SerialName("current_page") val currentPage: Int,
    @SerialName("next_page") val nextPage: Int?,
    @SerialName("per_page") val perPage: Int,
    @SerialName("total_count") val totalCount: Int
)

@Serializable
data class RequestData<T>(
    val data: T,
    val meta: Metadata
)

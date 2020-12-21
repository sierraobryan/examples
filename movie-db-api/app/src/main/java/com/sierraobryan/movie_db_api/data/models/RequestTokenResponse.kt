package com.sierraobryan.movie_db_api.data.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RequestTokenResponse(
    @Json(name = "expires_at") val expires: String,
    @Json(name = "request_token") val requestToken: String
)

@JsonClass(generateAdapter = true)
data class CreateSessionBody(
    @Json(name = "request_token") val authorizedRequestToken: String
)

@JsonClass(generateAdapter = true)
data class CreateSessionResponse(
    @Json(name = "session_id") val sessionId: String
)
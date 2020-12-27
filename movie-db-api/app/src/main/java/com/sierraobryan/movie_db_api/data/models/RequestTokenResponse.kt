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

@JsonClass(generateAdapter = true)
data class RateMovieBody(
    @Json(name = "value") val movieRating: Double
)

@JsonClass(generateAdapter = true)
data class PostEndpointResponse(
        @Json(name = "success") val success: Boolean,
        @Json(name = "status_code") val code: Int = -1,
        @Json(name = "status_message") val message: String = ""
)
package com.sierraobryan.movie_db_api.network

import com.sierraobryan.movie_db_api.data.models.*
import retrofit2.http.*

interface MovieDbAuthenticationApi {

    @GET("authentication/token/new")
    suspend fun getRequestToken(): RequestTokenResponse

    @POST("authentication/session/new")
    suspend fun createSessionId(@Body body: CreateSessionBody): CreateSessionResponse

    @HTTP(method = "DELETE", path = "authentication/session", hasBody = true)
    suspend fun deleteSession(@Body body: CreateSessionResponse): PostEndpointResponse

    @POST("movie/{movie_id}/rating")
    suspend fun rateMovie(
            @Path("movie_id") movieId: Int = 726208,
            @Query("session_id") sessionId: String,
            @Body body: RateMovieBody
    ): PostEndpointResponse

}

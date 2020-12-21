package com.sierraobryan.movie_db_api.network

import com.sierraobryan.movie_db_api.data.models.CreateSessionBody
import com.sierraobryan.movie_db_api.data.models.CreateSessionResponse
import com.sierraobryan.movie_db_api.data.models.RequestTokenResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface MovieDbAuthenticationApi {

    @GET("3/authentication/token/new")
    suspend fun getRequestToken(
        @Query("api_key") apiKey: String = ""
    ): RequestTokenResponse

    @POST("3/authentication/session/new")
    suspend fun createSessionId(
        @Query("api_key") apiKey: String = "",
        @Body body: CreateSessionBody
    ): CreateSessionResponse

}

package com.sierraobryan.movie_db_api.data

import com.sierraobryan.movie_db_api.data.models.CreateSessionBody
import com.sierraobryan.movie_db_api.network.MovieDbAuthenticationApi
import javax.inject.Inject

class movieDbRepository @Inject constructor(
    private val movieDbAuthenticationApi: MovieDbAuthenticationApi
) {

    suspend fun getRequestToken() = movieDbAuthenticationApi.getRequestToken()

    suspend fun createSessionId(
        authenticatedRequestToken: String
    ) = movieDbAuthenticationApi.createSessionId(
        body = CreateSessionBody(
            authorizedRequestToken = authenticatedRequestToken
        )
    )
}
package com.sierraobryan.movie_db_api.data

import com.sierraobryan.movie_db_api.data.models.CreateSessionBody
import com.sierraobryan.movie_db_api.data.models.CreateSessionResponse
import com.sierraobryan.movie_db_api.data.models.PostEndpointResponse
import com.sierraobryan.movie_db_api.data.models.RateMovieBody
import com.sierraobryan.movie_db_api.network.MovieDbAuthenticationApi
import javax.inject.Inject

class MovieDbAuthRepository @Inject constructor(
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

    suspend fun deleteSession(
        sessionId: String?
    ) = movieDbAuthenticationApi.deleteSession(
                body = CreateSessionResponse(
                    sessionId = sessionId ?: ""
                )
            )

    suspend fun rateMovie(
            sessionId: String,
            movieRating: Double
    ) = movieDbAuthenticationApi.rateMovie(
            sessionId = sessionId,
            body = RateMovieBody(
                    movieRating = movieRating
            )
    )
}
package com.sierraobryan.wwcode_accessibility.network

import com.sierraobryan.wwcode_accessibility.data.models.Commit
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubService {

    @GET("repos/{user}/{repository}/commits")
    suspend fun listCommits(
        @Path("user") user: String,
        @Path("repository") repository: String): List<Commit>

}
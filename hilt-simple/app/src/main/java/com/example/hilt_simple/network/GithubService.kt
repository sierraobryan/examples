package com.example.hilt_simple.network

import com.example.hilt_simple.data.models.Commit
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubService {

    @GET("repos/{user}/{repository}/commits")
    suspend fun listCommits(
        @Path("user") user: String,
        @Path("repository") repository: String): List<Commit>

}
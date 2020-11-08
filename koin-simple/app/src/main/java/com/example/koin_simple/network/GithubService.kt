package com.example.koin_simple.network

import com.example.koin_simple.data.models.Commit
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

    /**
     * Companion object to create the AmazonApi
     */
    companion object Factory {
        fun create(url: String): GithubService {
            val client = OkHttpClient.Builder()
                .build()
            val retrofit = Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create())
                .client(client)
                .baseUrl(url)
                .build()

            return retrofit.create(GithubService::class.java)
        }
    }

}
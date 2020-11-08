package com.example.hilt_simple.data

import com.example.hilt_simple.data.models.Commit
import com.example.hilt_simple.network.GithubService
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val githubService: GithubService
) {

    suspend fun listCommits(username: String, repoName: String): List<Commit> {
        return githubService.listCommits(username, repoName)
    }

}
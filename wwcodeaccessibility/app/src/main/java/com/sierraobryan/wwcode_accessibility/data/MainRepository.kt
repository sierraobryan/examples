package com.sierraobryan.wwcode_accessibility.data

import com.sierraobryan.wwcode_accessibility.data.models.Commit
import com.sierraobryan.wwcode_accessibility.network.GithubService
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val githubService: GithubService
) {

    suspend fun listCommits(username: String, repoName: String): List<Commit> {
        return githubService.listCommits(username, repoName)
    }

}

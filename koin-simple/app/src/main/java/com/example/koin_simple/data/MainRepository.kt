package com.example.koin_simple.data

import androidx.lifecycle.MutableLiveData
import com.example.koin_simple.data.models.Commit
import com.example.koin_simple.network.GithubService

class MainRepository(private val githubService: GithubService) {

    val allowNetworkCall = MutableLiveData(true)

    suspend fun listCommits(username: String, repoName: String): List<Commit> {
        return githubService.listCommits(username, repoName)
    }
}
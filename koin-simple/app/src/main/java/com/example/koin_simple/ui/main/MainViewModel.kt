package com.example.koin_simple.ui.main

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.koin_simple.data.MainRepository
import com.example.koin_simple.data.models.Commit
import com.example.koin_simple.network.LogService
import kotlinx.coroutines.launch

class MainViewModel(
    private val mainRepository: MainRepository,
    private val logService: LogService
) : ViewModel() {

    val username: MutableLiveData<String> = MutableLiveData("sierraobryan")
    val repoName: MutableLiveData<String> = MutableLiveData("hackerNews")
    val isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val commits: MutableLiveData<List<Commit>> = MutableLiveData()

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val isError: MutableLiveData<Boolean> = MutableLiveData(false)

    val fetchCommitsEnabled = MediatorLiveData<Boolean>().apply {
        addSource(username) {
            postValue(validateRepo() && validateUserName())
        }
        addSource(repoName) {
            postValue(validateRepo() && validateUserName())
        }
    }

    fun listCommits() {
        logService.logNetworkAttempt()
        viewModelScope.launch {
            isLoading.postValue(true)
            if (mainRepository.allowNetworkCall.value == true) {
                val result = mainRepository.listCommits(
                        username.value?.trim()!!,
                        repoName.value?.trim()!!
                )
                logService.logSuccess()
                isLoading.postValue(false)
                commits.postValue(result)
            } else {
                logService.logError()
                isLoading.postValue(false)
                isError.postValue(true)
            }
        }
    }

    private fun validateUserName() = username.value?.isNotBlank() == true

    private fun validateRepo() = repoName.value?.isNotBlank() == true

}
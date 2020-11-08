package com.example.hilt_simple.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hilt_simple.data.MainRepository
import com.example.hilt_simple.data.models.Commit
import kotlinx.coroutines.launch


class MainViewModel @ViewModelInject constructor(private val mainRepository: MainRepository) : ViewModel() {

    val username: MutableLiveData<String> = MutableLiveData("sierraobryan")
    val repoName: MutableLiveData<String> = MutableLiveData("hackerNews")
    val isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val commits: MutableLiveData<List<Commit>> = MutableLiveData()

    val fetchCommitsEnabled = MediatorLiveData<Boolean>().apply {
        addSource(username) {
            postValue(validateRepo() && validateUserName())
        }
        addSource(repoName) {
            postValue(validateRepo() && validateUserName())
        }
    }

    fun listCommits() {
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = mainRepository.listCommits(
                    username.value?.trim()!!,
                    repoName.value?.trim()!!
            )
            isLoading.postValue(false)
            commits.postValue(result)
        }
    }

    private fun validateUserName() = username.value?.isNotBlank() == true

    private fun validateRepo() = repoName.value?.isNotBlank() == true

}
package com.sierraobryan.wwcode_accessibility.ui.main

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sierraobryan.wwcode_accessibility.data.MainRepository
import com.sierraobryan.wwcode_accessibility.data.models.Commit
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val mainRepository: MainRepository) : ViewModel() {

    val username: MutableLiveData<String> = MutableLiveData("sierraobryan")
    val repoName: MutableLiveData<String> = MutableLiveData("hackerNews")
    val isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val commits: MutableLiveData<List<Commit>> = MutableLiveData()

    val fetchCommitsEnabled = MediatorLiveData<Boolean>().apply {
        addSource(username) {
            postValue(validateRepo() && validateUserName() && isLoading.value != true)
        }
        addSource(repoName) {
            postValue(validateRepo() && validateUserName() && isLoading.value != true)
        }
        addSource(isLoading) {
            postValue(validateRepo() && validateUserName() && isLoading.value != true)
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

    fun clearFields() {
        username.postValue("")
        repoName.postValue("")
    }

    private fun validateUserName() = username.value?.isNotBlank() == true

    private fun validateRepo() = repoName.value?.isNotBlank() == true

}
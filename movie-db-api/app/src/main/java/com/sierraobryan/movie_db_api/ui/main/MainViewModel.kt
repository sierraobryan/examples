package com.sierraobryan.movie_db_api.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sierraobryan.movie_db_api.data.movieDbRepository
import kotlinx.coroutines.launch
import java.lang.Exception

class MainViewModel @ViewModelInject constructor(
        private val movieDbRepository: movieDbRepository
) : ViewModel() {

    val requestToken = MutableLiveData<String>()
    val sessionId = MutableLiveData<String>()

    fun getRequestToken() {
        viewModelScope.launch {
            try {
                val requestTokenResponse = movieDbRepository.getRequestToken()
                requestToken.postValue(requestTokenResponse.requestToken)
            } catch (e: Exception) {
            }
        }
    }

    fun createSessionId() {
        viewModelScope.launch {
            try {
                val sessionIdResponse = movieDbRepository.createSessionId(
                        requestToken.value!!.trim()
                )
                // TODO save session id
                sessionId.postValue(sessionIdResponse.sessionId)
            } catch (e: Exception) {
            }
        }
    }

}
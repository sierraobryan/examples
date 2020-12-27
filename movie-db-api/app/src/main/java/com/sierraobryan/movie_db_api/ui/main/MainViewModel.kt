package com.sierraobryan.movie_db_api.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.sierraobryan.movie_db_api.data.MovieDbAuthRepository
import com.sierraobryan.movie_db_api.data.MovieDbPreferencesStore
import kotlinx.coroutines.launch
import java.lang.Exception

class MainViewModel @ViewModelInject constructor(
        private val movieDbAuthRepository: MovieDbAuthRepository,
        private val movieDbPreferencesStore: MovieDbPreferencesStore
) : ViewModel() {

    val requestToken = movieDbPreferencesStore.requestTokenFlow.asLiveData()
    val sessionId = movieDbPreferencesStore.sessionIdFlow.asLiveData()

    val rateMovieResponseMessage = MutableLiveData<String>()

    var shouldImmediatelyNavigateToWeb = false

    fun getRequestToken() {
        shouldImmediatelyNavigateToWeb = true
        viewModelScope.launch {
            try {
                val requestTokenResponse = movieDbAuthRepository.getRequestToken()
                movieDbPreferencesStore.saveRequestToken(requestTokenResponse)
            } catch (e: Exception) {
            }
        }
    }

    fun createSessionId(requestToken: String = this.requestToken.value!!.requestToken.trim()) {
        viewModelScope.launch {
            try {
                val sessionIdResponse = movieDbAuthRepository.createSessionId(requestToken)
                movieDbPreferencesStore.saveSessionId(sessionIdResponse.sessionId)
            } catch (e: Exception) {
            }
        }
    }

    fun rateMovie() {
        viewModelScope.launch {
            try {
                val response = movieDbAuthRepository.rateMovie(
                        sessionId = sessionId.value!!.trim(),
                        movieRating = 8.5
                )
                rateMovieResponseMessage.postValue(response.message)
            } catch (e: Exception) {

            }
        }
    }
}
package com.sierraobryan.movie_db_api.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sierraobryan.movie_db_api.data.MovieDbAuthRepository
import com.sierraobryan.movie_db_api.data.MovieDbPreferencesStore
import kotlinx.coroutines.launch
import java.lang.Exception

class MainViewModel @ViewModelInject constructor(
        private val movieDbAuthRepository: MovieDbAuthRepository,
        private val movieDbPreferencesStore: MovieDbPreferencesStore
) : ViewModel() {

    val requestToken = MutableLiveData<String>()
    val sessionId = MutableLiveData<String>()
    val rateMovieResponseMessage = MutableLiveData<String>()

    fun getRequestToken() {
        viewModelScope.launch {
            try {
                val requestTokenResponse = movieDbAuthRepository.getRequestToken()
                requestToken.postValue(requestTokenResponse.requestToken)
            } catch (e: Exception) {
            }
        }
    }

    fun createSessionId(requestToken: String = this.requestToken.value!!.trim()) {
        viewModelScope.launch {
            try {
                val sessionIdResponse = movieDbAuthRepository.createSessionId(requestToken)
                movieDbPreferencesStore.saveSessionId(sessionIdResponse.sessionId)
                sessionId.postValue(sessionIdResponse.sessionId)
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
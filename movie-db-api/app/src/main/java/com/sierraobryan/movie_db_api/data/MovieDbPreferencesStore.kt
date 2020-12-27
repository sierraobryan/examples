package com.sierraobryan.movie_db_api.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.core.remove
import com.google.gson.Gson
import com.sierraobryan.movie_db_api.data.models.RequestTokenResponse
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class MovieDbPreferencesStore @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    private val datePattern = "yyyy-MM-dd HH:mm:ss z"

    private val SESSION_ID = preferencesKey<String>("session_id")
    private val REQUEST_TOKEN = preferencesKey<String>("request_token")

    val sessionIdFlow = dataStore.data.map { currentPrefs ->
        currentPrefs[SESSION_ID]
    }

    val requestTokenFlow = dataStore.data.map { prefs ->
        var requestToken = Gson().fromJson(prefs[REQUEST_TOKEN], RequestTokenResponse::class.java)
        if (requestToken != null && requestTokenHasExpired(requestToken.expires)) {
            removeRequestToken()
            requestToken = null
        }
        requestToken
    }

    suspend fun saveSessionId(sessionId: String) {
        dataStore.edit { prefs ->
            prefs[SESSION_ID] = sessionId
            prefs.remove(REQUEST_TOKEN)
        }
    }

    suspend fun saveRequestToken(requestTokenResponse: RequestTokenResponse) {
        dataStore.edit { prefs ->
            prefs[REQUEST_TOKEN] = Gson().toJson(requestTokenResponse)
        }
    }

    private suspend fun removeRequestToken() {
        dataStore.edit { prefs ->
            prefs.remove(REQUEST_TOKEN)
        }
    }

    private fun requestTokenHasExpired(dateString: String): Boolean {
        val formatter = SimpleDateFormat(datePattern, Locale.getDefault())
        val date = formatter.parse(dateString)
        val currentDate = Date(System.currentTimeMillis())
        return date?.before(currentDate) ?: true
    }
}
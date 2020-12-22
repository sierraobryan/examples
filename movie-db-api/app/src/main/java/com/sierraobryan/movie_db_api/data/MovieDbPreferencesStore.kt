package com.sierraobryan.movie_db_api.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieDbPreferencesStore @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    private val SESSION_ID = preferencesKey<String>("session_id")

    val sessionIdFlow = dataStore.data.map { currentPrefs ->
        currentPrefs[SESSION_ID]
    }

    suspend fun saveSessionId(sessionId: String) {
        dataStore.edit { prefs ->
            prefs[SESSION_ID] = sessionId
        }
    }


}
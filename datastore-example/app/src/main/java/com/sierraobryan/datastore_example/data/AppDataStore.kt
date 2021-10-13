package com.sierraobryan.datastore_example.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sierraobryan.datastore_example.data.models.Member
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AppDataStore @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    private object PreferencesKeys {
        // this can only be Int, Long, Boolean, Float, Double, String
        val MEMBER = stringPreferencesKey("member")

        // only strings are currently supported
        val MEMBERS = stringPreferencesKey("members")
    }

    val myMemberFlow: Flow<Member?> = dataStore.data
        .map { currentPreferences ->
            // there's no type safety here.
            try {
                Gson().fromJson(currentPreferences[PreferencesKeys.MEMBER], Member::class.java)
            } catch (e: Exception) {
                null
            }
        }

    suspend fun saveMember(member: Member) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.MEMBER] = Gson().toJson(member)
        }
    }

    suspend fun removeMember() {
        dataStore.edit { preferences ->
            preferences.remove(PreferencesKeys.MEMBER)
        }
    }

    val myMembersFlow: Flow<List<Member>?> = dataStore.data
        .map { currentPreferences ->
            val listType = object : TypeToken<List<Member>>() {}.type
            Gson().fromJson(currentPreferences[PreferencesKeys.MEMBERS], listType)
        }
}
package com.sierraobryan.datastore_example.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import com.google.gson.Gson
import com.sierraobryan.datastore_example.data.models.Member
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AppDataStore @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    // this can only be Int, Long, Boolean, Float, Double, String
    private val MEMBER = preferencesKey<String>("member")

    // only strings are currently supported
    private val MEMBERS = preferencesSetKey<String>("members")

    val myMemberFlow: Flow<Member?> = dataStore.data
        .map { currentPreferences ->
            // there's no type safety here.
            try {
                Gson().fromJson(currentPreferences[MEMBER], Member::class.java)
            } catch (e: Exception) {
                null
            }
        }

    suspend fun saveMember(member: Member) {
        dataStore.edit { preferences ->
            preferences[MEMBER] = Gson().toJson(member)
        }
    }

    suspend fun removeMember() {
        dataStore.edit { preferences ->
            preferences.remove(MEMBER)
        }
    }

    val myMembersFlow: Flow<List<Member>?> = dataStore.data
        .map { currentPreferences ->
            currentPreferences[MEMBERS]?.map {
                Gson().fromJson(it, Member::class.java)
            }
        }
}
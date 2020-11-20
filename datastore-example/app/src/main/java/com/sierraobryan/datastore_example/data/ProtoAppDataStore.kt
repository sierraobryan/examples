package com.sierraobryan.datastore_example.data

import android.util.Log
import androidx.datastore.DataStore
import com.google.gson.Gson
import com.sierraobryan.datastore_example.MemberPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class ProtoAppDataStore @Inject constructor(
    private val protoAppDataStore: DataStore<MemberPreferences>
) {

    val myMemberPreferencesFlow: Flow<MemberPreferences> = protoAppDataStore.data
        .catch { exception ->
            // dataStore.data throws an IOException when an error is encountered when reading data
            if (exception is IOException) {
                Log.wtf("Yikes", "Error reading sort order preferences.", exception)
                emit(MemberPreferences.getDefaultInstance())
            } else {
                throw exception
            }
        }

    suspend fun saveMember(member: MemberPreferences.Member) {
        protoAppDataStore.updateData { current ->
            current.toBuilder().setMember(member).build()
        }
    }

    suspend fun removeMember() {
        protoAppDataStore.updateData { current ->
            current.toBuilder().clearMember().build()
        }
    }


}
package com.sierraobryan.datastore_example.data

import androidx.datastore.DataStore
import com.google.gson.Gson
import com.sierraobryan.datastore_example.MemberPreferences
import com.sierraobryan.datastore_example.data.models.Member
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProtoAppDataStore @Inject constructor(
    private val protoAppDataStore: DataStore<MemberPreferences>
) {

    val myMemberFlow: Flow<Member?> = protoAppDataStore.data
        .map { memberPreferences ->
            // The myCounter property is generated for you from your proto schema!
            try {
                Gson().fromJson(memberPreferences.member, Member::class.java)
            } catch (e: Exception) {
                null
            }
        }

    suspend fun saveMember(member: Member) {
        protoAppDataStore.updateData { current ->
            current.toBuilder().setMember(
                Gson().toJson(member)
            ).build()
        }
    }

    suspend fun removeMember() {
        protoAppDataStore.updateData { current ->
            current.toBuilder().setMember(
                Gson().toJson("")
            ).build()
        }
    }


}
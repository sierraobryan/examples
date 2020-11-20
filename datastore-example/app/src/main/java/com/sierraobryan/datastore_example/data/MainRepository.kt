package com.sierraobryan.datastore_example.data

import com.sierraobryan.datastore_example.MemberPreferences
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val protoAppDataStore: ProtoAppDataStore
) {

    val memberPrefFlow = protoAppDataStore.myMemberPreferencesFlow

    suspend fun addMember(member: MemberPreferences.Member) {
        protoAppDataStore.saveMember(member)
    }

    suspend fun removeMember() {
        protoAppDataStore.removeMember()
    }

}
package com.sierraobryan.datastore_example.data

import com.sierraobryan.datastore_example.data.models.Member
import com.sierraobryan.datastore_example.data.models.MemberRole
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val protoAppDataStore: ProtoAppDataStore
) {

    val member = protoAppDataStore.myMemberFlow

    suspend fun addMember(member: Member) {
        protoAppDataStore.saveMember(member)
    }

    suspend fun removeMember() {
        protoAppDataStore.removeMember()
    }

}
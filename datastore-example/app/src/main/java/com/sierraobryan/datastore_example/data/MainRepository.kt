package com.sierraobryan.datastore_example.data

import com.sierraobryan.datastore_example.data.models.Member
import com.sierraobryan.datastore_example.data.models.MemberRole
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val appDataStore: AppDataStore
) {

    val member = appDataStore.myMemberFlow

    suspend fun addMember(member: Member) {
        appDataStore.saveMember(member)
    }

    suspend fun removeMember() {
        appDataStore.removeMember()
    }

}
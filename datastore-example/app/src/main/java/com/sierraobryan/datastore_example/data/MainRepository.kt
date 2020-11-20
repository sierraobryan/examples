package com.sierraobryan.datastore_example.data

import com.sierraobryan.datastore_example.data.models.Member
import com.sierraobryan.datastore_example.data.models.MemberRole
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val appSharedPreferences: AppSharedPreferences
) {

    val member = appSharedPreferences.member

    fun addMember(member: Member) {
        appSharedPreferences.saveMember(member)
    }

    fun removeMember() {
        appSharedPreferences.removeMember()
    }

}
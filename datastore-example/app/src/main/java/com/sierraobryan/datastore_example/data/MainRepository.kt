package com.sierraobryan.datastore_example.data

import com.sierraobryan.datastore_example.MemberPreferences
import com.sierraobryan.datastore_example.data.models.Member
import com.sierraobryan.datastore_example.data.models.MemberRole
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val protoAppDataStore: ProtoAppDataStore
) {

    val memberFlow = protoAppDataStore.myMemberFlow.map {
        mapMemberPrefToMember(it)
    }

    suspend fun addMember(member: Member) {
        protoAppDataStore.saveMember(mapMemberToMemberPref(member))
    }

    suspend fun removeMember() {
        protoAppDataStore.removeMember()
    }

    private fun mapMemberPrefToMember(member: MemberPreferences.Member): Member {
        return Member(
            member.name,
            MemberRole.valueOf(member.role.toString()),
            member.techsList.toList()
        )
    }
    private fun mapMemberToMemberPref(member: Member): MemberPreferences.Member {
        return MemberPreferences.Member.newBuilder()
            .setName(member.name)
            .setRole(MemberPreferences.MemberRole.valueOf(member.role.toString()))
            .addAllTechs(member.techs)
            .build()
    }

}
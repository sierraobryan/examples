package com.sierraobryan.datastore_example.data.models

data class Member(
    val name: String,
    val role: MemberRole,
    val techs: List<String>
)

enum class MemberRole {
    MEMBER,
    VOLUNTEER,
    EVANGELIST,
    LEAD,
    DIRECTOR
}
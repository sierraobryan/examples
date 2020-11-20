package com.sierraobryan.datastore_example.data

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.sierraobryan.datastore_example.data.models.Member
import com.sierraobryan.datastore_example.data.models.MemberRole
import javax.inject.Inject

class AppSharedPreferences @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {

    val member = MutableLiveData<Member?>()

    private var savedMember = sharedPreferences.getString(MEMBER, "")

//    init {
//        saveMember(
//            Member(
//                name = "Sierra",
//                role = MemberRole.VOLUNTEER,
//                techs = listOf("Android", "kotlin")
//            )
//        )
//    }

    fun saveMember(member: Member?) {
        this.savedMember = Gson().toJson(member)
        sharedPreferences.edit().putString(MEMBER, this.savedMember).apply()
        this.member.postValue(getSavedMember())
    }

    fun removeMember() {
        saveMember(null)
    }

    private fun getSavedMember(): Member? {
        return try {
            Gson().fromJson(savedMember, Member::class.java)
        } catch (e: Exception) {
            null
        }
    }

    companion object {
        const val MEMBER = "member"
    }




}
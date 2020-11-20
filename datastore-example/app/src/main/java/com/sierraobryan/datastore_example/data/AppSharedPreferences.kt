package com.sierraobryan.datastore_example.data

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.sierraobryan.datastore_example.data.models.Member
import com.sierraobryan.datastore_example.data.models.MemberRole
import com.sierraobryan.datastore_example.util.Constants
import javax.inject.Inject

class AppSharedPreferences @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {

    val member = MutableLiveData<Member?>()

    private var savedMember = sharedPreferences.getString(Constants.MEMBER_KEY, "")

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
        sharedPreferences.edit().putString(Constants.MEMBER_KEY, this.savedMember).apply()
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
}
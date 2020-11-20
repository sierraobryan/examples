package com.sierraobryan.datastore_example.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.sierraobryan.datastore_example.data.MainRepository
import com.sierraobryan.datastore_example.data.models.Member
import com.sierraobryan.datastore_example.data.models.MemberRole

class MainViewModel @ViewModelInject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    val member = mainRepository.member

    fun onAddClick() {
        mainRepository.addMember(
            Member(
                name = "Sierra",
                role = MemberRole.VOLUNTEER,
                techs = listOf("Android", "kotlin")
            )
        )
    }

    fun onRemoveClick() {
        mainRepository.removeMember()
    }

}
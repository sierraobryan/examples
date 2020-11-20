package com.sierraobryan.datastore_example.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.sierraobryan.datastore_example.MemberPreferences
import com.sierraobryan.datastore_example.data.MainRepository
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    val member = mainRepository.memberPrefFlow.map { it.member }.asLiveData()

    fun onAddClick() {
        viewModelScope.launch {
            mainRepository.addMember(
                MemberPreferences.Member.newBuilder()
                    .setName("Sierra")
                    .setRole(MemberPreferences.MemberRole.VOLUNTEER)
                    .addAllTechs(listOf("Android", "Kotlin"))
                    .build()
            )
        }
    }

    fun onRemoveClick() {
        viewModelScope.launch {
            mainRepository.removeMember()
        }
    }

}
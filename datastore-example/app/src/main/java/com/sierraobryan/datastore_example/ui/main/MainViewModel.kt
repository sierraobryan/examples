package com.sierraobryan.datastore_example.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.sierraobryan.datastore_example.data.MainRepository
import com.sierraobryan.datastore_example.data.models.Member
import com.sierraobryan.datastore_example.data.models.MemberRole
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    val member = mainRepository.memberFlow.asLiveData()

    fun onAddClick() {
        viewModelScope.launch {
            mainRepository.addMember(
                Member(
                    "Sierra",
                    MemberRole.VOLUNTEER,
                    listOf("Android", "Kotlin")
                )
            )
        }
    }

    fun onRemoveClick() {
        viewModelScope.launch {
            mainRepository.removeMember()
        }
    }

}
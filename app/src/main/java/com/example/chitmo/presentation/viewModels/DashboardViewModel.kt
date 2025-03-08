package com.example.chitmo.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chitmo.data.local.entities.Chit
import com.example.chitmo.data.local.entities.Participant
import com.example.chitmo.data.local.repositories.ChitDBRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val chitDBRepository: ChitDBRepository
) : ViewModel() {
    val chitListMutableStateFlow: MutableStateFlow<List<Chit>> = MutableStateFlow(emptyList())
    val participantListMutableStateFlow: MutableStateFlow<List<Participant>> = MutableStateFlow(emptyList())

    fun getAllChits() {
        try {
            viewModelScope.launch {
                chitListMutableStateFlow.value = chitDBRepository.getAllChits()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getAllParticipants() {
        try {
            viewModelScope.launch {
                participantListMutableStateFlow.value = chitDBRepository.getAllParticipants()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
package com.example.chitmo.domain.models

data class ChitParticipantData(
    val participantId: Long,
    val isBid: Boolean,
    val bidMonthYear: String?,
    val chitParticipantMonthlyDataList: List<ChitParticipantMonthlyData>
)

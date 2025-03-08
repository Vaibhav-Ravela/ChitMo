package com.example.chitmo.domain.models

data class ChitData(
    val startMonthYear: String, // Format: MM-YYYY
    val durationInMonths: Int,
    val amount: Int,
    val chitParticipantList: List<ChitParticipantData>,
)
package com.example.chitmo.domain.models

data class ChitParticipantMonthlyData(
    val monthYear: String, // Format: MM-YYYY
    val status: PaymentStatus, // Example: "Paid", "Pending", "Overdue"
)

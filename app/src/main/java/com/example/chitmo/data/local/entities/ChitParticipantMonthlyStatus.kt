package com.example.chitmo.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.example.chitmo.domain.models.PaymentStatus

@Entity(
    primaryKeys = ["chitId", "participantId", "monthYear"],
    foreignKeys = [ForeignKey(
        entity = Chit::class,
        parentColumns = ["chitId"],
        childColumns = ["chitId"]
    ), ForeignKey(
        entity = Participant::class,
        parentColumns = ["participantId"],
        childColumns = ["participantId"]
    )],
    indices = [Index(value = ["chitId"]), Index(value = ["participantId"]), Index(value = ["monthYear"])]
)
data class ChitParticipantMonthlyStatus(
    var chitId: Long, var participantId: Long, var monthYear: String, // Format: MM-YYYY
    var status: PaymentStatus, // Example: "Paid", "Pending", "Overdue"
)
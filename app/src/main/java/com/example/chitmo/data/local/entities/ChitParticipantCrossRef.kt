package com.example.chitmo.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    primaryKeys = ["chitId", "participantId", "isBid"],
    foreignKeys = [ForeignKey(
        entity = Chit::class, parentColumns = ["chitId"], childColumns = ["chitId"]
    ), ForeignKey(
        entity = Participant::class,
        parentColumns = ["participantId"],
        childColumns = ["participantId"]
    )],
    indices = [Index(value = ["chitId"]), Index(value = ["participantId"]), Index(value = ["isBid"]), Index(
        value = ["bidMonthYear"]
    )]
)
data class ChitParticipantCrossRef(
    var chitId: Long, var participantId: Long, var isBid: Boolean, var bidMonthYear: String?
)
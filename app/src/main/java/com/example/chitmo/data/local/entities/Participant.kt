package com.example.chitmo.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Participant(
    @PrimaryKey(autoGenerate = true) var participantId: Long = 0,
    var name: String,
    var phoneNumber: String
)

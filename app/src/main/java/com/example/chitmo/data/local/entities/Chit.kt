package com.example.chitmo.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Chit(
    @PrimaryKey(autoGenerate = true) var chitId: Long = 0,
    var startMonthYear: String, // Format: YYYY-MM
    var durationInMonths: Int,
    var amount: Int,
)
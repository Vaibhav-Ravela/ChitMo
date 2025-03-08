package com.example.chitmo.data.local.convertors

import androidx.room.TypeConverter
import com.example.chitmo.domain.models.PaymentStatus

class Converters {
    @TypeConverter
    fun fromPaymentStatus(status: PaymentStatus): String {
        return status.name // Convert Enum to String
    }

    @TypeConverter
    fun toPaymentStatus(status: String): PaymentStatus {
        return PaymentStatus.valueOf(status) // Convert String back to Enum
    }
}
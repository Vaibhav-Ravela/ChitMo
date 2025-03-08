package com.example.chitmo.data.local.databases

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.chitmo.data.local.convertors.Converters
import com.example.chitmo.data.local.daos.ChitMoDao
import com.example.chitmo.data.local.entities.Chit
import com.example.chitmo.data.local.entities.ChitParticipantCrossRef
import com.example.chitmo.data.local.entities.ChitParticipantMonthlyStatus
import com.example.chitmo.data.local.entities.Participant

@Database(
    entities = [Chit::class, Participant::class, ChitParticipantCrossRef::class, ChitParticipantMonthlyStatus::class],
    exportSchema = false,
    version = 1
)
@TypeConverters(Converters::class)
abstract class ChitMoDatabase : RoomDatabase() {
    abstract fun getChitMoDao(): ChitMoDao
}
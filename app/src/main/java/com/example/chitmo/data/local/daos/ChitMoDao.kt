package com.example.chitmo.data.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.chitmo.data.local.entities.Chit
import com.example.chitmo.data.local.entities.ChitParticipantCrossRef
import com.example.chitmo.data.local.entities.ChitParticipantMonthlyStatus
import com.example.chitmo.data.local.entities.Participant

@Dao
interface ChitMoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertChit(chit: Chit): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertParticipant(participant: Participant)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertChitParticipantCrossRef(chitParticipantCrossRef: ChitParticipantCrossRef)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertChitParticipantMonthlyStatus(chitParticipantMonthlyStatus: ChitParticipantMonthlyStatus)

    @Query("SELECT * FROM Chit")
    fun getAllChits(): List<Chit>

    @Query("SELECT * FROM Participant")
    fun getAllParticipants(): List<Participant>

    @Query("SELECT * FROM ChitParticipantCrossRef WHERE chitId = :chitId")
    fun getAllChitParticipantsCrossRef(chitId: Long): List<ChitParticipantCrossRef>

    @Query("SELECT * FROM ChitParticipantMonthlyStatus WHERE chitId = :chitId and participantId = :participantId")
    fun getAllChitParticipantsMonthlyStatus(chitId: Long, participantId: Long): List<ChitParticipantMonthlyStatus>
}
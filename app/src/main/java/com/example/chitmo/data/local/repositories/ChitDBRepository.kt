package com.example.chitmo.data.local.repositories

import com.example.chitmo.data.local.entities.Chit
import com.example.chitmo.data.local.entities.ChitParticipantCrossRef
import com.example.chitmo.data.local.entities.ChitParticipantMonthlyStatus
import com.example.chitmo.data.local.entities.Participant
import com.example.chitmo.domain.models.ChitData


interface ChitDBRepository {
    suspend fun insertNewChit(chitData: ChitData): Chit
    suspend fun insertParticipant(participant: Participant)
    suspend fun getAllChits(): List<Chit>
    suspend fun getAllParticipants(): List<Participant>
    suspend fun getAllChitParticipants(chitId: Long): List<ChitParticipantCrossRef>
    suspend fun getAllChitParticipantsMonthlyStatus(chitId: Long, participantId: Long): List<ChitParticipantMonthlyStatus>
}
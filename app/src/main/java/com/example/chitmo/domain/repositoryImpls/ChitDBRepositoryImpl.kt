package com.example.chitmo.domain.repositoryImpls

import androidx.room.withTransaction
import com.example.chitmo.data.local.databases.ChitMoDatabase
import com.example.chitmo.data.local.entities.Chit
import com.example.chitmo.data.local.entities.ChitParticipantCrossRef
import com.example.chitmo.data.local.entities.ChitParticipantMonthlyStatus
import com.example.chitmo.data.local.entities.Participant
import com.example.chitmo.data.local.repositories.ChitDBRepository
import com.example.chitmo.domain.models.ChitData
import javax.inject.Inject


class ChitDBRepositoryImpl @Inject constructor(private val chitMoDatabase: ChitMoDatabase) :
    ChitDBRepository {

    override suspend fun insertNewChit(chitData: ChitData): Chit {
        return chitMoDatabase.withTransaction {
            val chit = Chit(
                startMonthYear = chitData.startMonthYear,
                durationInMonths = chitData.durationInMonths,
                amount = chitData.amount
            )
            chitMoDatabase.getChitMoDao().apply {
                chit.chitId = insertChit(chit)
                chitData.chitParticipantList.forEach {
                    insertChitParticipantCrossRef(
                        ChitParticipantCrossRef(
                            chit.chitId, it.participantId, it.isBid, it.bidMonthYear
                        )
                    )
                    it.chitParticipantMonthlyDataList.forEach { monthlyStatus ->
                        insertChitParticipantMonthlyStatus(
                            ChitParticipantMonthlyStatus(
                                chit.chitId,
                                it.participantId,
                                monthlyStatus.monthYear,
                                monthlyStatus.status
                            )
                        )
                    }
                }
            }
            chit
        }
    }

    override suspend fun insertParticipant(participant: Participant) {
        chitMoDatabase.getChitMoDao().insertParticipant(participant)
    }

    override suspend fun getAllChits(): List<Chit> = chitMoDatabase.getChitMoDao().getAllChits()

    override suspend fun getAllParticipants(): List<Participant> =
        chitMoDatabase.getChitMoDao().getAllParticipants()

    override suspend fun getAllChitParticipants(chitId: Long): List<ChitParticipantCrossRef> =
        chitMoDatabase.getChitMoDao().getAllChitParticipantsCrossRef(chitId)

    override suspend fun getAllChitParticipantsMonthlyStatus(
        chitId: Long, participantId: Long
    ): List<ChitParticipantMonthlyStatus> =
        chitMoDatabase.getChitMoDao().getAllChitParticipantsMonthlyStatus(chitId, participantId)
}
package com.example.chitmo.di

import android.content.Context
import androidx.room.Room
import com.example.chitmo.data.local.daos.ChitMoDao
import com.example.chitmo.data.local.databases.ChitMoDatabase
import com.example.chitmo.data.local.repositories.ChitDBRepository
import com.example.chitmo.domain.repositoryImpls.ChitDBRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppDatabaseModule {
    @Provides
    @Singleton
    fun provideChitDatabase(@ApplicationContext context: Context): ChitMoDatabase =
        Room.databaseBuilder(
            context,
            ChitMoDatabase::class.java,
            "chit database"
        ).build()

    @Provides
    @Singleton
    fun provideChitDBRepository(chitDBRepository: ChitDBRepositoryImpl): ChitDBRepository = chitDBRepository
}
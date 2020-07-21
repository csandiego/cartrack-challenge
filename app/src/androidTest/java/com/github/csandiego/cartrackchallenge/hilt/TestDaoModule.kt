package com.github.csandiego.cartrackchallenge.hilt

import android.content.Context
import androidx.room.Room
import com.github.csandiego.cartrackchallenge.dao.CredentialDao
import com.github.csandiego.cartrackchallenge.room.CartrackChallengeDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class TestDaoModule {

    @Singleton
    @Provides
    fun database(@ApplicationContext context: Context): CartrackChallengeDatabase =
        Room.inMemoryDatabaseBuilder(context, CartrackChallengeDatabase::class.java).build()

    @Singleton
    @Provides
    fun credentialDao(database: CartrackChallengeDatabase): CredentialDao = database.credentialDao()
}
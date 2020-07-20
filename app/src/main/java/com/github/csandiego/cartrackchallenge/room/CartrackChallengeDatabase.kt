package com.github.csandiego.cartrackchallenge.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.github.csandiego.cartrackchallenge.dao.CredentialDao
import com.github.csandiego.cartrackchallenge.data.Credential

@Database(entities = [Credential::class], version = 1, exportSchema = false)
abstract class CartrackChallengeDatabase : RoomDatabase() {

    abstract fun credentialDao(): CredentialDao
}
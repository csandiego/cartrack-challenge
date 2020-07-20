package com.github.csandiego.cartrackchallenge.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.github.csandiego.cartrackchallenge.data.Credential
import com.github.csandiego.cartrackchallenge.room.CartrackChallengeDatabase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class CredentialDaoUnitTest {

    private val credential = Credential(0, "username", "password")
    private lateinit var database: CartrackChallengeDatabase

    @Before
    fun setUp() = runBlocking {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            CartrackChallengeDatabase::class.java
        ).build().also {
            it.credentialDao().insert(credential)
        }
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun givenInvalidUsernamePasswordWhenValidateThenReturnFalse() = runBlocking {
        assertFalse(database.credentialDao().validate("abc", "def"))
    }

    @Test
    fun givenValidUsernamePasswordWhenValidateThenReturnTrue() = runBlocking {
        assertTrue(database.credentialDao().validate(credential.username, credential.password))
    }
}
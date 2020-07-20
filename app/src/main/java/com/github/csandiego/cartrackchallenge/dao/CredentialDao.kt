package com.github.csandiego.cartrackchallenge.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.github.csandiego.cartrackchallenge.data.Credential

@Dao
interface CredentialDao {

    @Insert
    suspend fun insert(credential: Credential): Long

    @Query("SELECT COUNT(*) = 1 FROM Credential WHERE username = :username AND password = :password")
    suspend fun validate(username: String, password: String): Boolean
}
package com.github.csandiego.cartrackchallenge.repository

import com.github.csandiego.cartrackchallenge.data.Credential

interface CredentialRepository {

    suspend fun insert(credential: Credential): Long

    suspend fun validate(username: String, password: String): Boolean
}
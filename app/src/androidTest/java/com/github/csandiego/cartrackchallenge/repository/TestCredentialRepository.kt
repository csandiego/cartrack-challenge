package com.github.csandiego.cartrackchallenge.repository

import com.github.csandiego.cartrackchallenge.data.Credential
import javax.inject.Inject

class TestCredentialRepository @Inject constructor() : CredentialRepository {

    override suspend fun insert(credential: Credential): Long = 0L

    override suspend fun validate(username: String, password: String): Boolean {
        return username == "username" && password == "password"
    }
}
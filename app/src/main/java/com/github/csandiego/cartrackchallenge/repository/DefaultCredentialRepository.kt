package com.github.csandiego.cartrackchallenge.repository

import com.github.csandiego.cartrackchallenge.dao.CredentialDao

class DefaultCredentialRepository(private val dao: CredentialDao) : CredentialRepository {

    override suspend fun validate(username: String, password: String): Boolean =
        dao.validate(username, password)
}
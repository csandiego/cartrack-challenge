package com.github.csandiego.cartrackchallenge.repository

import com.github.csandiego.cartrackchallenge.dao.CredentialDao
import com.github.csandiego.cartrackchallenge.data.Credential
import javax.inject.Inject

class DefaultCredentialRepository @Inject constructor(private val dao: CredentialDao) :
    CredentialRepository {

    override suspend fun insert(credential: Credential): Long = dao.insert(credential)

    override suspend fun validate(username: String, password: String): Boolean =
        dao.validate(username, password)
}
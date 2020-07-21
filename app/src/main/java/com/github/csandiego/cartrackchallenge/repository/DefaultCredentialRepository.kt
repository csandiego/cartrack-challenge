package com.github.csandiego.cartrackchallenge.repository

import com.github.csandiego.cartrackchallenge.dao.CredentialDao
import javax.inject.Inject

class DefaultCredentialRepository @Inject constructor(private val dao: CredentialDao) :
    CredentialRepository {

    override suspend fun validate(username: String, password: String): Boolean =
        dao.validate(username, password)
}
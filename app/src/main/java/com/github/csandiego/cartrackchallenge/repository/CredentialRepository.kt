package com.github.csandiego.cartrackchallenge.repository

interface CredentialRepository {

    suspend fun validate(username: String, password: String): Boolean
}
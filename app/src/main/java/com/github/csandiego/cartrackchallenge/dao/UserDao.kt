package com.github.csandiego.cartrackchallenge.dao

import com.github.csandiego.cartrackchallenge.data.User

interface UserDao {

    suspend fun getUsers(): List<User>
}
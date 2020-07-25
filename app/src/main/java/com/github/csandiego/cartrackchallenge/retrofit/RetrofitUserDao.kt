package com.github.csandiego.cartrackchallenge.retrofit

import com.github.csandiego.cartrackchallenge.dao.UserDao
import com.github.csandiego.cartrackchallenge.data.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RetrofitUserDao @Inject constructor(private val service: UserService) : UserDao {

    override suspend fun getUsers(): List<User> = withContext(Dispatchers.IO) {
        service.getUsers().execute().body() ?: emptyList<User>()
    }
}
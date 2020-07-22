package com.github.csandiego.cartrackchallenge.repository

import androidx.lifecycle.LiveData
import com.github.csandiego.cartrackchallenge.data.User
import kotlinx.coroutines.CoroutineScope

interface UserRepository {

    fun getUsersAsLiveData(scope: CoroutineScope): LiveData<List<User>>

    fun getUser(index: Int): User?
}
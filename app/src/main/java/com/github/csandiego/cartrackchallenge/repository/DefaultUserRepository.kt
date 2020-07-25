package com.github.csandiego.cartrackchallenge.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.csandiego.cartrackchallenge.dao.UserDao
import com.github.csandiego.cartrackchallenge.data.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class DefaultUserRepository @Inject constructor(private val dao: UserDao) : UserRepository{

    private val users = MutableLiveData<List<User>>()

    override fun getUsersAsLiveData(scope: CoroutineScope): LiveData<List<User>> {
        if (users.value == null) {
            scope.launch(Dispatchers.Main) {
                users.value = dao.getUsers()
            }
        }
        return users
    }

    override fun getUser(index: Int): User? = users.value?.get(index)
}
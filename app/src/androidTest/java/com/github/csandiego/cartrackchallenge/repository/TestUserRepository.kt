package com.github.csandiego.cartrackchallenge.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.test.platform.app.InstrumentationRegistry
import com.github.csandiego.cartrackchallenge.data.User
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class TestUserRepository @Inject constructor(): UserRepository {

    private val users: MutableLiveData<List<User>>

    init {
        val stream = InstrumentationRegistry.getInstrumentation().context.assets.open("users.json")
        val type = Types.newParameterizedType(List::class.java, User::class.java)
        val adapter: JsonAdapter<List<User>> = Moshi.Builder().build().adapter(type)
        val data = adapter.fromJson(okio.Buffer().readFrom(stream))
        users = MutableLiveData(data!!)
    }

    override fun getUsersAsLiveData(scope: CoroutineScope): LiveData<List<User>> = users

    override fun getUser(index: Int): User? = users.value?.get(index)
}
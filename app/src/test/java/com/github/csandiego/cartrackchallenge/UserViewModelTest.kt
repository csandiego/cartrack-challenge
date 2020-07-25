package com.github.csandiego.cartrackchallenge

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.github.csandiego.cartrackchallenge.data.User
import com.github.csandiego.cartrackchallenge.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class UserViewModelTest {

    private val users = listOf(
        User(null, null, null, null, null, null, null, null),
        User(1, null, null, null, null, null, null, null),
        User(null, null, null, null, null, null, null, null),
        User(null, null, null, null, null, null, null, null),
        User(null, null, null, null, null, null, null, null)
    )

    private val repository =  object : UserRepository {

        override fun getUsersAsLiveData(scope: CoroutineScope): LiveData<List<User>> = MutableLiveData(users)

        override fun getUser(index: Int): User? = users[index]
    }

    private lateinit var viewModel: UserViewModel

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val dispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        viewModel = UserViewModel(repository, SavedStateHandle()).apply {
            users.observeForever {}
            selected.observeForever {}
            user.observeForever {}
            present.observeForever {}
        }
    }

    @After
    fun tearDown() {
        dispatcher.cleanupTestCoroutines()
        Dispatchers.resetMain()
    }

    @Test
    fun givenUsersWhenLoadedThenLoadUsers() {
        assertEquals(users.size, viewModel.users.value!!.size)
    }

    @Test
    fun givenUsersWhenIndexSelectedThenUpdateUser() {
        with(viewModel) {
            select(1)
            assertEquals(this@UserViewModelTest.users[1], user.value)
        }
    }

    @Test
    fun givenUsersWhenIndexSelectedTriggerPresentation() {
        with(viewModel) {
            select(0)
            assertTrue(present.value!!)
        }
    }

    @Test
    fun givenSelectedIndexWhenIndexReselectedThenNoOp() {
        with(viewModel) {
            select(0)
            presentationHandled()
            select(0)
            assertFalse(present.value!!)
        }
    }
}
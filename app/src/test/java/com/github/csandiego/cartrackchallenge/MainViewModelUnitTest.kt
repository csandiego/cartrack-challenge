package com.github.csandiego.cartrackchallenge

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.github.csandiego.cartrackchallenge.data.Credential
import com.github.csandiego.cartrackchallenge.repository.CredentialRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelUnitTest {

    private val credential = Credential(0, "username", "password")
    private val repository = object : CredentialRepository {

        override suspend fun insert(credential: Credential): Long = 0L

        override suspend fun validate(username: String, password: String): Boolean =
            username == credential.username && password == credential.password
    }
    private lateinit var viewModel: MainViewModel

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val dispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        viewModel = MainViewModel(repository).apply {
            usernameError.observeForever {}
            passwordError.observeForever {}
            loginSuccess.observeForever {}
        }
    }

    @After
    fun tearDown() {
        dispatcher.cleanupTestCoroutines()
        Dispatchers.resetMain()
    }

    @Test
    fun givenEmptyUsernameWhenEnteredThenUsernameErrorRequired() {
        with(viewModel) {
            username = ""
            assertEquals(MainViewModel.Error.REQUIRED, usernameError.value)
        }
    }

    @Test
    fun givenNonEmptyUsernameWhenEnteredThenUsernameErrorNone() {
        with(viewModel) {
            username = credential.username
            assertEquals(MainViewModel.Error.NONE, usernameError.value)
        }
    }

    @Test
    fun givenEmptyPasswordWhenEnteredThenPasswordErrorRequired() {
        with(viewModel) {
            password = ""
            assertEquals(MainViewModel.Error.REQUIRED, passwordError.value)
        }
    }

    @Test
    fun givenNonEmptyPasswordWhenEnteredThenPasswordErrorNone() {
        with(viewModel) {
            password = credential.password
            assertEquals(MainViewModel.Error.NONE, passwordError.value)
        }
    }

    @Test
    fun givenEmptyCredentialWhenLoginThenUsernameErrorRequiredAndPasswordErrorRequired() {
        with(viewModel) {
            login()
            assertEquals(MainViewModel.Error.REQUIRED, usernameError.value)
            assertEquals(MainViewModel.Error.REQUIRED, passwordError.value)
        }
    }

    @Test
    fun givenInvalidCredentialWhenLoginThenUsernameErrorInvalid() {
        with(viewModel) {
            username = "abc"
            password = "xyz"
            login()
            assertEquals(MainViewModel.Error.INVALID, usernameError.value)
        }
    }

    @Test
    fun givenValidCredentialWhenLoginThenLoginSuccessTrue() {
        with(viewModel) {
            username = credential.username
            password = credential.password
            login()
            assertTrue(loginSuccess.value!!)
        }
    }
}
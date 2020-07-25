package com.github.csandiego.cartrackchallenge.retrofit

import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.Buffer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class RetrofitUserDaoUnitTest {

    private val src = javaClass.classLoader!!.getResourceAsStream("users.json")
    private lateinit var server: MockWebServer
    private lateinit var dao: RetrofitUserDao

    @Before
    fun setUp() {
        server = MockWebServer().apply {
            enqueue(MockResponse().setBody(Buffer().readFrom(src)))
            start()
        }
        val r = Retrofit.Builder()
            .baseUrl(server.url(""))
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
        val s = r.create(UserService::class.java)
        dao = RetrofitUserDao(s)
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun given10EntriesWhenGetUsersThenReturn10Users() = runBlocking {
        assertEquals(10, dao.getUsers().size)
    }
}
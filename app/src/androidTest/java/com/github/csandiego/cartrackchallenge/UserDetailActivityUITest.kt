package com.github.csandiego.cartrackchallenge

import android.content.Context
import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.github.csandiego.cartrackchallenge.hilt.DaoModule
import com.github.csandiego.cartrackchallenge.hilt.RepositoryModule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@UninstallModules(value = [DaoModule::class, RepositoryModule::class])
@HiltAndroidTest
class UserDetailActivityUITest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule<UserDetailActivity>(
        Intent(
            ApplicationProvider.getApplicationContext<Context>(),
            UserDetailActivity::class.java
        ).apply {
            putExtra("index", 6)
        })

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun givenDataWhenLoadedThenShowMap() {
        onView(withId(R.id.mapView)).check(matches(isDisplayed()))
    }
}
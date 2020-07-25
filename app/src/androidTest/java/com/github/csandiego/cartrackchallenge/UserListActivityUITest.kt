package com.github.csandiego.cartrackchallenge

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.github.csandiego.cartrackchallenge.hilt.DaoModule
import com.github.csandiego.cartrackchallenge.hilt.RepositoryModule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.hamcrest.Matchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@UninstallModules(value = [DaoModule::class, RepositoryModule::class])
@HiltAndroidTest
class UserListActivityUITest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(UserListActivity::class.java)

    @Before
    fun setUp() {
        hiltRule.inject()
        Intents.init()
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    @Test
    fun givenDataWhenItemClickedThenStartActivity() {
        val position = 1
        onView(withId(R.id.user_list)).perform(
            actionOnItemAtPosition<UserListActivity.ViewHolder>(
                position,
                click()
            )
        )
        intended(
            allOf(
                hasComponent(UserDetailActivity::class.qualifiedName),
                hasExtra("index", position)
            )
        )
    }
}
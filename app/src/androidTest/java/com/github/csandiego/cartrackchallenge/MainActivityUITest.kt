package com.github.csandiego.cartrackchallenge

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.github.csandiego.cartrackchallenge.data.Credential
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.endsWith
import org.junit.Rule
import org.junit.Test

class MainActivityUITest {

    private val credential = Credential(0, "username", "password")

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun givenEmptyUsernameWhenEnteredThenUsernameErrorRequired() {
        onView(allOf(
            withClassName(endsWith("TextInputEditText")),
            isDescendantOfA(withId(R.id.usernameTextInputLayout))
        )).perform(replaceText("a"), replaceText(""))
        onView(allOf(
            withId(R.id.textinput_error),
            isDescendantOfA(withId(R.id.usernameTextInputLayout))
        )).check(matches(allOf(
            withText(R.string.error_required),
            withEffectiveVisibility(Visibility.VISIBLE)
        )))
    }

    @Test
    fun givenNonEmptyUsernameWhenEnteredThenUsernameErrorNone() {
        onView(allOf(
            withClassName(endsWith("TextInputEditText")),
            isDescendantOfA(withId(R.id.usernameTextInputLayout))
        )).perform(replaceText(credential.username))
        onView(allOf(
            withId(R.id.textinput_error),
            isDescendantOfA(withId(R.id.usernameTextInputLayout))
        )).check(doesNotExist())
    }

    @Test
    fun givenEmptyPasswordWhenEnteredThenPasswordErrorRequired() {
        onView(allOf(
            withClassName(endsWith("TextInputEditText")),
            isDescendantOfA(withId(R.id.passwordTextInputLayout))
        )).perform(replaceText("a"), replaceText(""))
        onView(allOf(
            withId(R.id.textinput_error),
            isDescendantOfA(withId(R.id.passwordTextInputLayout))
        )).check(matches(allOf(
            withText(R.string.error_required),
            withEffectiveVisibility(Visibility.VISIBLE)
        )))
    }

    @Test
    fun givenNonEmptyPasswordWhenEnteredThenPasswordErrorNone() {
        onView(allOf(
            withClassName(endsWith("TextInputEditText")),
            isDescendantOfA(withId(R.id.passwordTextInputLayout))
        )).perform(replaceText(credential.password))
        onView(allOf(
            withId(R.id.textinput_error),
            isDescendantOfA(withId(R.id.passwordTextInputLayout))
        )).check(doesNotExist())
    }

    @Test
    fun givenEmptyCredentialWhenLoginThenUsernameErrorRequiredAndPasswordErrorRequired() {
        onView(withId(R.id.loginButton)).perform(click())
        onView(allOf(
            withId(R.id.textinput_error),
            isDescendantOfA(withId(R.id.usernameTextInputLayout))
        )).check(matches(allOf(
            withText(R.string.error_required),
            withEffectiveVisibility(Visibility.VISIBLE)
        )))
        onView(allOf(
            withId(R.id.textinput_error),
            isDescendantOfA(withId(R.id.passwordTextInputLayout))
        )).check(matches(allOf(
            withText(R.string.error_required),
            withEffectiveVisibility(Visibility.VISIBLE)
        )))
    }

    @Test
    fun givenInvalidCredentialWhenLoginThenUsernameErrorInvalid() {
        onView(allOf(
            withClassName(endsWith("TextInputEditText")),
            isDescendantOfA(withId(R.id.usernameTextInputLayout))
        )).perform(replaceText("abc"))
        onView(allOf(
            withClassName(endsWith("TextInputEditText")),
            isDescendantOfA(withId(R.id.passwordTextInputLayout))
        )).perform(replaceText("def"))
        onView(withId(R.id.loginButton)).perform(click())
        onView(allOf(
            withId(R.id.textinput_error),
            isDescendantOfA(withId(R.id.usernameTextInputLayout))
        )).check(matches(allOf(
            withText(R.string.error_invalid_username_password),
            withEffectiveVisibility(Visibility.VISIBLE)
        )))
    }
}
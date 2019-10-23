package com.example.codetest

import android.support.test.espresso.Espresso.onData
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.typeText
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.matcher.ComponentNameMatchers.hasClassName
import android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent
import android.support.test.espresso.matcher.RootMatchers.isDialog
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.example.codetest.model.DummyData
import org.hamcrest.Matchers.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    var activityTestRule = ActivityTestRule<MainActivity>(MainActivity::class.java)

    @Before
    fun setUp() {
        Intents.init()
    }

    @Test
    fun testEmptyFromAccount() {
        onView(withId(R.id.btnConfirm)).perform(click())
        onView(withId(R.id.tvErrorMessage)).check(matches(withText(R.string.error_missing_from_account)))
    }

    @Test
    fun testEmptyToAccount() {
        val accounts = DummyData.accounts

        onView(withId(R.id.rlFromAccount)).perform(click())
        onView(withText(R.string.choose_an_account)).inRoot(isDialog()).check(matches(isDisplayed()))
        onData(anything()).atPosition(1).perform(click())
        onView(withId(R.id.tvFromAccountNumber)).check(matches(withText(accounts[1])))

        onView(withId(R.id.btnConfirm)).perform(click())
        onView(withId(R.id.tvErrorMessage)).check(matches(withText(R.string.error_missing_to_account)))
    }

    @Test
    fun testEmptyAmount() {
        val accounts = DummyData.accounts

        onView(withId(R.id.rlFromAccount)).perform(click())
        onView(withText(R.string.choose_an_account)).inRoot(isDialog()).check(matches(isDisplayed()))
        onData(anything()).atPosition(1).perform(click())
        onView(withId(R.id.tvFromAccountNumber)).check(matches(withText(accounts[1])))

        onView(withId(R.id.rlToAccount)).perform(click())
        onView(withText(R.string.choose_an_account)).inRoot(isDialog()).check(matches(isDisplayed()))
        onData(anything()).atPosition(2).perform(click())
        onView(withId(R.id.tvToAccountNumber)).check(matches(withText(accounts[2])))

        onView(withId(R.id.btnConfirm)).perform(click())
        onView(withId(R.id.tvErrorMessage)).check(matches(withText(R.string.error_missing_amount)))
    }

    @Test
    fun testInvalidAmount() {
        val accounts = DummyData.accounts

        onView(withId(R.id.rlFromAccount)).perform(click())
        onView(withText(R.string.choose_an_account)).inRoot(isDialog()).check(matches(isDisplayed()))
        onData(anything()).atPosition(1).perform(click())
        onView(withId(R.id.tvFromAccountNumber)).check(matches(withText(accounts[1])))

        onView(withId(R.id.rlToAccount)).perform(click())
        onView(withText(R.string.choose_an_account)).inRoot(isDialog()).check(matches(isDisplayed()))
        onData(anything()).atPosition(2).perform(click())
        onView(withId(R.id.tvToAccountNumber)).check(matches(withText(accounts[2])))

        onView(withId(R.id.etAmount)).perform(typeText("16.789"))
        onView(withId(R.id.btnConfirm)).perform(click())
        onView(withId(R.id.tvErrorMessage)).check(matches(withText(R.string.error_invalid_amount)))
    }

    @Test
    fun testValidTransfer() {
        val accounts = DummyData.accounts

        onView(withId(R.id.rlFromAccount)).perform(click())
        onView(withText(R.string.choose_an_account)).inRoot(isDialog()).check(matches(isDisplayed()))
        onData(anything()).atPosition(1).perform(click())
        onView(withId(R.id.tvFromAccountNumber)).check(matches(withText(accounts[1])))

        onView(withId(R.id.rlToAccount)).perform(click())
        onView(withText(R.string.choose_an_account)).inRoot(isDialog()).check(matches(isDisplayed()))
        onData(anything()).atPosition(2).perform(click())
        onView(withId(R.id.tvToAccountNumber)).check(matches(withText(accounts[2])))

        onView(withId(R.id.etAmount)).perform(typeText("16.7"))
        onView(withId(R.id.btnConfirm)).perform(click())

        Thread.sleep(2000) // Wait for 2 seconds

        intended(hasComponent(hasClassName(TransferSuccessActivity::class.java.name)))

        onView(withId(R.id.tvReferenceNumber)).check(matches(withText(startsWith("REF"))))
        onView(withId(R.id.tvFromAccountNumber)).check(matches(withText(accounts[1])))
        onView(withId(R.id.tvToAccountNumber)).check(matches(withText(accounts[2])))
        onView(withId(R.id.tvAmount)).check(matches(withText("16.7")))
    }

    @After
    fun tearDown() {
        Intents.release()
    }
}
package com.jeff.covidtracker.main.list.view

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.jeff.covidtracker.R
import com.jeff.covidtracker.main.RecyclerViewItemCountAssertion
import org.hamcrest.CoreMatchers
import org.hamcrest.Matcher
import org.hamcrest.Matchers.greaterThan
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @Rule @JvmField
    var activityRule = ActivityTestRule(MainActivity::class.java)

    private fun sleep() {
        Thread.sleep(3000)
    }

    @Test
    fun brazil() {
        onView(withId(R.id.country_recycler_view)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                1, click()));


        onView(
            CoreMatchers.allOf(
                CoreMatchers.instanceOf(TextView::class.java),
                ViewMatchers.withParent(withId(R.id.country_detail_toolbar))
            )
        )
            .check(ViewAssertions.matches(ViewMatchers.withText("Brazil")))
    }

    @Test
    fun countryListIsNotEmpty() {
        sleep()
        onView(withId(R.id.country_recycler_view)).check(
            RecyclerViewItemCountAssertion.withItemCount(greaterThan(0) as Matcher<Int?>?)
        )
    }

}
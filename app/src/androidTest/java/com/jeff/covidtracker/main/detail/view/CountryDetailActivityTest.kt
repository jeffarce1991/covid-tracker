package com.jeff.covidtracker.main.detail.view

import android.content.Intent
import android.widget.TextView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.jeff.covidtracker.R
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.instanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class CountryDetailActivityTest {

    @Rule @JvmField
    var activityRule = ActivityTestRule(
        CountryDetailActivity::class.java,
        true,
        false)

    @Test
    fun toolbarTitleNull() {
        val intent = Intent()
        activityRule.launchActivity(intent)

        onView(allOf(instanceOf(TextView::class.java),
            withParent(withId(R.id.country_detail_toolbar))))
            .check(matches(withText(R.string.missing_country_name)))
    }

    @Test
    fun toolbarTitleSettingSucceeds() {
        val intent = Intent()
        intent.putExtra(CountryDetailActivity.EXTRA_COUNTRY_NAME, "Brazil")
        activityRule.launchActivity(intent)

        onView(allOf(instanceOf(TextView::class.java),
            withParent(withId(R.id.country_detail_toolbar))))
            .check(matches(withText("Brazil")))
    }
}
package com.jeff.covidtracker.main.list.view

import android.content.Intent
import android.os.SystemClock
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.jeff.covidtracker.R
import com.jeff.covidtracker.TestComponent
import com.jeff.covidtracker.database.local.Cases
import com.jeff.covidtracker.main.RecyclerViewItemCountAssertion
import com.jeff.covidtracker.main.list.presenter.MainPresenter
import com.jeff.covidtracker.supplychain.country.list.SummaryLoader
import com.jeff.covidtracker.webservices.exception.NoInternetException
import io.reactivex.Single
import org.hamcrest.CoreMatchers
import org.hamcrest.Matcher
import org.hamcrest.Matchers.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import javax.inject.Inject

@RunWith(MockitoJUnitRunner::class)
class MainActivityTest {
    @Rule @JvmField
    var activityRule = ActivityTestRule(MainActivity::class.java, false , false)

    @Inject
    lateinit var summaryLoader: SummaryLoader

    @Before
    fun setup() {
        Intents.init()
        MockitoAnnotations.initMocks(this)
        TestComponent.get().inject(this)
    }

    @After
    fun tearDown() {
        Intents.release()
    }
    private fun waitForSeconds(seconds: Long) {
        SystemClock.sleep(seconds * 1000)
    }



    @Test
    fun countryLoaderSuccess() {
        //val summaryLoader = Mockito.mock(SummaryLoader::class.java)
        val expected: List<Cases> = listOf(Cases(), Cases(), Cases())

        given(summaryLoader.loadCountryCases()).willReturn(Single.just(expected))

        //Mockito.`when`(summaryLoader.loadCountryCases()).thenReturn(Single.just(expected))
        //doReturn(Single.just(expected)).`when`(summaryLoader).loadCountryCases()
        showScreen()


        waitForSeconds(3)

        onView(withId(R.id.country_recycler_view)).check(
            RecyclerViewItemCountAssertion.withItemCount(greaterThan(0) as Matcher<Int?>?))

        //onView(withId(R.id.error_image))
            //.check(matches(not(isDisplayed())))
            //.check(matches(isDisplayed()))
    }

    @Test
    fun countryLoaderReturnsEmptyList(){
        given(summaryLoader.loadCountryCases())
            .willReturn(Single.just(emptyList()))

        showScreen()
        waitForSeconds(3)

        onView(withId(R.id.country_recycler_view)).check(
            RecyclerViewItemCountAssertion.withItemCount(comparesEqualTo(0) as Matcher<Int?>?))
    }

    @Test
    fun showsError() {
        given(summaryLoader.loadCountryCases()).willReturn(Single.error(NoInternetException()))

        showScreen()

        waitForSeconds(3)

        onView(withId(R.id.error_image))
            .check(matches(isDisplayed()))

        onView(withId(R.id.error_message))
            .check(matches(withText(R.string.something_went_wrong)))
            .check(matches(isDisplayed()))
    }


    @Test
    fun brazil() {
        showScreen()
        waitForSeconds(3)
        onView(withId(R.id.country_recycler_view)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                1, click()));


        onView(
            CoreMatchers.allOf(
                CoreMatchers.instanceOf(TextView::class.java),
                withParent(withId(R.id.country_detail_toolbar))
            )
        ).check(matches(withText("Brazil")))
    }

    @Test
    fun countryListIsNotEmpty() {
        showScreen()
        waitForSeconds(3)
        onView(withId(R.id.country_recycler_view)).check(
            RecyclerViewItemCountAssertion.withItemCount(greaterThan(0) as Matcher<Int?>?)
        )
    }

    private fun showScreen() {
        activityRule.launchActivity(Intent())
    }
}
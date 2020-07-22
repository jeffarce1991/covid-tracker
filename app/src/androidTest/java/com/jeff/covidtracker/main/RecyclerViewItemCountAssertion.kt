package com.jeff.covidtracker.main

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewAssertion
import org.hamcrest.CoreMatchers
import org.hamcrest.Matcher
import org.junit.Assert

class RecyclerViewItemCountAssertion private constructor(private val matcher: Matcher<Int?>?) :
    ViewAssertion {
    override fun check(
        view: View?,
        noViewFoundException: NoMatchingViewException?
    ) {
        if (noViewFoundException != null) {
            throw noViewFoundException
        }
        val recyclerView = view as RecyclerView?
        val adapter = recyclerView!!.adapter
        Assert.assertThat(adapter!!.itemCount, matcher)
    }

    companion object {
        fun withItemCount(expectedCount: Int): RecyclerViewItemCountAssertion? {
            return withItemCount(CoreMatchers.`is`(expectedCount))
        }

        fun withItemCount(matcher: Matcher<Int?>?): RecyclerViewItemCountAssertion? {
            return RecyclerViewItemCountAssertion(matcher)
        }
    }

}
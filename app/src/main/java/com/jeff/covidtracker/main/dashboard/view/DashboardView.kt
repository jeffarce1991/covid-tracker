package com.jeff.covidtracker.main.dashboard.view

import com.hannesdorfmann.mosby.mvp.MvpView
import com.jeff.covidtracker.database.local.Cases

interface DashboardView : MvpView {
    fun navigateToCountryCasesList()

    fun showSelectMyCountryView()
    fun showMyCountryView()

    fun setMyCountryCases(cases: Cases)

    fun navigateToSelectMyCountryActivity(countryList: ArrayList<String>)

}

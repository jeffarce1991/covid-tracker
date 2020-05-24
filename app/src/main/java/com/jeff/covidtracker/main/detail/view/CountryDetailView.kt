package com.jeff.covidtracker.main.detail.view

import com.hannesdorfmann.mosby.mvp.MvpView
import com.jeff.covidtracker.database.local.Cases

interface CountryDetailView: MvpView {

    fun setCases(cases: List<Cases>)
    fun showProgress()
    fun hideProgress()
}
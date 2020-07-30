package com.jeff.covidtracker.main.detail.view

import com.hannesdorfmann.mosby.mvp.MvpView
import com.jeff.covidtracker.database.local.Cases

interface CountryDetailView: MvpView {

    fun setCases(cases: Cases)
    fun setToolbarTitle()
    fun showProgress()
    fun hideProgress()
}
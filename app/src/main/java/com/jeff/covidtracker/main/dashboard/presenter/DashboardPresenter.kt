package com.jeff.covidtracker.main.dashboard.presenter

import com.hannesdorfmann.mosby.mvp.MvpPresenter
import com.jeff.covidtracker.main.dashboard.view.DashboardView

interface DashboardPresenter: MvpPresenter<DashboardView> {

    fun loadCases(iso2: String)
}

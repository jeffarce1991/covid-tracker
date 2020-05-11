package com.jeff.covidtracker.main.detail.presenter

import com.hannesdorfmann.mosby.mvp.MvpPresenter
import com.jeff.covidtracker.database.local.Cases
import com.jeff.covidtracker.main.detail.view.CountryDetailView
import io.reactivex.Single

interface CountryDetailPresenter : MvpPresenter<CountryDetailView>{
    fun loadLatestCases(slug: String)
}

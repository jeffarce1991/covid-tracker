package com.jeff.covidtracker.main.list.presenter

import com.hannesdorfmann.mosby.mvp.MvpPresenter
import com.jeff.covidtracker.main.list.view.MainView

interface MainPresenter: MvpPresenter<MainView> {
    fun getPhoto(id: Int)
    fun getPhotos()

    fun getCountries()
}
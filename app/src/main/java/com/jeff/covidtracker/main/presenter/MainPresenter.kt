package com.jeff.covidtracker.main.presenter

import com.hannesdorfmann.mosby.mvp.MvpPresenter
import com.jeff.covidtracker.main.view.MainView

interface MainPresenter: MvpPresenter<MainView> {
    fun getPhoto(id: Int)
    fun getPhotos()
}
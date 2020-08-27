package com.jeff.covidtracker.main.splash.presenter

import com.hannesdorfmann.mosby.mvp.MvpPresenter
import com.jeff.covidtracker.main.splash.view.SplashView

interface SplashPresenter: MvpPresenter<SplashView> {
    fun decideWhichScreenToRedirectTo()
}

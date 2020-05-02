package com.jeff.covidtracker.main.view

import com.hannesdorfmann.mosby.mvp.MvpView
import com.jeff.covidtracker.database.local.Photo

interface MainView : MvpView {
     fun hideProgress()
     fun showProgressRemote()
     fun showProgressLocal()

     fun showLoadingDataFailed()
     fun showToast(message: String)
     fun generateDataList(photos: List<Photo>)
}
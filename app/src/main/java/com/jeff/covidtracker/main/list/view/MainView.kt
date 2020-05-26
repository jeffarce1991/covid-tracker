package com.jeff.covidtracker.main.list.view

import com.hannesdorfmann.mosby.mvp.MvpView
import com.jeff.covidtracker.database.local.Cases

interface MainView : MvpView {
     fun hideProgress()
     fun showProgressRemote()
     fun showProgressLocal()

     fun showLoadingDataFailed()
     fun showToast(message: String)
     fun generateDataList(cases: List<Cases>)
}
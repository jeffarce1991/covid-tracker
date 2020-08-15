package com.jeff.covidtracker.main.list.view

import com.hannesdorfmann.mosby.mvp.MvpView
import com.jeff.covidtracker.database.local.Cases

interface MainView : MvpView {
     fun hideProgress()
     fun showProgress()
     fun showProgressRemote()
     fun showProgressLocal()

     fun showEmptyListError()
     fun showNoInternetError()
     fun showError(message: String)
     fun showLoadedLocally()
     fun showLoadedRemotely()
     fun generateDataList(cases: List<Cases>)
     fun clearDataList()
}
package com.jeff.covidtracker.main.detail.presenter

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter
import com.jeff.covidtracker.database.local.Cases
import com.jeff.covidtracker.database.local.Country
import com.jeff.covidtracker.main.detail.view.CountryDetailView
import com.jeff.covidtracker.supplychain.country.detail.CasesLoader
import com.jeff.covidtracker.supplychain.country.list.CountryLoader
import com.jeff.covidtracker.utilities.rx.RxSchedulerUtils
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import timber.log.Timber
import javax.inject.Inject

class DefaultCountryDetailPresenter @Inject
constructor(
    private val casesLoader: CasesLoader,
    private val schedulers: RxSchedulerUtils
): MvpBasePresenter<CountryDetailView>(),
    CountryDetailPresenter {


    lateinit var disposable: Disposable

    override fun loadCases(countryCode: String) {
        casesLoader.loadByCountryCode(countryCode)
            .compose(schedulers.forSingle())
            .subscribe(object : SingleObserver<Cases>{
                override fun onSuccess(t: Cases) {
                    Timber.d("==q countryCode $t")
                    view!!.setCases(t)
                    view!!.hideProgress()
                    dispose()
                }

                override fun onSubscribe(d: Disposable) {
                    disposable = d
                    view!!.showProgress()
                    Timber.d("==q onSubscribe countryCode")
                }

                override fun onError(e: Throwable) {
                    view!!.hideProgress()
                    Timber.e(e)
                    e.printStackTrace()
                    dispose()
                }
            })
    }

    private fun dispose() {
        if (!disposable.isDisposed) disposable.dispose()
    }
}

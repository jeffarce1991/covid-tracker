package com.jeff.covidtracker.main.dashboard.presenter

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter
import com.jeff.covidtracker.database.local.Cases
import com.jeff.covidtracker.database.local.Country
import com.jeff.covidtracker.main.dashboard.view.DashboardView
import com.jeff.covidtracker.main.list.presenter.MainPresenter
import com.jeff.covidtracker.supplychain.country.detail.CasesLoader
import com.jeff.covidtracker.supplychain.country.list.CountryLoader
import com.jeff.covidtracker.supplychain.country.list.SummaryLoader
import com.jeff.covidtracker.utilities.rx.RxSchedulerUtils
import com.jeff.covidtracker.webservices.exception.NoInternetException
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import timber.log.Timber
import javax.inject.Inject

class DefaultDashboardPresenter @Inject
constructor(
    private val casesLoader: CasesLoader,
    private val summaryLoader: SummaryLoader,
    private val schedulers: RxSchedulerUtils
): MvpBasePresenter<DashboardView>(),
    DashboardPresenter {

    lateinit var view: DashboardView

    private var disposable: Disposable? = null

    override fun loadCases(iso2: String) {
        casesLoader.loadByCountryCode(iso2)
            .compose(schedulers.forSingle())
            .subscribe(object : SingleObserver<Cases> {
                override fun onSuccess(t: Cases) {
                    Timber.d("==q countryCode $t")
                    view.setMyCountryCases(t)
                    //view!!.hideProgress()
                    dispose()
                }

                override fun onSubscribe(d: Disposable) {
                    disposable = d
                    //view!!.showProgress()
                    Timber.d("==q onSubscribe loadByCountryCodeRemotely")
                }

                override fun onError(e: Throwable) {
                    //view!!.hideProgress()
                    Timber.e(e)
                    e.printStackTrace()
                    dispose()
                }
            })
    }


    override fun attachView(view: DashboardView) {
        super.attachView(view)
        this.view = view
    }

    private fun dispose() {
        if (!disposable?.isDisposed!!) disposable!!.dispose()
    }

    override fun detachView(retainInstance: Boolean) {
        dispose()
    }
}

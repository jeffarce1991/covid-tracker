package com.jeff.covidtracker.main.splash.presenter

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter
import com.hannesdorfmann.mosby.mvp.MvpPresenter
import com.jeff.covidtracker.database.local.Cases
import com.jeff.covidtracker.main.list.view.MainView
import com.jeff.covidtracker.main.splash.view.SplashView
import com.jeff.covidtracker.supplychain.country.list.SummaryLoader
import com.jeff.covidtracker.utilities.rx.RxSchedulerUtils
import com.jeff.covidtracker.webservices.exception.NoInternetException
import io.reactivex.Completable
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class DefaultSplashPresenter @Inject
constructor(
    private val schedulerUtils: RxSchedulerUtils,
    private val summaryLoader: SummaryLoader
): MvpBasePresenter<SplashView>(), SplashPresenter {

    lateinit var view: SplashView

    lateinit var disposable: Disposable

    override fun decideWhichScreenToRedirectTo() {
        disposable = Completable.complete()
            //.delay(2, TimeUnit.SECONDS)
            .andThen(summaryLoader.loadCountryCasesRemotelyCompletable())
            .andThen { view.navigateToDashboardScreen() }
            //.onErrorResumeNext { redirectToViewingPrivacyPolicy() }
            //.onErrorResumeNext { redirectToFingerprintVerification() }
            .compose(schedulerUtils.forCompletable())
            .subscribe()
    }

    fun loadSummary() {
        summaryLoader.loadCountryCasesRemotely()
            .compose(schedulerUtils.forSingle())
            .subscribe(object : SingleObserver<List<Cases>> {
                override fun onSuccess(t: List<Cases>) {
                    Timber.d("==q getSummary() onSuccess ${t.size}")
                    //view.hideProgress()

                    //view.generateDataList(t)
                    //view.showLoadedRemotely()
                    dispose()
                }

                override fun onSubscribe(d: Disposable) {
                    //view.clearDataList()
                    //view.showProgress()
                    disposable = d
                }

                override fun onError(e: Throwable) {
                    Timber.d("==q getSummary() onError $e")

                    if (e is NoInternetException) {
                        //view.showNoInternetError()
                    } else {
                        //view.showError(e.message!!)
                    }

                    //view.clearDataList()
                    dispose()
                    //view.hideProgress()
                }
            })
    }

    override fun attachView(view: SplashView) {
        super.attachView(view)
        this.view = view
    }

    private fun dispose() {
        if (!disposable.isDisposed) disposable.dispose()
    }

    override fun detachView(retainInstance: Boolean) {
        dispose()
    }

}
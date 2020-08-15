package com.jeff.covidtracker.main.list.presenter

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter
import com.jeff.covidtracker.database.local.Cases
import com.jeff.covidtracker.database.local.Country
import com.jeff.covidtracker.database.local.Photo
import com.jeff.covidtracker.webservices.internet.RxInternet
import com.jeff.covidtracker.main.list.view.MainView
import com.jeff.covidtracker.supplychain.country.list.CountryLoader
import com.jeff.covidtracker.supplychain.country.list.SummaryLoader
import com.jeff.covidtracker.supplychain.photo.PhotoLoader
import com.jeff.covidtracker.webservices.dto.PhotoDto
import com.jeff.covidtracker.utilities.rx.RxSchedulerUtils
import com.jeff.covidtracker.webservices.exception.NoInternetException
import io.reactivex.*
import io.reactivex.disposables.Disposable
import timber.log.Timber
import javax.inject.Inject

class DefaultMainPresenter @Inject
constructor(
    private val internet: RxInternet,
    private val schedulerUtils: RxSchedulerUtils,
    private val loader: PhotoLoader,
    private val countryLoader: CountryLoader,
    private val summaryLoader: SummaryLoader
) : MvpBasePresenter<MainView>(),
    MainPresenter {

    lateinit var view: MainView

    lateinit var disposable: Disposable


    override fun loadCountryCases() {
        summaryLoader.loadCountryCasesRemotely()
            .compose(schedulerUtils.forSingle())
            .subscribe(object : SingleObserver<List<Cases>>{
                override fun onSuccess(t: List<Cases>) {
                    Timber.d("==q getSummary() onSuccess ${t.size}")
                    view.hideProgress()

                    view.generateDataList(t)
                    view.showLoadedRemotely()
                    dispose()
                }

                override fun onSubscribe(d: Disposable) {
                    view.clearDataList()
                    view.showProgress()
                    disposable = d
                }

                override fun onError(e: Throwable) {
                    Timber.d("==q getSummary() onError $e")

                    if (e is NoInternetException) {
                        view.showNoInternetError()
                    } else {
                        view.showError(e.message!!)
                    }

                    view.clearDataList()
                    dispose()
                    view.hideProgress()
                }
            })
    }

    override fun loadCountryCasesLocally() {
        summaryLoader.loadCountryCasesLocally()
            .compose(schedulerUtils.forSingle())
            .subscribe(object : SingleObserver<List<Cases>>{
                override fun onSuccess(t: List<Cases>) {
                    Timber.d("==q getSummary() onSuccess ${t.size}")
                    view.hideProgress()

                    if (t.isEmpty()) {
                        Timber.d("==q getSummary() isEmpty ${t.size}")
                        view.showEmptyListError()
                    } else {
                        view.generateDataList(t)
                        view.showLoadedLocally()
                    }
                    dispose()
                }

                override fun onSubscribe(d: Disposable) {
                    view.showProgress()
                    disposable = d
                }

                override fun onError(e: Throwable) {
                    Timber.d("==q getSummary() onError $e")
                    view.showError(e.message!!)

                    dispose()
                    view.hideProgress()
                }
            })
    }

    override fun getCountries() {
        countryLoader.loadAll()
            .compose(schedulerUtils.forSingle())
            .subscribe(object : SingleObserver<List<Country>>{
                override fun onSuccess(t: List<Country>) {
                    view.hideProgress()
                    //view.generateCountryList(t)

                    dispose()
                }

                override fun onSubscribe(d: Disposable) {
                    view.showProgressRemote()
                    disposable = d
                }

                override fun onError(e: Throwable) {
                    Timber.d("==q getCountries() onError ${e}")

                    dispose()
                    view.hideProgress()
                }
            })


    }

    fun getCountriesFromLocal() {
        countryLoader.loadAllFromLocal()
            .compose(schedulerUtils.forSingle())
            .subscribe(object : SingleObserver<List<Country>>{
                override fun onSuccess(t: List<Country>) {
                    Timber.d("==q getCountriesFromLocal onSuccess : ${t.size}")
                    view.hideProgress()
                    //view.generateDataList(t)
                    dispose()
                }

                override fun onSubscribe(d: Disposable) {
                    view.showProgressLocal()
                    disposable = d
                }

                override fun onError(e: Throwable) {
                    Timber.d("==q getCountriesFromLocal onError ${e}")
                    view.hideProgress()
                    dispose()
                }
            })
    }

    private fun mapPhotoDtosToPhotos(photoDtos: List<PhotoDto>): List<Photo> {
        val photos = mutableListOf<Photo>()
        for (photoDto in photoDtos) {
            val photo = Photo(
                photoDto.id,
                photoDto.albumId,
                photoDto.title,
                photoDto.url,
                photoDto.thumbnailUrl
            )
            photos.add(photo)
        }
        return photos
    }

    override fun attachView(view: MainView) {
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
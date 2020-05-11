package com.jeff.covidtracker.main.list.presenter

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter
import com.jeff.covidtracker.Constants
import com.jeff.covidtracker.database.local.Country
import com.jeff.covidtracker.database.local.Photo
import com.jeff.covidtracker.database.usecase.local.loader.PhotoLocalLoader
import com.jeff.covidtracker.database.usecase.local.saver.PhotoLocalSaver
import com.jeff.covidtracker.webservices.exception.NoInternetException
import com.jeff.covidtracker.webservices.internet.RxInternet
import com.jeff.covidtracker.main.list.view.MainView
import com.jeff.covidtracker.supplychain.country.list.CountryLoader
import com.jeff.covidtracker.supplychain.photo.PhotoLoader
import com.jeff.covidtracker.webservices.dto.PhotoDto
import com.jeff.covidtracker.webservices.api.photos.PhotosApi
import com.jeff.covidtracker.webservices.api.RetrofitClientInstance
import com.jeff.covidtracker.utilities.rx.RxSchedulerUtils
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

class DefaultMainPresenter @Inject
constructor(
    private val internet: RxInternet,
    private val schedulerUtils: RxSchedulerUtils,
    private val loader: PhotoLoader,
    private val countryLoader: CountryLoader
) : MvpBasePresenter<MainView>(),
    MainPresenter {

    lateinit var view: MainView

    lateinit var disposable: Disposable


    override fun getCountries() {
        countryLoader.loadAll()
            .compose(schedulerUtils.forSingle())
            .subscribe(object : SingleObserver<List<Country>>{
                override fun onSuccess(t: List<Country>) {
                    view.hideProgress()
                    view.generateCountryList(t)

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
                    view.generateCountryList(t)
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


    private fun getApi(): PhotosApi {

        /*Create handle for the RetrofitInstance interface*/
        return RetrofitClientInstance.getRxRetrofitInstance(
            Constants.Gateways.JSONPLACEHOLDER
        )!!.create(
            PhotosApi::class.java
        )
    }

    override fun getPhotos() {
        internet.isConnected()
            .andThen(loader.loadAllFromRemote())
            .compose(schedulerUtils.forSingle())
            .subscribe(object : SingleObserver<List<Photo>>{
                override fun onSuccess(t: List<Photo>) {
                    Timber.d("""==q onSuccess ${t.size}""")
                    view.hideProgress()
                    if (t.isNotEmpty()) {
                        view.generateDataList(t)
                        view.showToast("Data loaded Remotely")
                    } else {
                        view.showLoadingDataFailed()
                    }
                    dispose()
                }

                override fun onSubscribe(d: Disposable) {
                    view.showProgressRemote()
                    disposable = d
                }

                override fun onError(e: Throwable) {
                    view.showToast("getPhotos() onError() : ${e.message!!}")

                    Timber.d("==q onError $e" )
                    e.printStackTrace()

                    view.hideProgress()

                    if (e is NoInternetException) {
                        getPhotosFromLocal()
                    } else {
                        dispose()
                    }
                }
            })
    }

    override fun getPhoto(id: Int) {
            internet.isConnected()
                .andThen(getApi().loadPhotoById(id))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<Response<PhotoDto>> {

                    override fun onSubscribe(d: Disposable) {
                        view.showProgressRemote()

                        disposable = d
                        Timber.d("==q onSubscribe")
                    }

                    override fun onSuccess(t: Response<PhotoDto>) {
                        view.hideProgress()
                        view.generateDataList(mapPhotoDtosToPhotos(listOf(t.body()!!)))

                        dispose()
                        Timber.d("==q onSuccess")
                    }
                    override fun onError(e: Throwable) {
                        view.hideProgress()

                        dispose()
                        Timber.e(e)
                        Timber.d("==q onError")
                        e.printStackTrace()
                    }
                })
    }


    fun getPhotosFromLocal(){
        loader.loadAllFromLocal()
            .compose(schedulerUtils.forSingle())
            .subscribe(object : SingleObserver<List<Photo>>{
                override fun onSubscribe(d: Disposable) {
                    disposable = d
                    view.showProgressLocal()
                }

                override fun onSuccess(t: List<Photo>) {
                    Timber.d("==q loadAll onSuccess ${t.size}")

                    view.hideProgress()

                    if (t.isNotEmpty()) {
                        view.showToast("Data loaded Locally")
                        view.generateDataList(t)
                    } else {
                        view.showLoadingDataFailed()
                    }
                    dispose()
                }

                override fun onError(e: Throwable) {
                    Timber.d("==q Load Photos Failed $e")

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
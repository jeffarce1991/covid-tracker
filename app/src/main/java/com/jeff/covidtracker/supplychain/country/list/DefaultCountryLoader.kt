package com.jeff.covidtracker.supplychain.country.list

import com.jeff.covidtracker.database.local.Country
import com.jeff.covidtracker.database.usecase.local.loader.CountryLocalLoader
import com.jeff.covidtracker.database.usecase.local.saver.CountryLocalSaver
import com.jeff.covidtracker.main.mapper.CountryDtoToCountryMapper
import com.jeff.covidtracker.webservices.internet.RxInternet
import com.jeff.covidtracker.webservices.usecase.loader.CountryRemoteLoader
import io.reactivex.Observable
import io.reactivex.Single
import timber.log.Timber
import javax.inject.Inject

class DefaultCountryLoader @Inject
constructor(
    private val internet: RxInternet,
    private val remoteLoader: CountryRemoteLoader,
    private val localSaver: CountryLocalSaver,
    private val localLoader: CountryLocalLoader
) : CountryLoader {

    override fun loadAll(): Single<List<Country>> {
        return loadAllFromLocal()
            .flatMap {
                if (it.isNotEmpty()) {
                    Timber.d("==q Countries loaded locally.")
                    Single.just(it)
                } else {
                    Timber.d("==q No existing Countries locally.")
                    loadAllFromRemote()
                }
            }
    }

    override fun loadAllFromRemote(): Single<List<Country>> {
        return internet.isConnected()
            .andThen(remoteLoader.loadCountries())
            .flatMapObservable { list -> Observable.fromIterable(list) }
            .flatMap(CountryDtoToCountryMapper())
            .toList()
            .flatMap { Single.fromObservable(localSaver.saveAll(it)) }
            .flatMap {
                Timber.d("==q Countries loaded remotely. ${it.size}")
                Single.just(it)
            }
    }

    override fun loadAllFromLocal(): Single<List<Country>> {
        return localLoader.loadAll()
            /*.flatMap {
                when {
                    it.isEmpty() -> Single.error(
                        Throwable(EmptyResultException()))
                    else -> Single.just(it)
                }
            }*/
            .flatMap { countries -> Single.just(countries)}
    }

}

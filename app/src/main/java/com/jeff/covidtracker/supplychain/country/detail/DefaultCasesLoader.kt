package com.jeff.covidtracker.supplychain.country.detail

import com.jeff.covidtracker.database.local.Cases
import com.jeff.covidtracker.database.usecase.local.loader.CasesLocalLoader
import com.jeff.covidtracker.main.mapper.CasesDtoToCasesMapper
import com.jeff.covidtracker.webservices.internet.RxInternet
import com.jeff.covidtracker.webservices.usecase.loader.CasesRemoteLoader
import io.reactivex.Observable
import io.reactivex.Single
import timber.log.Timber
import javax.inject.Inject

class DefaultCasesLoader @Inject
constructor(
    private val localLoader: CasesLocalLoader,
    private val remoteLoader: CasesRemoteLoader,
    private val internet: RxInternet
): CasesLoader{

    override fun loadByCountryCode(countryCode: String): Single<Cases> {
        return localLoader.loadByCountryCode(countryCode)
    }

    override fun loadByCountryCodeRemotely(iso2: String): Single<Cases> {
        return internet.isConnected()
            .andThen(remoteLoader.loadCasesByIso2(iso2))
            .flatMapObservable { list -> Observable.fromIterable(list) }
            .flatMap(CasesDtoToCasesMapper())
            .toList()
            .flatMap {
                Timber.d("==q Countries loaded remotely. ${it.size}")
                Single.just(it.last())
            }
    }

}

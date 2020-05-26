package com.jeff.covidtracker.supplychain.country.list

import com.jeff.covidtracker.database.local.Cases
import com.jeff.covidtracker.database.usecase.local.loader.CountryLocalLoader
import com.jeff.covidtracker.database.usecase.local.saver.CountryLocalSaver
import com.jeff.covidtracker.main.mapper.CasesDtoToCasesMapper
import com.jeff.covidtracker.webservices.internet.RxInternet
import com.jeff.covidtracker.webservices.usecase.loader.SummaryRemoteLoader
import io.reactivex.Observable
import io.reactivex.Single
import timber.log.Timber
import javax.inject.Inject

class DefaultSummaryLoader @Inject
constructor(
    private val internet: RxInternet,
    private val remoteLoader: SummaryRemoteLoader,
    private val localSaver: CountryLocalSaver,
    private val localLoader: CountryLocalLoader
) : SummaryLoader {

    override fun loadCountryCases(): Single<List<Cases>> {
        return internet.isConnected()
            .andThen(remoteLoader.loadSummary())
            .flatMapObservable { list -> Observable.fromIterable(list.countries) }
            .flatMap(CasesDtoToCasesMapper())
            .toList()
            //.flatMap { Single.fromObservable(localSaver.saveAll(it)) }
            .flatMap {
                Timber.d("==q Countries loaded remotely. $it")
                Single.just(it)
            }
    }

}

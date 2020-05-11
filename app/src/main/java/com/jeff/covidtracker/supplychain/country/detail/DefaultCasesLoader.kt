package com.jeff.covidtracker.supplychain.country.detail

import com.jeff.covidtracker.database.local.Cases
import com.jeff.covidtracker.main.mapper.CasesDtoToCasesMapper
import com.jeff.covidtracker.webservices.internet.RxInternet
import com.jeff.covidtracker.webservices.usecase.loader.CasesRemoteLoader
import io.reactivex.Observable
import io.reactivex.Single
import timber.log.Timber
import javax.inject.Inject

class DefaultCasesLoader @Inject
constructor(
    private val internet: RxInternet,
    private val remoteLoader: CasesRemoteLoader
): CasesLoader{

    override fun loadAll(slug: String): Single<List<Cases>> {
        return loadAllFromRemote(slug)
        .flatMap {
            if (it.isNotEmpty()) {
                Timber.d("==q Cases loaded locally.")
                Single.just(it)
            } else {
                Timber.d("==q No existing Cases locally.")
                loadAllFromLocal(slug)
            }
        }
    }

    override fun loadLast(slug: String): Single<Cases> {
        return loadAllFromRemote(slug)
            .flatMap {
                Single.just(it.last())
            }
    }

    override fun loadAllFromRemote(slug: String): Single<List<Cases>> {
        return internet.isConnected()
            .andThen(remoteLoader.loadCases(slug))
            .flatMapObservable { list -> Observable.fromIterable(list) }
            .flatMap(CasesDtoToCasesMapper())
            .toList()
            //.flatMap { Single.fromObservable(localSaver.saveAll(it)) }
            .flatMap {
                Timber.d("==q Countries loaded remotely. ${it.size}")
                Single.just(it)
            }
    }

    override fun loadAllFromLocal(slug: String): Single<List<Cases>> {
        TODO("Not yet implemented")
    }

}

package com.jeff.covidtracker.supplychain.country.list

import android.provider.Settings
import androidx.room.EmptyResultSetException
import com.jeff.covidtracker.database.local.Cases
import com.jeff.covidtracker.database.local.GlobalCases
import com.jeff.covidtracker.database.local.Summary
import com.jeff.covidtracker.database.usecase.local.loader.CasesLocalLoader
import com.jeff.covidtracker.database.usecase.local.saver.CasesLocalSaver
import com.jeff.covidtracker.main.mapper.CasesDtoToCasesMapper
import com.jeff.covidtracker.main.mapper.GlobalCasesDtoToGlobalCasesMapper
import com.jeff.covidtracker.webservices.dto.GlobalCasesDto
import com.jeff.covidtracker.webservices.dto.SummaryDto
import com.jeff.covidtracker.webservices.internet.RxInternet
import com.jeff.covidtracker.webservices.usecase.loader.SummaryRemoteLoader
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import timber.log.Timber
import javax.inject.Inject

class DefaultSummaryLoader @Inject
constructor(
    private val internet: RxInternet,
    private val remoteLoader: SummaryRemoteLoader,
    private val countryCasesLocalSaver: CasesLocalSaver,
    private val countryCasesLocalLoader: CasesLocalLoader
) : SummaryLoader {

    override fun loadCountryCases(): Single<List<Cases>> {
        return loadCountryCasesRemotely()
            .onErrorResumeNext { loadCountryCasesLocally() }
    }

    override fun loadCountryCasesRemotely(): Single<List<Cases>> {
        return internet.isConnected()
            .andThen(remoteLoader.loadSummary())
            .flatMapObservable { list -> Observable.fromIterable(list.countryCases) }
            .flatMap(CasesDtoToCasesMapper())
            .toList()
            .flatMap { Single.fromObservable(countryCasesLocalSaver.saveAll(it)) }
            .flatMap {
                Timber.d("==q Countries loaded remotely. ${it.size}")
                Single.just(it)
            }
    }

    override fun loadCountryCasesRemotelyCompletable(): Completable {
        return internet.isConnected()
            .andThen(remoteLoader.loadSummary())
            .flatMapObservable { list -> Observable.fromIterable(list.countryCases) }
            .flatMap(CasesDtoToCasesMapper())
            .toList()
            .flatMap { Single.fromObservable(countryCasesLocalSaver.saveAll(it)) }
            .flatMapCompletable { Completable.complete() }
    }

    override fun loadCountryCasesLocally() : Single<List<Cases>> {
        return countryCasesLocalLoader.loadAll()
            .flatMap {
                Timber.d("==q Countries loaded locally. ${it.size}")
                Single.just(it)
            }
    }

    override fun loadGlobal(): Single<GlobalCases> {
        return internet.isConnected()
        .andThen(remoteLoader.loadSummary())
        .map { mapToGlobalCases(it) }
        //.flatMap { Single.fromObservable(countryCasesLocalSaver.saveAll(it)) }
        .flatMap {
            Timber.d("==q Countries loaded remotely. ${it.date}")
            Single.just(it)
        }
    }

    private fun mapToGlobalCases(it: SummaryDto): GlobalCases {
        val globalCases = GlobalCases()
        globalCases.date = it.date
        globalCases.newCases!!.newConfirmed = it.globalCases.newConfirmed
        globalCases.newCases!!.newDeaths = it.globalCases.newDeaths
        globalCases.newCases!!.newRecovered = it.globalCases.newRecovered

        return globalCases
    }

}

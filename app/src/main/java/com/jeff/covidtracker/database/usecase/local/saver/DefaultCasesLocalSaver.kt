package com.jeff.covidtracker.database.usecase.local.saver

import com.jeff.covidtracker.database.local.Cases
import com.jeff.covidtracker.database.room.dao.CasesDao
import io.reactivex.Completable
import io.reactivex.Observable
import timber.log.Timber
import javax.inject.Inject

class DefaultCasesLocalSaver @Inject
constructor(private val dao: CasesDao) : CasesLocalSaver {

    override fun save(cases: Cases): Completable {
        return Completable.fromAction { dao.insert(cases)}
    }

    override fun saveAll(casesList: List<Cases>): Observable<List<Cases>> {
        return Completable.fromCallable {
            dao.insert(casesList)
            Timber.d("==q saveAll Done")
            Completable.complete()
        }.andThen(Observable.fromCallable { casesList })
    }

}
package com.jeff.covidtracker.database.usecase.local.saver

import com.jeff.covidtracker.database.local.Cases
import com.jeff.covidtracker.database.local.Photo
import io.reactivex.Completable
import io.reactivex.Observable

interface CasesLocalSaver {

    fun save(cases: Cases): Completable

    fun saveAll(casesList: List<Cases>): Observable<List<Cases>>
}

package com.jeff.covidtracker.database.usecase.local.loader

import com.jeff.covidtracker.database.local.Cases
import com.jeff.covidtracker.database.local.Photo
import com.jeff.covidtracker.database.room.dao.CasesDao
import com.jeff.covidtracker.database.room.dao.PhotoDao
import io.reactivex.Single
import javax.inject.Inject

class DefaultCasesLocalLoader @Inject
constructor(private val dao: CasesDao): CasesLocalLoader {

    override fun loadAll(): Single<List<Cases>> {
        return Single.fromCallable { dao.loadAll() }

    }

    override fun loadByCountryCode(countryCode: String): Single<Cases> {
        return Single.fromCallable { dao.loadByCountryCOde(countryCode) }
    }

}
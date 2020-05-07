package com.jeff.covidtracker.database.usecase.local.loader

import com.jeff.covidtracker.database.local.Country
import com.jeff.covidtracker.database.room.dao.CountryDao
import io.reactivex.Single
import javax.inject.Inject

class DefaultCountryLocalLoader @Inject
constructor(private val dao: CountryDao): CountryLocalLoader{

    override fun loadAll(): Single<List<Country>> {
        return Single.fromCallable { dao.loadAll() }
    }

}

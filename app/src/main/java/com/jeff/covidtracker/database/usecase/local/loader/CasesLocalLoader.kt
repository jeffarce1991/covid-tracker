package com.jeff.covidtracker.database.usecase.local.loader

import com.jeff.covidtracker.database.local.Cases
import com.jeff.covidtracker.database.local.Photo
import io.reactivex.Single

interface CasesLocalLoader {
    fun loadAll(): Single<List<Cases>>
    fun loadByCountryCode(countryCode: String): Single<Cases>
}
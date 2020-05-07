package com.jeff.covidtracker.database.usecase.local.loader

import com.jeff.covidtracker.database.local.Country
import io.reactivex.Single

interface CountryLocalLoader {
    fun loadAll(): Single<List<Country>>
}

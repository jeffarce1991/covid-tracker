package com.jeff.covidtracker.supplychain.country.list

import com.jeff.covidtracker.database.local.Country
import io.reactivex.Single

interface CountryLoader {
    fun loadAll() : Single<List<Country>>
    fun loadAllFromRemote() : Single<List<Country>>
    fun loadAllFromLocal() : Single<List<Country>>
}

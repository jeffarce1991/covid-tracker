package com.jeff.covidtracker.supplychain.country.list

import com.jeff.covidtracker.database.local.Cases
import io.reactivex.Single

interface SummaryLoader {
    fun loadCountryCases() : Single<List<Cases>>
    //TODO Make Models for Global and Summary
    // fun loadGlobal() : Single<Global>
    // fun loadSummary() : Single<Summary>
}

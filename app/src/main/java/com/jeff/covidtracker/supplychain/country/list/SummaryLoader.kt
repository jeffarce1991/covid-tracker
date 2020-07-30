package com.jeff.covidtracker.supplychain.country.list

import android.provider.Settings
import com.jeff.covidtracker.database.local.Cases
import com.jeff.covidtracker.database.local.GlobalCases
import io.reactivex.Single

interface SummaryLoader {
    fun loadCountryCases() : Single<List<Cases>>
    fun loadCountryCasesLocally() : Single<List<Cases>>
    fun loadCountryCasesRemotely() : Single<List<Cases>>
    //TODO Make Models for Global and Summary
    fun loadGlobal() : Single<GlobalCases>
    //fun loadSummary() : Single<Summary>
}

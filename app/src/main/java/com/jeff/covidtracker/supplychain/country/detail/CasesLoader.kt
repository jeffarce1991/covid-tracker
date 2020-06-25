package com.jeff.covidtracker.supplychain.country.detail

import com.jeff.covidtracker.database.local.Cases
import io.reactivex.Single

interface CasesLoader {
        fun loadByCountryCode(countryCode: String): Single<Cases>
}
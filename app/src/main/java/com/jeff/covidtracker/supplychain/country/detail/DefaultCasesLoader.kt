package com.jeff.covidtracker.supplychain.country.detail

import com.jeff.covidtracker.database.local.Cases
import com.jeff.covidtracker.database.usecase.local.loader.CasesLocalLoader
import com.jeff.covidtracker.main.mapper.CasesDtoToCasesMapper
import com.jeff.covidtracker.webservices.internet.RxInternet
import com.jeff.covidtracker.webservices.usecase.loader.CasesRemoteLoader
import io.reactivex.Observable
import io.reactivex.Single
import timber.log.Timber
import javax.inject.Inject

class DefaultCasesLoader @Inject
constructor(
    private val localLoader: CasesLocalLoader
): CasesLoader{

    override fun loadByCountryCode(countryCode: String): Single<Cases> {
        return localLoader.loadByCountryCode(countryCode)
    }

}

package com.jeff.covidtracker.webservices.usecase.loader

import com.jeff.covidtracker.webservices.dto.CountryDto
import com.jeff.covidtracker.webservices.dto.SummaryDto
import io.reactivex.Single

interface CountryRemoteLoader {
    fun loadCountries(): Single<List<CountryDto>>
}

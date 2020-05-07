package com.jeff.covidtracker.webservices.usecase.loader

import com.jeff.covidtracker.webservices.dto.CountryDto
import io.reactivex.Single

interface CountryRemoteLoader {
    fun loadAll(): Single<List<CountryDto>>
}

package com.jeff.covidtracker.webservices.usecase.loader

import com.jeff.covidtracker.webservices.dto.CasesDto
import io.reactivex.Single

interface CasesRemoteLoader {
    fun loadCases(slug: String): Single<List<CasesDto>>
    fun loadCasesByIso2(iso2: String): Single<List<CasesDto>>
}

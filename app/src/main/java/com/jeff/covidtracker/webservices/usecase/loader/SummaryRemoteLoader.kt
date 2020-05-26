package com.jeff.covidtracker.webservices.usecase.loader

import com.jeff.covidtracker.webservices.dto.SummaryDto
import io.reactivex.Single

interface SummaryRemoteLoader {
    fun loadSummary(): Single<SummaryDto>
}

package com.jeff.covidtracker.main.mapper

import com.jeff.covidtracker.database.local.*
import com.jeff.covidtracker.webservices.dto.CasesDto
import com.jeff.covidtracker.webservices.dto.CountryDto
import com.jeff.covidtracker.webservices.dto.PhotoDto
import io.reactivex.Observable
import io.reactivex.functions.Function
import timber.log.Timber

class CasesDtoToCasesMapper : Function<CasesDto, Observable<Cases>> {

    @Throws(Exception::class)
    override fun apply(casesDto: CasesDto): Observable<Cases> {
        return Observable.fromCallable {
            val cases: Cases
            if (casesDto.active != null) {
                cases = Cases(
                    casesDto.countryCode,
                    casesDto.country,
                    casesDto.date!!,
                    Cases.CountryDetails(
                        casesDto.province,
                        casesDto.city,
                        casesDto.cityCode,
                        casesDto.lat,
                        casesDto.lon),
                    null,
                    TotalCases(
                        casesDto.confirmed!!,
                        casesDto.deaths!!,
                        casesDto.recovered!!,
                        casesDto.active)
                )

            } else {
                cases = Cases(
                    casesDto.countryCode,
                    casesDto.country,
                    casesDto.date!!,
                    null,
                    NewCases(
                        casesDto.slug,
                        casesDto.newConfirmed,
                        casesDto.newDeaths,
                        casesDto.newRecovered
                    ),
                    TotalCases(
                        casesDto.totalConfirmed!!,
                        casesDto.totalDeaths!!,
                        casesDto.totalRecovered!!
                    )
                )
            }

            cases
        }
    }
}
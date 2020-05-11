package com.jeff.covidtracker.main.mapper

import com.jeff.covidtracker.database.local.Cases
import com.jeff.covidtracker.database.local.Country
import com.jeff.covidtracker.database.local.Photo
import com.jeff.covidtracker.webservices.dto.CasesDto
import com.jeff.covidtracker.webservices.dto.CountryDto
import com.jeff.covidtracker.webservices.dto.PhotoDto
import io.reactivex.Observable
import io.reactivex.functions.Function

class CasesDtoToCasesMapper : Function<CasesDto, Observable<Cases>> {

    @Throws(Exception::class)
    override fun apply(casesDto: CasesDto): Observable<Cases> {
        return Observable.fromCallable {
            val cases = Cases(
                casesDto.country,
                casesDto.countryCode,
                casesDto.province,
                casesDto.city,
                casesDto.cityCode,
                casesDto.lat,
                casesDto.lon,
                casesDto.confirmed,
                casesDto.deaths,
                casesDto.recovered,
                casesDto.active,
                casesDto.date
            )
            cases
        }
    }
}
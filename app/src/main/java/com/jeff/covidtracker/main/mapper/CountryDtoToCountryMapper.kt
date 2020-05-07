package com.jeff.covidtracker.main.mapper

import com.jeff.covidtracker.database.local.Country
import com.jeff.covidtracker.database.local.Photo
import com.jeff.covidtracker.webservices.dto.CountryDto
import com.jeff.covidtracker.webservices.dto.PhotoDto
import io.reactivex.Observable
import io.reactivex.functions.Function

class CountryDtoToCountryMapper : Function<CountryDto, Observable<Country>> {

    @Throws(Exception::class)
    override fun apply(countryDto: CountryDto): Observable<Country> {
        return Observable.fromCallable {
            val country = Country(
                countryDto.country,
                countryDto.slug,
                countryDto.iso2
            )
            country
        }
    }
}
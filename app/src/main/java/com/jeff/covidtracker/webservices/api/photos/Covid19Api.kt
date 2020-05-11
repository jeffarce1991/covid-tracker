package com.jeff.covidtracker.webservices.api.photos

import com.jeff.covidtracker.webservices.dto.CasesDto
import com.jeff.covidtracker.webservices.dto.CountryDto
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface Covid19Api {

    @GET("countries")
    fun loadCountries(): Single<Response<List<CountryDto>>>

    @GET("total/country/{iso2}")
    fun loadCountryCasesByIso2(
        @Path("iso2") iso2: String): Single<Response<List<CasesDto>>>

    @GET("total/country/{slug}")
    fun loadCasesBySlug(
        @Path("slug") slug: String): Single<Response<List<CasesDto>>>
}
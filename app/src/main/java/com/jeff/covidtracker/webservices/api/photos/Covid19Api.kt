package com.jeff.covidtracker.webservices.api.photos

import com.jeff.covidtracker.webservices.dto.CountryDto
import com.jeff.covidtracker.webservices.dto.PhotoDto
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET

interface Covid19Api {

    @GET("countries")
    fun loadCountries(): Single<Response<List<CountryDto>>>
}
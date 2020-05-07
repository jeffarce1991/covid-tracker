package com.jeff.covidtracker.webservices.usecase.loader

import com.jeff.covidtracker.webservices.api.ApiFactory
import com.jeff.covidtracker.webservices.api.photos.Covid19Api
import com.jeff.covidtracker.webservices.api.photos.PhotosApi
import com.jeff.covidtracker.webservices.dto.CountryDto
import com.jeff.covidtracker.webservices.dto.PhotoDto
import com.jeff.covidtracker.webservices.transformer.NullResultTransformer
import com.jeff.covidtracker.webservices.transformer.ResponseCodeNot200SingleTransformer
import io.reactivex.Single
import javax.inject.Inject

class DefaultCountryRemoteLoader @Inject
constructor(private val apiFactory: ApiFactory): CountryRemoteLoader {

    override fun loadAll(): Single<List<CountryDto>> {
        return apiFactory.create(Covid19Api::class.java)
            .flatMap { it.loadCountries() }
            .compose(ResponseCodeNot200SingleTransformer())
            .compose(NullResultTransformer())
            .flatMap { response ->
                Single.just(response.body()!!) }
    }

}

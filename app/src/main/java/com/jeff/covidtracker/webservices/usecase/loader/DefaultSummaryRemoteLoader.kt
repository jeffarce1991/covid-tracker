package com.jeff.covidtracker.webservices.usecase.loader

import com.jeff.covidtracker.webservices.api.ApiFactory
import com.jeff.covidtracker.webservices.api.photos.Covid19Api
import com.jeff.covidtracker.webservices.dto.SummaryDto
import com.jeff.covidtracker.webservices.transformer.NullResultTransformer
import com.jeff.covidtracker.webservices.transformer.ResponseCodeNot200SingleTransformer
import io.reactivex.Single
import javax.inject.Inject

class DefaultSummaryRemoteLoader @Inject
constructor(private val apiFactory: ApiFactory): SummaryRemoteLoader {

    override fun loadSummary(): Single<SummaryDto> {
        return apiFactory.create(Covid19Api::class.java)
            .flatMap { it.loadSummary() }
            .compose(ResponseCodeNot200SingleTransformer())
            .compose(NullResultTransformer())
            .flatMap { response ->
                Single.just(response.body()!!) }
    }

}

package com.jeff.covidtracker.webservices.usecase.loader

import com.jeff.covidtracker.webservices.dto.PhotoDto
import io.reactivex.Single

interface PhotoRemoteLoader {

    fun loadAll(): Single<List<PhotoDto>>
}
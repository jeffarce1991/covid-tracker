package com.jeff.covidtracker.database.usecase.local.loader

import com.jeff.covidtracker.database.local.Photo
import io.reactivex.Single

interface PhotoLocalLoader {
    fun loadAll(): Single<List<Photo>>
}
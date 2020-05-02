package com.jeff.covidtracker.supplychain.photo

import com.jeff.covidtracker.database.local.Photo
import io.reactivex.Single

interface PhotoLoader {

    fun loadAll(): Single<List<Photo>>

    fun loadAllFromLocal(): Single<List<Photo>>
}
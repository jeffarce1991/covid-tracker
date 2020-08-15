package com.jeff.covidtracker.supplychain.photo

import com.jeff.covidtracker.database.local.Photo
import io.reactivex.Single

interface PhotoLoader {

    fun loadAllFromRemote(): Single<List<Photo>>

    fun loadAllFromLocal(): Single<List<Photo>>
}
package com.jeff.covidtracker.database.usecase.local.saver

import com.jeff.covidtracker.database.local.Country
import com.jeff.covidtracker.database.local.Photo
import io.reactivex.Completable
import io.reactivex.Observable

interface CountryLocalSaver {

    fun save(country: Country): Completable

    fun saveAll(countries: List<Country>): Observable<List<Country>>
}

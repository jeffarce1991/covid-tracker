package com.jeff.covidtracker.database.usecase.local.saver

import com.jeff.covidtracker.database.local.Country
import com.jeff.covidtracker.database.local.Photo
import com.jeff.covidtracker.database.room.dao.CountryDao
import io.reactivex.Completable
import io.reactivex.Observable
import timber.log.Timber
import javax.inject.Inject

class DefaultCountryLocalSaver @Inject
constructor(private val dao: CountryDao): CountryLocalSaver{

    override fun save(country: Country): Completable {
        TODO("Not yet implemented")
    }

    override fun saveAll(countries: List<Country>): Observable<List<Country>> {
        return Completable.fromCallable {
            dao.insert(countries)
            Completable.complete()
        }.andThen(Observable.fromCallable {
            Timber.d("==q Countries saved locally.")
            countries
        })
    }

}

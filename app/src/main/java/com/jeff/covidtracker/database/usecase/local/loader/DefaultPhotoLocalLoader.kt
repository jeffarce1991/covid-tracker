package com.jeff.covidtracker.database.usecase.local.loader

import com.jeff.covidtracker.database.local.Photo
import com.jeff.covidtracker.database.room.dao.PhotoDao
import io.reactivex.Single
import javax.inject.Inject

class DefaultPhotoLocalLoader @Inject
constructor(private val dao: PhotoDao): PhotoLocalLoader {

    override fun loadAll(): Single<List<Photo>> {
        return Single.fromCallable { dao.loadAll() }

    }

}
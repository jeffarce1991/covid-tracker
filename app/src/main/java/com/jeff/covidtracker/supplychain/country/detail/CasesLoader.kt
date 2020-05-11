package com.jeff.covidtracker.supplychain.country.detail

import com.jeff.covidtracker.database.local.Cases
import io.reactivex.Single

interface CasesLoader {
        fun loadAll(slug: String) : Single<List<Cases>>
        fun loadLast(slug: String) : Single<Cases>
        fun loadAllFromRemote(slug: String) : Single<List<Cases>>
        fun loadAllFromLocal(slug: String) : Single<List<Cases>>
}
package com.jeff.covidtracker.main.mapper

import com.jeff.covidtracker.database.local.*
import com.jeff.covidtracker.webservices.dto.CasesDto
import com.jeff.covidtracker.webservices.dto.CountryDto
import com.jeff.covidtracker.webservices.dto.GlobalCasesDto
import com.jeff.covidtracker.webservices.dto.PhotoDto
import io.reactivex.Observable
import io.reactivex.functions.Function
import timber.log.Timber

class GlobalCasesDtoToGlobalCasesMapper : Function<GlobalCasesDto, Observable<GlobalCases>> {

    @Throws(Exception::class)
    override fun apply(dto: GlobalCasesDto): Observable<GlobalCases> {
        return Observable.fromCallable {
            val globalCases = GlobalCases()

            globalCases.newCases!!.newConfirmed = dto.newConfirmed
            globalCases.newCases!!.newDeaths = dto.newDeaths
            globalCases.newCases!!.newRecovered = dto.newRecovered

            globalCases
        }
    }
}
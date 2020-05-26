package com.jeff.covidtracker.webservices.dto

import com.google.gson.annotations.SerializedName

data class SummaryDto (
    @field:SerializedName("Global") var global: GlobalDto,
    @field:SerializedName("Countries") var countries: List<CasesDto>,
    @field:SerializedName("Date") var date: String
)

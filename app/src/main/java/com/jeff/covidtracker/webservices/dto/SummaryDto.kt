package com.jeff.covidtracker.webservices.dto

import com.google.gson.annotations.SerializedName

data class SummaryDto (
    @field:SerializedName("Global") var globalCases: GlobalCasesDto,
    @field:SerializedName("Countries") var countryCases: List<CasesDto>,
    @field:SerializedName("Date") var date: String
)

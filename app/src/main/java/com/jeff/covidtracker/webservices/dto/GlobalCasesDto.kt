package com.jeff.covidtracker.webservices.dto

import com.google.gson.annotations.SerializedName

data class GlobalCasesDto (
    @field:SerializedName("NewConfirmed") var newConfirmed: Int,
    @field:SerializedName("TotalConfirmed") var totalConfirmed: Int,
    @field:SerializedName("NewDeaths") var newDeaths: Int,
    @field:SerializedName("TotalDeaths") var totalDeaths: Int,
    @field:SerializedName("NewRecovered") var newRecovered: Int,
    @field:SerializedName("TotalRecovered") var totalRecovered: Int
)
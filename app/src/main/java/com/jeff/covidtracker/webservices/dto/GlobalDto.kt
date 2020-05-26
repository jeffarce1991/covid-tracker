package com.jeff.covidtracker.webservices.dto

import com.google.gson.annotations.SerializedName

data class GlobalDto (
    @field:SerializedName("NewConfirmed") var newConfirmed: String,
    @field:SerializedName("TotalConfirmed") var totalConfirmed: String,
    @field:SerializedName("NewDeaths") var newDeaths: String,
    @field:SerializedName("TotalDeaths") var totalDeaths: String,
    @field:SerializedName("NewRecovered") var newRecovered: String,
    @field:SerializedName("TotalRecovered") var totalRecovered: String
)
package com.jeff.covidtracker.webservices.dto

import com.google.gson.annotations.SerializedName

data class CasesDto(
    @field:SerializedName("Country") var country: String,
    @field:SerializedName("CountryCode") var  countryCode: String,
    @field:SerializedName("Province") var province: String,
    @field:SerializedName("City") var city: String,
    @field:SerializedName("CityCode") var cityCode: String,
    @field:SerializedName("Lat") var lat: String,
    @field:SerializedName("Lon") var lon: String,
    @field:SerializedName("Confirmed") var confirmed: String,
    @field:SerializedName("Deaths") var deaths: String,
    @field:SerializedName("Recovered") var recovered: String,
    @field:SerializedName("Active") var active: String,
    @field:SerializedName("Date") var date: String
)


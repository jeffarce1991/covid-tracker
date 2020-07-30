package com.jeff.covidtracker.webservices.dto

import androidx.annotation.Nullable
import com.google.gson.annotations.SerializedName

data class CasesDto(


    @field:SerializedName("Country") var country: String,
    @field:SerializedName("CountryCode") var  countryCode: String,
    @Nullable
    @field:SerializedName("Slug") var  slug: String? = null,

    @field:SerializedName("Date") var date: String? = null,

    @Nullable
    @field:SerializedName("Province") var province: String? = null,
    @Nullable
    @field:SerializedName("City") var city: String? = null,
    @Nullable
    @field:SerializedName("CityCode") var cityCode: String? = null,
    @Nullable
    @field:SerializedName("Lat") var lat: String? = null,
    @Nullable
    @field:SerializedName("Lon") var lon: String? = null,
    @Nullable
    @field:SerializedName("Confirmed") var confirmed: Int? = null,
    @Nullable
    @field:SerializedName("Deaths") var deaths: Int? = null,
    @Nullable
    @field:SerializedName("Recovered") var recovered: Int? = null,
    @Nullable
    @field:SerializedName("Active") var active: Int? = null,

    @Nullable
    @field:SerializedName("NewConfirmed") var newConfirmed: Int? = null,
    @Nullable
    @field:SerializedName("TotalConfirmed") var totalConfirmed: Int? = null,
    @Nullable
    @field:SerializedName("NewDeaths") var newDeaths: Int? = null,
    @Nullable
    @field:SerializedName("TotalDeaths") var totalDeaths: Int? = null,
    @Nullable
    @field:SerializedName("NewRecovered") var newRecovered: Int? = null,
    @Nullable
    @field:SerializedName("TotalRecovered") var totalRecovered: Int? = null

) {
    constructor(): this("", "", "", null, null, null)
}


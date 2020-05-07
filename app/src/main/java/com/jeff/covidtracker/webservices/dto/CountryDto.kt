package com.jeff.covidtracker.webservices.dto

import com.google.gson.annotations.SerializedName

data class CountryDto(
    @field:SerializedName("Country") var country: String,
    @field:SerializedName("Slug") var  slug: String,
    @field:SerializedName("ISO2") var iso2: String)
package com.jeff.covidtracker.database.local

data class TotalCases(
    var totalConfirmed: Int,
    var totalDeaths: Int,
    var totalRecovered: Int,
    var totalActive: Int? = null
)
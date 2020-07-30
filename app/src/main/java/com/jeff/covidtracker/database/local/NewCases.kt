package com.jeff.covidtracker.database.local

data class NewCases(
    var slug: String? = null,
    var newConfirmed: Int? = null,
    var newDeaths: Int? = null,
    var newRecovered: Int? = null
) {}
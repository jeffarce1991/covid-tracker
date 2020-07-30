package com.jeff.covidtracker.database.local

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = Summary.TABLE_NAME)
data class Summary(
    @ColumnInfo(name = "global_cases")
    var global: GlobalCases?,
    @ColumnInfo(name = "countries_cases")
    var countries: List<Cases>,
    @ColumnInfo(name = "date")
    var date: String

){
    constructor(): this(null, emptyList(), "")

    companion object {

        const val TABLE_NAME = "summary"
    }

}
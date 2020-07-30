package com.jeff.covidtracker.database.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = GlobalCases.TABLE_NAME)
data class GlobalCases (

    @PrimaryKey
    @ColumnInfo(name = "date")
    var date: String,
    @ColumnInfo(name = "new_cases")
    var newCases: NewCases? = null,
    @ColumnInfo(name = "total_cases")
    var totalCases: TotalCases? = null
) {
    constructor(): this("", null, null)

    companion object {


        const val COLUMN_DEAL_ID = "global_cases_id"
        const val COLUMN_ID = "id"
        const val TABLE_NAME = "global_cases"
    }

}
package com.jeff.covidtracker.database.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = Country.TABLE_NAME)
data class Country constructor(
                 @PrimaryKey
                 @ColumnInfo(name = "country")
                 var country: String,
                 @ColumnInfo(name = "slug")
                 var slug: String,
                 @ColumnInfo(name = "iso2")
                 var iso2: String) : Comparable<Country> {

    companion object {

        const val COLUMN_DEAL_ID = "country_id"
        const val COLUMN_ID = "id"
        const val TABLE_NAME = "country"
    }

    override fun compareTo(other: Country): Int {
        return other.compareTo(other)
    }
}

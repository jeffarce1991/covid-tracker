package com.jeff.covidtracker.database.room.dao

import androidx.room.*
import com.jeff.covidtracker.database.local.Cases
import com.jeff.covidtracker.database.local.Photo

@Dao
interface CasesDao {
    @Query("Select * FROM " + Cases.TABLE_NAME)
    fun loadAll(): List<Cases>

    @Query("SELECT * FROM " + Cases.TABLE_NAME +
            " WHERE " + Cases.COUNTRY_CODE +" LIKE :countryCode")
    fun loadByCountryCOde(countryCode: String): Cases

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cases: List<Cases>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cases: Cases)

    @Delete
    fun delete(cases: Cases)

}
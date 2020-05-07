package com.jeff.covidtracker.database.room.dao

import androidx.room.*
import com.jeff.covidtracker.database.local.Country
import com.jeff.covidtracker.database.local.Photo

@Dao
interface CountryDao {
    @Query("Select * FROM " + Country.TABLE_NAME)
    fun loadAll(): List<Country>

    /*@Query("Select * FROM " + Country.TABLE_NAME +
            " WHERE "+ Country.COLUMN_ID +" IN (:id)")
    fun loadAllByIds(id: IntArray): List<Country>

    @Query("SELECT * FROM " + Country.TABLE_NAME +
            " WHERE title LIKE :title AND title LIMIT 1")
    fun findByTitle(title: String): Country*/

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(countries: List<Country>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(country: Country)

    @Delete
    fun delete(country: Country)

}
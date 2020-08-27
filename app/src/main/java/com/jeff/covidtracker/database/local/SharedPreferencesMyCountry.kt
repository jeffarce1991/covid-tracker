package com.jeff.covidtracker.database.local

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesMyCountry constructor(context: Context) {
    private var pref: SharedPreferences? = null

    init {
        pref = context.getSharedPreferences(SHARED_PREF_COUNTRY_KEY, Context.MODE_PRIVATE)
    }

    fun get(): String? {
        return pref!!.getString(SHARED_PREF_COUNTRY_KEY, DEFAULT_VALUE)
    }

    fun put(sharedPreferencesMyCountry: SharedPreferencesMyCountry, countryCode: String) {
        val editor = sharedPreferencesMyCountry.pref!!.edit()
        editor.putString(SHARED_PREF_COUNTRY_KEY, countryCode)
        editor.apply()
    }
    companion object {
        const val DEFAULT_VALUE: String = "DEFAULT_VALUE"
        const val SHARED_PREF_COUNTRY_KEY = "SHARED_PREF_COUNTRY_KEY"


    }
}
package com.jeff.covidtracker.main.selector

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blongho.country_data.World
import com.jeff.covidtracker.R
import com.jeff.covidtracker.adapter.CountryCasesListAdapter
import com.jeff.covidtracker.adapter.SelectCountryAdapter
import com.jeff.covidtracker.database.local.Cases
import com.jeff.covidtracker.databinding.ActivityDashboardBinding
import com.jeff.covidtracker.databinding.ActivitySelectBinding
import com.jeff.covidtracker.main.detail.view.CountryDetailActivity
import com.jeff.covidtracker.main.list.view.MainActivity

class SelectActivity : AppCompatActivity() {

    private var adapter: SelectCountryAdapter = SelectCountryAdapter(this, emptyList())

    lateinit var binding : ActivitySelectBinding

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, SelectActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_select)

        World.init(this)

        World.getAllCountries()
        generateDataList(
            World.getAllCountries()
        )

        setToolbarTitle()
    }

    private fun setToolbarTitle() {
        setSupportActionBar(binding.toolbar)

        supportActionBar!!.title = "Select Country"
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    //Method to generate List of data using RecyclerView with custom com.project.retrofit.adapter*//*
    private fun generateDataList(countries: List<com.blongho.country_data.Country>) {
        adapter = SelectCountryAdapter(this, countries)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this@SelectActivity)
        binding.selectCountryRecyclerView.layoutManager = layoutManager
        binding.selectCountryRecyclerView.adapter = adapter
        //adapter.sort()
        //setSearchQueryListener(cases)
    }
}
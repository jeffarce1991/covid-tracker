package com.jeff.covidtracker.main.list.view

import android.app.ProgressDialog
import android.app.ProgressDialog.*
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.mosby.mvp.MvpActivity
import com.jeff.covidtracker.R
import com.jeff.covidtracker.adapter.CountryListAdapter
import com.jeff.covidtracker.adapter.CustomAdapter
import com.jeff.covidtracker.android.base.extension.longToast
import com.jeff.covidtracker.database.local.Country
import com.jeff.covidtracker.database.local.Photo
import com.jeff.covidtracker.databinding.ActivityMainBinding
import com.jeff.covidtracker.main.list.presenter.MainPresenter
import dagger.android.AndroidInjection
import java.util.*
import javax.inject.Inject


class MainActivity : MvpActivity<MainView, MainPresenter>(), MainView {
    private lateinit var adapter: CustomAdapter
    private lateinit var countryListAdapter: CountryListAdapter
    private lateinit var progressDialog: ProgressDialog

    lateinit var mainBinding : ActivityMainBinding

    lateinit var photos : List<Photo>

    lateinit var countries : List<Country>


    @Inject
    internal lateinit var mainPresenter: MainPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        mainPresenter.getCountries()
    }

    private fun onSelectCountry(country: Country) {
        
    }

    //Method to generate List of data using RecyclerView with custom com.project.retrofit.adapter*//*
    override fun generateDataList(photos: List<Photo>) {
        adapter = CustomAdapter(this, photos)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this@MainActivity)
        mainBinding.customRecyclerView.layoutManager = layoutManager
        mainBinding.customRecyclerView.adapter = adapter
    }

    //Method to generate List of data using RecyclerView with custom com.project.retrofit.adapter*//*
    override fun generateCountryList(countries: List<Country>) {
        countryListAdapter = CountryListAdapter(this, countries)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this@MainActivity)
        mainBinding.customRecyclerView.layoutManager = layoutManager
        mainBinding.customRecyclerView.adapter = countryListAdapter
    }

    override fun createPresenter(): MainPresenter {
        return mainPresenter
    }

    override fun hideProgress() {
        progressDialog.dismiss()
    }

    override fun showLoadingDataFailed() {
        longToast("Loading data failed")
        /*invokeSimpleDialog("Project420",
            "OK",
            "List is empty or null.")*/
    }

    override fun showToast(message: String) {
        longToast(message)
    }

    override fun showProgressRemote() {
        progressDialog = show(
            this,
            "Project420",
            "Loading data remotely...")
    }

    override fun showProgressLocal() {
        progressDialog = show(
            this,
            "Project420",
            "Loading data locally...")
    }
}

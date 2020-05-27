package com.jeff.covidtracker.main.list.view

import android.app.ProgressDialog
import android.app.ProgressDialog.show
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.mosby.mvp.MvpActivity
import com.jeff.covidtracker.R
import com.jeff.covidtracker.adapter.CountryCasesListAdapter
import com.jeff.covidtracker.android.base.extension.longToast
import com.jeff.covidtracker.database.local.Cases
import com.jeff.covidtracker.database.local.Country
import com.jeff.covidtracker.database.local.Photo
import com.jeff.covidtracker.databinding.ActivityMainBinding
import com.jeff.covidtracker.main.list.presenter.MainPresenter
import dagger.android.AndroidInjection
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList


class MainActivity : MvpActivity<MainView, MainPresenter>(), MainView {
    private lateinit var adapter: CountryCasesListAdapter
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
        setUpToolbarTitle()
        mainPresenter.loadCountryCases()


    }

    private fun setUpToolbarTitle() {
        setSupportActionBar(mainBinding.toolbar)

        supportActionBar!!.title = "Select Country"
    }

    //Method to generate List of data using RecyclerView with custom com.project.retrofit.adapter*//*
    override fun generateDataList(cases: List<Cases>) {
        adapter = CountryCasesListAdapter(this, cases)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this@MainActivity)
        mainBinding.customRecyclerView.layoutManager = layoutManager
        mainBinding.customRecyclerView.adapter = adapter
        adapter.sort()
        addTextChangedListener(cases)
    }

    private fun addTextChangedListener(countries: List<Cases>) {
        mainBinding.searchField
            .addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    filter(s.toString(), countries )
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }
            })
    }

    private fun filter(text: String,
                       countries: List<Cases>) {
        //new array list that will hold the filtered data
        val filteredCountries: ArrayList<Cases> = ArrayList()

        //looping through existing elements
        for (s in countries) {
            //if the existing elements contains the search input

            if (s.country.toLowerCase(Locale.getDefault()).contains(text.toLowerCase(Locale.getDefault())) ||
                s.countryCode.toLowerCase(Locale.getDefault()).contains(text.toLowerCase(Locale.getDefault()))
            ) {
                //adding the element to filtered list
                filteredCountries.add(s)
            }
        }

        //calling a method of the adapter class and passing the filtered list
        adapter.update(filteredCountries)
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
        //TEST COMMIT
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

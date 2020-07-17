package com.jeff.covidtracker.main.list.view

import android.app.ProgressDialog
import android.app.ProgressDialog.show
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.view.MenuItemCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blongho.country_data.World
import com.hannesdorfmann.mosby.mvp.MvpActivity
import com.jeff.covidtracker.R
import com.jeff.covidtracker.adapter.CountryCasesListAdapter
import com.jeff.covidtracker.android.base.extension.longToast
import com.jeff.covidtracker.android.base.extension.shortToast
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

    private lateinit var searchView: SearchView


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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        initializeSearchView(menu)

        return true
    }

    private fun initializeSearchView(menu: Menu?) {
        val searchItem: MenuItem = menu!!.findItem(R.id.action_search)
        searchView =
            MenuItemCompat.getActionView(searchItem) as SearchView
        searchView.setOnCloseListener { true }

        val searchPlate =
            searchView.findViewById(androidx.appcompat.R.id.search_src_text) as EditText
        searchPlate.hint = "Country, Country Code"
        searchPlate.setHintTextColor(resources.getColor(R.color.light_gray))
        searchPlate.setTextColor(resources.getColor(R.color.white))

        val searchPlateView: View = searchView.findViewById(androidx.appcompat.R.id.search_plate)
        searchPlateView.setBackgroundColor(
            ContextCompat.getColor(
                this,
                android.R.color.transparent
            )
        )

    }

    private fun setSearchQueryListener(list: List<Cases>) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // do your logic here
                shortToast("Submitted $query")
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filter(newText!!, list)
                return false
            }
        })
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
        setSearchQueryListener(cases)
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

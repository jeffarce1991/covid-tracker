package com.jeff.covidtracker.main.list.view

import android.app.ProgressDialog
import android.app.ProgressDialog.show
import android.os.Bundle
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
import com.google.android.material.snackbar.Snackbar
import com.hannesdorfmann.mosby.mvp.MvpActivity
import com.jeff.covidtracker.R
import com.jeff.covidtracker.adapter.CountryCasesListAdapter
import com.jeff.covidtracker.android.base.extension.hide
import com.jeff.covidtracker.android.base.extension.shortToast
import com.jeff.covidtracker.android.base.extension.show
import com.jeff.covidtracker.database.local.Cases
import com.jeff.covidtracker.database.local.Country
import com.jeff.covidtracker.database.local.Photo
import com.jeff.covidtracker.databinding.ActivityMainBinding
import com.jeff.covidtracker.main.list.presenter.MainPresenter
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList


class MainActivity : MvpActivity<MainView, MainPresenter>(), MainView {
    private var adapter: CountryCasesListAdapter = CountryCasesListAdapter(this, emptyList())
    private lateinit var progressDialog: ProgressDialog

    lateinit var mainBinding : ActivityMainBinding

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
        setOnRefreshListener()
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

    private fun setOnRefreshListener() {
        mainBinding.swipeRefreshLayout.setOnRefreshListener {
            mainPresenter.loadCountryCases()
        }
    }

    //Method to generate List of data using RecyclerView with custom com.project.retrofit.adapter*//*
    override fun generateDataList(cases: List<Cases>) {
        hideErrorImage()
        adapter = CountryCasesListAdapter(this, cases)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this@MainActivity)
        mainBinding.countryRecyclerView.layoutManager = layoutManager
        mainBinding.countryRecyclerView.adapter = adapter
        adapter.sort()
        setSearchQueryListener(cases)
    }

    override fun clearDataList() {
        adapter.clear()
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
    override fun showNoInternetError() {
        Snackbar.make(mainBinding.coordLayout,
            resources.getString(R.string.no_internet_connection),
            Snackbar.LENGTH_LONG)
            .setAction("Load cache") { presenter.loadCountryCasesLocally() }
            .setActionTextColor(resources.getColor(R.color.green))
            .show()

        showErrorImage()
    }

    override fun showSessionTimeoutError() {
        Snackbar.make(mainBinding.coordLayout,
            resources.getString(R.string.session_timeout_exception),
            Snackbar.LENGTH_LONG)
            .setAction("Load cache") { presenter.loadCountryCasesLocally() }
            .setActionTextColor(resources.getColor(R.color.green))
            .show()

        showErrorImage()
    }

    override fun showEmptyListError() {
        Snackbar.make(mainBinding.coordLayout,
            resources.getString(R.string.no_data_saved_locally),
            Snackbar.LENGTH_LONG)
            .show()

        showErrorImage()
    }

    override fun showError(message: String) {
        Snackbar.make(mainBinding.coordLayout,
            message,
            Snackbar.LENGTH_INDEFINITE)
            .show()

        showErrorImage()
    }

    override fun showLoadedLocally() {
        //longToast(message)
        Snackbar.make(mainBinding.coordLayout,
            resources.getString(R.string.loaded_data_from_cache),
            Snackbar.LENGTH_SHORT)
            .show()
    }

    override fun showLoadedRemotely() {
        //longToast(message)
        Snackbar.make(mainBinding.coordLayout,
            resources.getString(R.string.loaded_data_from_remote),
            Snackbar.LENGTH_SHORT)
            .show()
    }

    override fun hideProgress() {
        //mainBinding.progressBar.hide()
        swipe_refresh_layout.isRefreshing = false
    }

    override fun showProgress() {
        //mainBinding.progressBar.show()
        swipe_refresh_layout.isRefreshing = true
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

    private fun hideErrorImage() {
        mainBinding.errorImage.hide()
        mainBinding.errorMessage.hide()
    }
    private fun showErrorImage() {
        mainBinding.errorImage.show()
        mainBinding.errorMessage.show()
    }
}


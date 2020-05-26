package com.jeff.covidtracker.main.detail.view

import android.app.ProgressDialog
import android.app.ProgressDialog.show
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.hannesdorfmann.mosby.mvp.MvpActivity
import com.jeff.covidtracker.R
import com.jeff.covidtracker.database.local.Cases
import com.jeff.covidtracker.databinding.ActivityCountryDetailBinding
import com.jeff.covidtracker.main.detail.presenter.DefaultCountryDetailPresenter
import com.jeff.covidtracker.utilities.extensions.toDisplay
import dagger.android.AndroidInjection
import javax.inject.Inject

class CountryDetailActivity : MvpActivity<CountryDetailView, DefaultCountryDetailPresenter>(),
    CountryDetailView {

    @Inject
    internal lateinit var countryDetailPresenter: DefaultCountryDetailPresenter

    private lateinit var progressDialog: ProgressDialog

    lateinit var binding : ActivityCountryDetailBinding

    companion object {
        private var EXTRA_COUNTRY_NAME = "EXTRA_COUNTRY_NAME"
        private var EXTRA_COUNTRY_SLUG = "EXTRA_COUNTRY_SLUG"
        private var EXTRA_COUNTRY_ISO2 = "EXTRA_COUNTRY_ISO2"

        fun getStartIntent(
            context: Context,
            country : String,
            slug : String,
            iso2 : String
        ): Intent {
            return Intent(context, CountryDetailActivity::class.java)
                .putExtra(EXTRA_COUNTRY_NAME, country)
                .putExtra(EXTRA_COUNTRY_SLUG, slug)
                .putExtra(EXTRA_COUNTRY_ISO2, iso2)
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_country_detail)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_country_detail)

        setUpToolbarTitle()
        countryDetailPresenter.loadAllCases(intent.getStringExtra(EXTRA_COUNTRY_SLUG))


    }

    private fun setUpToolbarTitle() {
        setSupportActionBar(binding.countryDetailToolbar)

        supportActionBar!!.title = intent.getStringExtra(EXTRA_COUNTRY_NAME).capitalize()
        binding.countryDetailToolbar.setNavigationOnClickListener { onBackPressed() }
    }

    override fun createPresenter(): DefaultCountryDetailPresenter {
        return countryDetailPresenter
    }

    override fun setCases(cases: List<Cases>) {
        val yesterday = cases[cases.lastIndex - 1]
        val total = cases.last()
        binding.countryDate.text = String.format("As of ${total.date.toDisplay("MMM dd, yyyy")}")
        binding.countryConfirmedTotal.text = total.totalCases!!.totalConfirmed.toString()
        binding.countryConfirmedToday.text = get(total.totalCases!!.totalConfirmed,
                                                 yesterday.totalCases!!.totalConfirmed)

        binding.countryDeathsTotal.text = total.totalCases!!.totalDeaths.toString()
        binding.countryDeathsToday.text = get(total.totalCases!!.totalDeaths,
                                              yesterday.totalCases!!.totalDeaths)

        binding.countryRecoveredTotal.text = total.totalCases!!.totalRecovered.toString()
        binding.countryRecoveredToday.text = get(total.totalCases!!.totalRecovered,
                                                 yesterday.totalCases!!.totalRecovered)

        binding.countryActiveTotal.text = total.totalCases!!.totalActive.toString()
        binding.countryActiveToday.text = get(total.totalCases!!.totalActive,
                                              yesterday.totalCases!!.totalActive)
    }

    fun get(x: Int?, y: Int?): String{
        return String.format("+${(x!! - y!!)}")
    }



    override fun hideProgress() {
        progressDialog.dismiss()
    }

    override fun showProgress() {
        progressDialog = show(
            this,
            getString(R.string.app_name),
            "Loading Cases...")
    }

}

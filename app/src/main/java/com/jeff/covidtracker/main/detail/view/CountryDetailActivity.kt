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
        private var EXTRA_COUNTRY_COUNTRY = "EXTRA_COUNTRY_COUNTRY"
        private var EXTRA_COUNTRY_SLUG = "EXTRA_COUNTRY_SLUG"
        private var EXTRA_COUNTRY_ISO2 = "EXTRA_COUNTRY_ISO2"

        fun getStartIntent(
            context: Context,
            country : String,
            slug : String,
            iso2 : String
        ): Intent {
            return Intent(context, CountryDetailActivity::class.java)
                .putExtra(EXTRA_COUNTRY_COUNTRY, country)
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

        supportActionBar!!.title = intent.getStringExtra(EXTRA_COUNTRY_SLUG).capitalize()
        binding.countryDetailToolbar.setNavigationOnClickListener { onBackPressed() }
    }

    override fun createPresenter(): DefaultCountryDetailPresenter {
        return countryDetailPresenter
    }

    override fun setCases(cases: List<Cases>) {
        val yesterday = cases[cases.lastIndex - 1]
        val total = cases.last()
        binding.countryDate.text = String.format("As of ${total.date.toDisplay("MMM dd, yyyy")}")
        binding.countryConfirmedTotal.text = total.confirmed
        binding.countryConfirmedToday.text = get(total.confirmed, yesterday.confirmed)

        binding.countryDeathsTotal.text = total.deaths
        binding.countryDeathsToday.text = get(total.deaths, yesterday.deaths)

        binding.countryRecoveredTotal.text = total.recovered
        binding.countryRecoveredToday.text = get(total.recovered, yesterday.recovered)

        binding.countryActiveTotal.text = total.active
        binding.countryActiveToday.text = get(total.active, yesterday.active)
    }

    fun get(x: String, y: String): String{
        return String.format("+${(x.toInt() - y.toInt())}")
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

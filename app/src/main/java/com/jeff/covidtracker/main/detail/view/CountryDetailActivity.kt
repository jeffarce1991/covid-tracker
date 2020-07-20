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
import kotlinx.android.synthetic.main.item_country.*
import javax.inject.Inject

class CountryDetailActivity : MvpActivity<CountryDetailView, DefaultCountryDetailPresenter>(),
    CountryDetailView {

    @Inject
    internal lateinit var countryDetailPresenter: DefaultCountryDetailPresenter

    private lateinit var progressDialog: ProgressDialog

    lateinit var binding : ActivityCountryDetailBinding

    companion object {
        var EXTRA_COUNTRY_NAME = "EXTRA_COUNTRY_NAME"
        var EXTRA_COUNTRY_CODE = "EXTRA_COUNTRY_CODE"
        var EXTRA_COUNTRY_ISO2 = "EXTRA_COUNTRY_ISO2"

        fun getStartIntent(
            context: Context,
            country : String,
            countryCode : String,
            iso2 : String
        ): Intent {
            return Intent(context, CountryDetailActivity::class.java)
                .putExtra(EXTRA_COUNTRY_NAME, country)
                .putExtra(EXTRA_COUNTRY_CODE, countryCode)
                .putExtra(EXTRA_COUNTRY_ISO2, iso2)
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_country_detail)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_country_detail)

        setToolbarTitle()
        val countryCode = intent.getStringExtra(EXTRA_COUNTRY_CODE)
        if (countryCode != null) {
            countryDetailPresenter.loadCases(countryCode)
        }
    }


    override fun setToolbarTitle() {
        setSupportActionBar(binding.countryDetailToolbar)

        var countryName = intent.getStringExtra(EXTRA_COUNTRY_NAME)
        countryName = countryName?.capitalize() ?: getString(R.string.missing_country_name)
        supportActionBar!!.title = countryName
        binding.countryDetailToolbar.setNavigationOnClickListener { onBackPressed() }

    }

    override fun createPresenter(): DefaultCountryDetailPresenter {
        return countryDetailPresenter
    }

    override fun setCases(cases: Cases) {
        binding.countryDate.text = String.format("As of ${cases.date.toDisplay("MMM dd, yyyy")}")
        binding.countryConfirmedTotal.text = cases.totalCases!!.totalConfirmed.toString()
        binding.countryConfirmedToday.text = get(cases.newCases!!.newConfirmed)

        binding.countryDeathsTotal.text = cases.totalCases!!.totalDeaths.toString()
        binding.countryDeathsToday.text = get(cases.newCases!!.newDeaths)

        binding.countryRecoveredTotal.text = cases.totalCases!!.totalRecovered.toString()
        binding.countryRecoveredToday.text = get(cases.newCases!!.newRecovered)
    }

    fun get(x: Int?, y: Int?): String{
        return String.format("+${(x!! - y!!)}")
    }

    fun get(x: Int?): String{
        return String.format("+${x!!}")
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

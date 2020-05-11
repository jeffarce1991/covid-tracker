package com.jeff.covidtracker.main.detail.view

import android.app.ProgressDialog
import android.app.ProgressDialog.show
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.hannesdorfmann.mosby.mvp.MvpActivity
import com.jeff.covidtracker.BuildConfig
import com.jeff.covidtracker.R
import com.jeff.covidtracker.database.local.Cases
import com.jeff.covidtracker.databinding.ActivityCountryDetailBinding
import com.jeff.covidtracker.main.detail.presenter.DefaultCountryDetailPresenter
import dagger.android.AndroidInjection
import javax.inject.Inject

class CountryDetailActivity : MvpActivity<CountryDetailView, DefaultCountryDetailPresenter>(),
    CountryDetailView {

    @Inject
    internal lateinit var countryDetailPresenter: DefaultCountryDetailPresenter

    private lateinit var progressDialog: ProgressDialog

    lateinit var binding : ActivityCountryDetailBinding

    companion object {
        private var EXTRA_COUNTRY_SLUG = "EXTRA_COUNTRY_SLUG"

        fun getStartIntent(
            context: Context,
            slug : String
        ): Intent {
            return Intent(context, CountryDetailActivity::class.java)
                .putExtra(EXTRA_COUNTRY_SLUG, slug)
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_country_detail)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_country_detail)

        setUpToolbarTitle()
        countryDetailPresenter.loadLatestCases(intent.getStringExtra(EXTRA_COUNTRY_SLUG))


    }

    private fun setUpToolbarTitle() {
        /*setSupportActionBar(binding.countryDetailToolbar)

        binding.countryDetailToolbar.setNavigationOnClickListener { onBackPressed() }*/
    }

    override fun createPresenter(): DefaultCountryDetailPresenter {
        return countryDetailPresenter
    }

    override fun setCases(cases: Cases) {
        binding.countryConfirmedCount.text = cases.confirmed
        binding.countryDeathsCount.text = cases.deaths
        binding.countryRecoveredCount.text = cases.recovered
        binding.countryActiveCount.text = cases.active
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

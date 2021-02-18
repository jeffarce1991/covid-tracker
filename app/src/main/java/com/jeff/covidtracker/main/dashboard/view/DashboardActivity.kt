package com.jeff.covidtracker.main.dashboard.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.hannesdorfmann.mosby.mvp.MvpActivity
import com.jeff.covidtracker.R
import com.jeff.covidtracker.adapter.SelectCountryAdapter
import com.jeff.covidtracker.android.base.extension.hide
import com.jeff.covidtracker.android.base.extension.numbersWithComma
import com.jeff.covidtracker.android.base.extension.show
import com.jeff.covidtracker.database.local.Cases
import com.jeff.covidtracker.database.local.SharedPreferencesMyCountry
import com.jeff.covidtracker.databinding.ActivityDashboardBinding
import com.jeff.covidtracker.main.dashboard.presenter.DashboardPresenter
import com.jeff.covidtracker.main.list.view.MainActivity
import com.jeff.covidtracker.main.selector.SelectActivity
import dagger.android.AndroidInjection
import timber.log.Timber
import javax.inject.Inject

class DashboardActivity : MvpActivity<DashboardView,
        DashboardPresenter>(), DashboardView {

    lateinit var binding : ActivityDashboardBinding

    var SELECT_COUNTRY_REQUEST_CODE = 1

    @Inject
    internal lateinit var dashboardPresenter: DashboardPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard)
        setToolbarTitle()
        binding.seeAllCases.setOnClickListener { navigateToCountryCasesList() }

        refreshMyCountryView()
        dashboardPresenter.loadCases(getSelectedMyCountry())

        binding.selectMyCountryCardView.setOnClickListener {
            navigateToSelectCountryActivity()
        }

        binding.editMyCountry.setOnClickListener {
            navigateToSelectCountryActivity()
        }
    }

    private fun navigateToSelectCountryActivity() {
        startActivityForResult(SelectActivity.getStartIntent(this), SELECT_COUNTRY_REQUEST_CODE)
    }

    private fun refreshMyCountryView() {
        if (getSelectedMyCountry() == SharedPreferencesMyCountry.DEFAULT_VALUE) {
            showSelectMyCountryView()
        } else {
            //binding.myCountry.text = getSelectedMyCountry()
            showMyCountryView()
        }

        Timber.d("==q " + getSelectedMyCountry())
    }

    private fun getSelectedMyCountry(): String {
        val myCountryPrefs = SharedPreferencesMyCountry(this)
        return myCountryPrefs.get()!!
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SELECT_COUNTRY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                refreshMyCountryView()
                dashboardPresenter.loadCases(getSelectedMyCountry())
                binding.myCountry.text = data!!.getStringExtra(SelectCountryAdapter.SELECT_NAME_KEY)
            }
        }
    }

    private fun setToolbarTitle() {
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.title = "Dashboard"
    }

    override fun createPresenter(): DashboardPresenter = dashboardPresenter

    override fun navigateToCountryCasesList() {
        val intent = MainActivity.getStartIntent(this)
        this.startActivity(intent)
    }

    override fun showSelectMyCountryView() {
        binding.selectMyCountryCardView.show()
        binding.myCountryCardView.hide()
    }

    override fun showMyCountryView() {
        binding.selectMyCountryCardView.hide()
        binding.myCountryCardView.show()
    }

    override fun setMyCountryCases(cases: Cases) {
        binding.myCountry.text = cases.country
        binding.confirmedCases.text =
            String.numbersWithComma(cases.totalCases!!.totalConfirmed)
        binding.newConfirmedCases.text =
            String.numbersWithComma(cases.newCases!!.newConfirmed!!)
    }

    override fun navigateToSelectMyCountryActivity(countryList: ArrayList<String>) {
        TODO("Not yet implemented")
    }


}
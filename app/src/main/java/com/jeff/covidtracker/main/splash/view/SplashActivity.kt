package com.jeff.covidtracker.main.splash.view

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import com.hannesdorfmann.mosby.mvp.MvpActivity
import com.jeff.covidtracker.R
import com.jeff.covidtracker.databinding.ActivitySplashBinding
import com.jeff.covidtracker.main.dashboard.view.DashboardActivity
import com.jeff.covidtracker.main.splash.presenter.SplashPresenter
import dagger.android.AndroidInjection
import javax.inject.Inject

class SplashActivity : MvpActivity<SplashView, SplashPresenter>(), SplashView {

    private lateinit var binding: ActivitySplashBinding

    @Inject
    lateinit var splashPresenter: SplashPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        splashPresenter.decideWhichScreenToRedirectTo()
    }

    override fun createPresenter(): SplashPresenter {
        return splashPresenter
    }

    override fun navigateToDashboardScreen() {
        val intent = Intent(this, DashboardActivity::class.java)
        intent.addFlags(
            Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                    or FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }

    override fun showError(message: String) {
        Snackbar.make(binding.coordLayout,
            message,
            Snackbar.LENGTH_INDEFINITE)
            .setAction("Retry") {splashPresenter.decideWhichScreenToRedirectTo()}
            .show()
    }
}
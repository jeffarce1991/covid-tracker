package com.jeff.covidtracker

import android.app.Activity
import android.app.Application
import android.app.Service
import androidx.fragment.app.Fragment
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.HasServiceInjector
import dagger.android.support.HasSupportFragmentInjector
import timber.log.Timber
import javax.inject.Inject

open class MyApplication : Application(), HasActivityInjector, HasServiceInjector,
    HasSupportFragmentInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var dispatchingServiceInjector: DispatchingAndroidInjector<Service>

    @Inject
    lateinit var dispatchingSupportFragmentInjector: DispatchingAndroidInjector<Fragment>


    /*open val component: AppComponent by lazy {
        DaggerAppComponent.builder()
            .application(this)
            .build()
    }*/
    override fun onCreate() {
        super.onCreate()

        //initializeCalligraphy()

        if (BuildConfig.DEBUG) {
            initializeTimber()
        }

        initializeComponent()
            .inject(this)

    }

    open fun initializeComponent(): AppComponent {
        return DaggerAppComponent.builder().application(this).build()
    }

    override fun activityInjector(): AndroidInjector<Activity> {
        return dispatchingAndroidInjector
    }

    override fun serviceInjector(): AndroidInjector<Service> {
        return dispatchingServiceInjector
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return dispatchingSupportFragmentInjector
    }

    private fun initializeTimber() {
        Timber.plant(Timber.DebugTree())
    }

}
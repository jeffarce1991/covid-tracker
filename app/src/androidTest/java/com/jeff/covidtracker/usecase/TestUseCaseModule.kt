package com.jeff.covidtracker.usecase

import com.jeff.covidtracker.supplychain.country.detail.CasesLoader
import com.jeff.covidtracker.supplychain.country.list.CountryLoader
import com.jeff.covidtracker.supplychain.country.list.SummaryLoader
import com.jeff.covidtracker.webservices.internet.RxInternet
import dagger.Module
import dagger.Provides
import io.reactivex.Completable
import io.reactivex.Single
import org.mockito.BDDMockito
import org.mockito.BDDMockito.*
import org.mockito.Mockito
import javax.inject.Singleton

/**
 * Provides fake and mocked classes of the dependencies on our object graph, allowing us to stub
 * their methods on UI tests.
 */
@Module()
class TestUseCaseModule {

    @Provides
    @Singleton
    internal fun provideInternet(): RxInternet {
        val mock = Mockito.mock(RxInternet::class.java)

        given(mock.connected()).willReturn(Completable.error(unstubbed()))
        given(mock.notConnected()).willReturn(Completable.error(unstubbed()))

        return mock
    }

    @Provides
    @Singleton
    internal fun provideSummaryLoader(): SummaryLoader {
        val deviceLoader = Mockito.mock(SummaryLoader::class.java)

        given(deviceLoader.loadCountryCases()).willReturn(Single.error(unstubbed()))
        given(deviceLoader.loadCountryCasesRemotely()).willReturn(Single.error(unstubbed()))
        given(deviceLoader.loadCountryCasesLocally()).willReturn(Single.error(unstubbed()))
        given(deviceLoader.loadGlobal()).willReturn(Single.error(unstubbed()))

        return deviceLoader
    }

    @Provides
    @Singleton
    internal fun provideCountryLoader(): CountryLoader {
        val loader = Mockito.mock(CountryLoader::class.java)

        given(loader.loadAll()).willReturn(Single.error(unstubbed()))

        return loader
    }

    @Provides
    @Singleton
    internal fun provideCasesLoader(): CasesLoader {
        return Mockito.mock(CasesLoader::class.java)
    }

    private fun unstubbed() = Throwable("Function unstubbed!")
}
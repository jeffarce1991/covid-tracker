package com.jeff.covidtracker.webservices.api;

import dagger.Binds;
import dagger.Module;

@Module
public interface ApiModule {

    @Binds
    @SuppressWarnings("unused")
    ApiFactory bindApiFactory(DefaultApiFactory apiFactory);
}




package com.pasha.efebudak.selectacar;

import com.pasha.efebudak.selectacar.data.CarTypesService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by efebudak on 11/05/2017.
 */

@Module
public class NetworkModule {

    public NetworkModule() {
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BuildConfig.SERVER_URL)
                .build();
    }

    @Provides
    @Singleton
    CarTypesService provideCarTypeService(Retrofit retrofit) {
        return retrofit.create(CarTypesService.class);
    }
}
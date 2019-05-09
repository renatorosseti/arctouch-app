package com.arctouch.codechallenge.di

import com.arctouch.codechallenge.api.TmdbApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
class NetworkModule {

    @Provides
    fun providesRetrofit(): TmdbApi = Retrofit.Builder()
            .baseUrl(TmdbApi.URL)
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(TmdbApi::class.java)

//    @Provides
//    internal fun providesOutputInteractor() = HomeContracts.InteractorOutput::class.java

//    @Provides
//    fun providesHomeInteractor(output: HomeContracts.InteractorOutput, api: TmdbApi, rxScheduler: AppRxSchedulers) = HomeInteractor(output, api, rxScheduler)
}
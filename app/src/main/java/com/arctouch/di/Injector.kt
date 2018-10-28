package com.arctouch.di


import com.arctouch.codechallenge.repository.GenreRepository
import com.arctouch.codechallenge.repository.MovieRepository
import com.arctouch.codechallenge.repository.api.TmdbApi
import com.arctouch.codechallenge.util.MovieUrlBuilder
import okhttp3.OkHttpClient
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import com.arctouch.codechallenge.viewModel.ViewModel

class Injector {
    val graph = Kodein.lazy {
        bind<TmdbApi>() with provider { Retrofit.Builder()
                .baseUrl(TmdbApi.URL)
                .client(OkHttpClient.Builder().build())
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(TmdbApi::class.java)
        }

        bind<GenreRepository>() with provider { GenreRepository(instance()) }

        bind<MovieRepository>() with provider { MovieRepository(instance()) }

        bind<ViewModel>() with singleton { ViewModel(instance(),instance()) }

        bind<MovieUrlBuilder>() with provider { MovieUrlBuilder() }
    }



}

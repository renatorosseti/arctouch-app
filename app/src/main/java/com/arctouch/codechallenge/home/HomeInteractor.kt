package com.arctouch.codechallenge.home

import android.util.Log
import com.arctouch.codechallenge.api.TmdbApi
import com.arctouch.codechallenge.data.AppRxSchedulers
import com.arctouch.codechallenge.data.Cache
import com.arctouch.codechallenge.model.GenreResponse
import com.arctouch.codechallenge.model.UpcomingMoviesResponse
import io.reactivex.Single
import javax.inject.Inject


class HomeInteractor @Inject constructor(val api: TmdbApi, val rxSchedulers: AppRxSchedulers) : HomeContracts.Interactor {

    override fun fetchMoviesData(): Single<UpcomingMoviesResponse> {
        Log.i("HomeInteractor", "fetchMoviesData")

        return api.genres(TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE)
                .subscribeOn(rxSchedulers.network)
                .observeOn(rxSchedulers.main)
                .flatMap { genresResponse: GenreResponse ->
                    Cache.cacheGenres(genresResponse.genres)
                    Log.i("HomeInteractor", "fetch Movies with genres")

                    api.upcomingMovies(TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE, 1, TmdbApi.DEFAULT_REGION)
                            .subscribeOn(rxSchedulers.network)
                            .observeOn(rxSchedulers.main)
                }
    }

}
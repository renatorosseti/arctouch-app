package com.arctouch.codechallenge.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.arctouch.codechallenge.model.UpcomingMoviesResponse
import com.arctouch.codechallenge.api.TmdbApi
import com.arctouch.codechallenge.data.Cache
import com.arctouch.codechallenge.model.Movie
import retrofit2.Call
import retrofit2.Response

class MovieRepository(private val api: TmdbApi) {

    fun fetchMovies(page : Long): LiveData<ArrayList<Movie>> {
        val data = MutableLiveData<ArrayList<Movie>>()
        var call: Call<UpcomingMoviesResponse> = api.upcomingMovies(TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE, page, TmdbApi.DEFAULT_REGION)
        call.enqueue(object : retrofit2.Callback<UpcomingMoviesResponse> {
            override fun onResponse(call: Call<UpcomingMoviesResponse>?, response: Response<UpcomingMoviesResponse>?) {
                if (response?.body()?.results != null) {
                    val moviesResponse = response.body()
                    val moviesWithGenres = moviesResponse?.results?.map { movie ->
                        movie.copy(genres = Cache.genres.filter { movie.genreIds?.contains(it.id) == true })
                    } as ArrayList<Movie>
                    data.value = moviesWithGenres
                }
            }

            override fun onFailure(call: Call<UpcomingMoviesResponse>?, t: Throwable?) {
                Log.e("MovieRepository","An exception error occurred due to ${t?.localizedMessage}")
            }

        })
        return data
    }
}
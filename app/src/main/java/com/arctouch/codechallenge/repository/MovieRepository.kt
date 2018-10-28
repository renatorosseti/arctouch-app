package com.arctouch.codechallenge.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.arctouch.codechallenge.model.UpcomingMoviesResponse
import com.arctouch.codechallenge.repository.api.TmdbApi
import com.arctouch.codechallenge.repository.data.Cache
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

    fun fetchMovieDetails(movieId: Long) : LiveData<Movie> {
        val data = MutableLiveData<Movie>()
        val call: Call<Movie> = api.movie(movieId,TmdbApi.API_KEY,TmdbApi.DEFAULT_LANGUAGE)
        call.enqueue(object : retrofit2.Callback<Movie> {
            override fun onResponse(call: Call<Movie>?, response: Response<Movie>?) {
                if (response?.body() != null) {
                    val movie = response.body()
                    movie?.copy(genres = Cache.genres.filter { movie.genreIds?.contains(it.id) == true })
                    data.value = movie
                }
            }

            override fun onFailure(call: Call<Movie>?, t: Throwable?) {
                Log.e("MovieRepository","An exception error occurred due to ${t?.localizedMessage}")
            }

        })
        return data
    }
}
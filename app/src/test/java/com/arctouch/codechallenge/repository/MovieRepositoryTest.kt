package com.arctouch.codechallenge.repository

import com.arctouch.codechallenge.api.TmdbApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito.*
import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.model.UpcomingMoviesResponse
import org.junit.Assert
import retrofit2.mock.Calls



class MovieRepositoryTest {
    lateinit var movieRepository: MovieRepository

    var api: TmdbApi = mock(TmdbApi::class.java)

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        movieRepository = MovieRepository(api)
    }

    @Test
    fun fetchMovies_whenASingleMovieIsRetrieved() {
        var page: Long = 1
        var arrayMovies = ArrayList<Movie>()
        arrayMovies.add(Movie(1,"test","overview test",null,null,null,null,null))

        val response = UpcomingMoviesResponse(1,arrayMovies,2,1)

        val call = Calls.response(response)
        `when`(api.upcomingMovies(TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE, 1, TmdbApi.DEFAULT_REGION)).thenReturn(call)
        var result = movieRepository.fetchMovies(1)
        Assert.assertEquals("Should return 1 when response contains a single movie.",1,result?.value?.size)

    }

    @Test
    fun fetchMovies_whenEmptyMoviesIsRetrieved() {
        var page: Long = 1
        var arrayMovies = ArrayList<Movie>()
        val response = UpcomingMoviesResponse(1,arrayMovies,2,1)

        val call = Calls.response(response)
        `when`(api.upcomingMovies(TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE, page, TmdbApi.DEFAULT_REGION)).thenReturn(call)
        var result = movieRepository.fetchMovies(1)
        Assert.assertEquals("Should return 0 when response contains no movies.",0,result?.value?.size)
    }

}
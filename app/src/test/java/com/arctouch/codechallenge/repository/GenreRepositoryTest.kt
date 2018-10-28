package com.arctouch.codechallenge.repository

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.arctouch.codechallenge.api.TmdbApi
import com.arctouch.codechallenge.data.Cache
import com.arctouch.codechallenge.model.Genre
import com.arctouch.codechallenge.model.GenreResponse
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.model.UpcomingMoviesResponse
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import retrofit2.mock.Calls
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.*
import retrofit2.Call


class GenreRepositoryTest {
    lateinit var genreRepository: GenreRepository

    var api: TmdbApi = Mockito.mock(TmdbApi::class.java)

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        genreRepository = GenreRepository(api)
    }

    @Test
    fun fetchGenres_whenASingleGenreIsRetrieved() {
        var page: Long = 1
        var arrayGenres = ArrayList<Genre>()
        arrayGenres.add(Genre(1,"Genre name"))

        val response = GenreResponse(arrayGenres)

        val call = Calls.response(response)
        Mockito.`when`(api.genres(TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE)).thenReturn(call)

        val result = genreRepository.fetchGenres()

        Assert.assertEquals("The size of the genre list in the Cache should be 1 when GenreResponse contains a single genre.",1,Cache.genres.size)
    }


}
package com.arctouch.codechallenge.viewModel

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.arctouch.codechallenge.repository.GenreRepository
import com.arctouch.codechallenge.repository.MovieRepository
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito

class ViewModelTest {

    lateinit var viewModel: ViewModel

    var genreRepository = Mockito.mock(GenreRepository::class.java)

    var movieRepository = Mockito.mock(MovieRepository::class.java)


    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        viewModel = ViewModel(genreRepository,movieRepository)
    }

    @Test
    fun fetchMovies() {
        Assert.assertEquals("Should return 0 for currentPage when fetchMovies did not requested.",0,viewModel.currentPage)
        viewModel.fetchMovies()

        Assert.assertEquals("Should return 1 for currentPage when fetchMovies has requested.",1,viewModel.currentPage)

        viewModel.fetchMovies()
        viewModel.fetchMovies()
        viewModel.fetchMovies()

        Assert.assertEquals("Should return 4 for currentPage when fetchMovies has requested 4 times.",4,viewModel.currentPage)
    }
}
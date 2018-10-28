package com.arctouch.codechallenge.view.home

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.arctouch.application.CodeChallengeApp
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.model.GenreResponse
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.repository.api.TmdbApi
import com.arctouch.codechallenge.view.movieDetails.MovieDetailsActivity
import com.arctouch.codechallenge.viewModel.ViewModel
import kotlinx.android.synthetic.main.home_activity.*
import org.jetbrains.anko.startActivity
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.direct
import org.kodein.di.generic.instance

class HomeActivity : AppCompatActivity(), KodeinAware {

    override lateinit var kodein: Kodein
    lateinit var api: TmdbApi
    lateinit var viewModel: ViewModel
    lateinit var linearManager: LinearLayoutManager
    var listMovies : ArrayList<Movie> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)
        kodein = (applicationContext as CodeChallengeApp).kodein
        api = kodein.direct.instance()
        viewModel = kodein.direct.instance()
    }

    override fun onStart() {
        super.onStart()
        setupRecyclerView()
        loadObservers()
    }

    private fun setupRecyclerView() {
        recyclerView.adapter = HomeAdapter(listMovies,this, kodein.direct.instance())
        linearManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearManager
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if(dy > 0) {
                    var visibleItemCount = linearManager.childCount
                    var totalItemCount = linearManager.itemCount
                    var pastVisibleItems = linearManager.findFirstCompletelyVisibleItemPosition()


                    if(pastVisibleItems + visibleItemCount > totalItemCount) {
                        progressBar.visibility = View.VISIBLE
                        fetchNewMovies()
                    }
                }
            }
        })

    }

    private fun fetchNewMovies() {
        viewModel.fetchMovies().observe(this, Observer<ArrayList<Movie>> {
            progressBar.visibility = View.GONE
            if (it != null) {
                listMovies.addAll(it)
                recyclerView.adapter.notifyDataSetChanged()
            }
        })
    }

    private fun loadObservers() {
        viewModel.fetchGenres().observe(this, Observer<GenreResponse> {
            fetchNewMovies()
        })
    }

    fun listenMovieDetails(movieId: Int) {
        progressBar.visibility = View.VISIBLE
        viewModel.fetchMovieDetails(movieId).observe(this, Observer<Movie> {
            progressBar.visibility = View.GONE
            if(it != null) {
                startActivity<MovieDetailsActivity>(
                        "title" to it.title,
                        "overview" to it.overview,
                        "genres" to it.genres?.joinToString(separator = ", ") { it.name },
                        "backdropPath" to it.backdropPath,
                        "posterPath" to it.posterPath,
                        "release_date" to it.releaseDate
                        )
            }
        })
    }
}

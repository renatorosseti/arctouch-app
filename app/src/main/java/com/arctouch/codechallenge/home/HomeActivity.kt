package com.arctouch.codechallenge.home

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
import com.arctouch.codechallenge.api.TmdbApi
import com.arctouch.codechallenge.viewModel.ViewModel
import kotlinx.android.synthetic.main.home_activity.*
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
        recyclerView.adapter = HomeAdapter(listMovies)
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

    fun fetchNewMovies() {
        viewModel.fetchMovies().observe(this, Observer<ArrayList<Movie>> {
            if (it != null) {
                listMovies.addAll(it)
                recyclerView.adapter.notifyDataSetChanged()
                progressBar.visibility = View.GONE
            }
        })
    }

    private fun loadObservers() {
        viewModel.fetchGenres().observe(this, Observer<GenreResponse> {
            fetchNewMovies()
        })
    }
}

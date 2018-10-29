package com.arctouch.codechallenge.view.home

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.util.MovieUrlBuilder
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.movie_item.view.*
import java.util.Locale.filter


class HomeAdapter(val movies: List<Movie>,
                  var filteredMovies: List<Movie> = movies,
                  private val activity: HomeActivity,
                  private val movieImageUrlBuilder: MovieUrlBuilder) : RecyclerView.Adapter<HomeAdapter.ViewHolder>(), Filterable {

    class ViewHolder(itemView: View, val movieImageUrlBuilder: MovieUrlBuilder) : RecyclerView.ViewHolder(itemView) {

        fun bind(movie: Movie) {
            itemView.titleTextView.text = movie.title
            itemView.genresTextView.text = movie.genres?.joinToString(separator = ", ") { it.name }
            itemView.releaseDateTextView.text = movie.releaseDate

            Glide.with(itemView)
                .load(movie.posterPath?.let { movieImageUrlBuilder.buildPosterUrl(it) })
                .apply(RequestOptions().placeholder(R.drawable.ic_image_placeholder))
                .into(itemView.posterImageView)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return ViewHolder(view,movieImageUrlBuilder)
    }

    override fun getItemCount() = filteredMovies.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = filteredMovies[position]
        holder.bind(movie)
        holder.itemView.setOnClickListener { activity.listenMovieDetails(movie.id) }
    }

    override fun getFilter(): Filter = CustomFilter(this)

}

class CustomFilter(private var adapter: HomeAdapter) : Filter() {

    override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
        adapter.filteredMovies = results?.values as ArrayList<Movie>
        adapter.notifyDataSetChanged()
    }

    override fun performFiltering(constraint: CharSequence?): FilterResults {
        val filterResults = Filter.FilterResults()
        val charString = constraint.toString()
        val filteredList = ArrayList<Movie>()

        if (!charString.isEmpty()) {
            adapter.movies.filterTo(filteredList) {
                it.title.toLowerCase().contains(charString.toLowerCase())
            }
            filterResults.values = filteredList
        } else {
            filterResults.values = adapter.movies
        }
        return filterResults
    }
}


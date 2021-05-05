package com.rappi.movies.presentation.ui.search

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rappi.movies.databinding.ItemSearchMovieBinding
import com.rappi.movies.domain.model.Movie

class SearchMovieAdapter(private val context: Context) :
    RecyclerView.Adapter<SearchMovieAdapter.ViewHolder>() {

    private val movies: MutableList<Movie> = mutableListOf()

    fun updateMovies(movies: List<Movie>) {
        this.movies.clear()
        this.movies.addAll(movies)
        this.notifyDataSetChanged()
    }

    internal var clickMovie: (Movie) -> Unit = { _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int) =
        ViewHolder(ItemSearchMovieBinding.inflate(LayoutInflater.from(context), parent, false))

    override fun getItemCount(): Int = this.movies.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contact = this.movies[position]
        holder.bindView(
            this.context,
            contact,
            this.clickMovie
        )
    }

    class ViewHolder(val binding: ItemSearchMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindView(
            context: Context,
            movie: Movie,
            clickMovie: (Movie) -> Unit
        ) =
            with(binding) {
                Glide.with(context)
                    .load("https://image.tmdb.org/t/p/w500${movie.posterPath}")
                    .centerCrop()
                    .into(this.imgMovie)
                this.txtTitle.text = movie.originalTitle
                this.txtOverview.text = movie.overview
                this.root.setOnClickListener { clickMovie(movie) }
            }
    }

}
package com.rappi.movies.presentation.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rappi.movies.R
import com.rappi.movies.databinding.ItemMovieBinding
import com.rappi.movies.domain.model.Movie
import com.rappi.movies.utils.formatToServerDateDefaults
import com.rappi.movies.utils.removeZeros

class MovieAdapter(private val context: Context) :
    RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    private val movies: MutableList<Movie> = mutableListOf()

    private var insertIndex: Int = 0

    fun updateMovies(movies: List<Movie>) {
        this.movies.addAll(insertIndex, movies)
        this.notifyItemRangeChanged(insertIndex, this.movies.size)
        this.insertIndex += movies.size
    }

    internal var clickMovie: (Movie) -> Unit = { _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int) =
        ViewHolder(ItemMovieBinding.inflate(LayoutInflater.from(context), parent, false))

    override fun getItemCount(): Int = this.movies.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contact = this.movies[position]
        holder.bindView(
            this.context,
            contact,
            this.clickMovie
        )

        holder.binding.root.animation =
            AnimationUtils.loadAnimation(holder.itemView.context, R.anim.scale)

    }

    class ViewHolder(val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindView(
            context: Context,
            movie: Movie,
            clickMovie: (Movie) -> Unit
        ) =
            with(binding) {
                Glide.with(context)
                    .load("https://image.tmdb.org/t/p/w500${movie.posterPath}")
                    .error(R.drawable.ic_logo_tmdb)
                    .placeholder(R.drawable.ic_logo_tmdb)
                    .into(this.imgMovie)
                this.root.setOnClickListener { clickMovie(movie) }
                this.txtTitle.text = movie.title
                this.progressBar.progress = ((movie.voteAverage * 10).toInt())
                this.txtVoteAverage.text = (movie.voteAverage * 10).removeZeros().plus("%")
                this.txtDate.text = movie.releaseDate?.formatToServerDateDefaults()
            }
    }

}
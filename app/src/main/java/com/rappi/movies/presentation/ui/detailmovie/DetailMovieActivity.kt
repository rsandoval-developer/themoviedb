package com.rappi.movies.presentation.ui.detailmovie

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.rappi.movies.databinding.ActivityDetailMovieBinding
import com.rappi.movies.domain.model.Movie
import com.rappi.movies.utils.formatToServerDateDefaultsYear

class DetailMovieActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailMovieBinding

    private val movie: Movie? by lazy { intent.getParcelableExtra("movie") }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityDetailMovieBinding.inflate(layoutInflater)
        setContentView(this.binding.root)

        this.binding.toolbar.title = this.movie?.title
        setSupportActionBar(this.binding.toolbar)
        this.supportActionBar?.setDisplayHomeAsUpEnabled(true)



        this.setDetailMovie()
    }

    @SuppressLint("SetTextI18n")
    private fun setDetailMovie() {
        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500${movie?.backdropPath}")
            .centerCrop()
            .into(this.binding.imgMoviePreview)

        this.binding.contentDetailMovie.txtTitle.text = this.movie?.originalTitle
        this.binding.contentDetailMovie.txtYear.text =
            "(${this.movie?.releaseDate?.formatToServerDateDefaultsYear()})"
        this.binding.contentDetailMovie.txtOverview.text = this.movie?.overview


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}
package com.rappi.movies.presentation.ui.detailmovie

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.rappi.movies.databinding.ActivityDetailMovieBinding
import com.rappi.movies.domain.model.Movie
import com.rappi.movies.domain.model.Video
import com.rappi.movies.presentation.base.Resource
import com.rappi.movies.presentation.ui.player.PlayerYoutubeActivity
import com.rappi.movies.extensions.formatToServerDateDefaultsYear
import com.rappi.movies.extensions.showIf
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailMovieActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailMovieBinding

    private val movie: Movie? by lazy { intent.getParcelableExtra("movie") }

    private val viewModel: DetailViewModel by viewModels()

    private var video: Video? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityDetailMovieBinding.inflate(layoutInflater)
        setContentView(this.binding.root)

        this.binding.toolbar.title = this.movie?.title
        setSupportActionBar(this.binding.toolbar)
        this.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        this.initObserveViewModel()
        this.setDetailMovie()
        this.initButtons()
        this.viewModel.getVideoTrailer(movie?.id ?: 0)
    }

    private fun initButtons() {
        this.binding.btnPlay.setOnClickListener {
            val intent = Intent(this, PlayerYoutubeActivity::class.java)
            intent.putExtra("video", this.video)
            startActivity(intent)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setDetailMovie() {
        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500${movie?.backdropPath}")
            .centerCrop()
            .into(this.binding.imgMoviePreview)


        this.binding.contentDetailMovie.apply {
            txtTitle.text = movie?.originalTitle
            txtYear.text = "(${movie?.releaseDate?.formatToServerDateDefaultsYear()})"
            txtOverview.text = movie?.overview
        }
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


    private fun initObserveViewModel() {
        this.viewModel.resource.observe(this, Observer { state ->
            when (state) {
                is Resource.Loading -> {
                    this.binding.apply {
                        progressBar.showIf { state.visible }
                        btnPlay.showIf { !state.visible }
                    }
                }
                is Resource.Success -> {
                    this.video = state.data
                }
                is Resource.Failure -> {
                    Snackbar.make(
                        this.binding.root,
                        state.error.message.orEmpty(),
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }

        })
    }
}
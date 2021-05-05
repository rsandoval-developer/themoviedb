package com.rappi.movies.presentation.ui.search

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.rappi.movies.R
import com.rappi.movies.data.remote.exceptions.AppException
import com.rappi.movies.databinding.ActivitySearchMoviesBinding
import com.rappi.movies.databinding.ContentSearchMoviesBinding
import com.rappi.movies.presentation.base.Resource
import com.rappi.movies.presentation.ui.detailmovie.DetailMovieActivity
import com.rappi.movies.utils.hide
import com.rappi.movies.utils.show
import com.rappi.movies.utils.showIf
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchMoviesActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchMoviesBinding
    private lateinit var contentSearchMoviesBinding: ContentSearchMoviesBinding

    private val searchMovieAdapter: SearchMovieAdapter by lazy {
        SearchMovieAdapter(this)
    }

    private val viewModel: SearchMoviesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.binding = ActivitySearchMoviesBinding.inflate(layoutInflater)
        this.contentSearchMoviesBinding = this.binding.contentSearchMovies
        val view = binding.root
        setContentView(view)
        this.initToolbar()
        this.initAdapter()
        this.initObserveViewModel()
        this.initButtons()

        this.binding.searchView.setOnQueryTextListener(object :
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.isNotEmpty()) {
                    viewModel.searchMovies(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isNotEmpty()) {
                    viewModel.searchMovies(newText)
                }
                return true
            }

        })

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

    private fun initToolbar() {
        this.binding.toolbar.title = ""
        this.setSupportActionBar(binding.toolbar)
        this.supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initAdapter() {
        this.contentSearchMoviesBinding.rvMovies.layoutManager = LinearLayoutManager(this)
        this.contentSearchMoviesBinding.rvMovies.adapter = this.searchMovieAdapter
    }


    private fun initObserveViewModel() {
        this.viewModel.resource.observe(this, Observer { state ->
            when (state) {
                is Resource.Loading -> {
                    this.contentSearchMoviesBinding.progressBar.showIf { state.visible }
                    this.contentSearchMoviesBinding.rvMovies.showIf { !state.visible }
                }
                is Resource.Success -> {
                    if (state.data.isEmpty()) {
                        this.contentSearchMoviesBinding.txtEmpty.show()
                        this.contentSearchMoviesBinding.rvMovies.hide()
                        this.contentSearchMoviesBinding.txtEmpty.text =
                            this.resources.getString(R.string.no_results)
                        return@Observer
                    }
                    this.contentSearchMoviesBinding.txtEmpty.hide()
                    this.contentSearchMoviesBinding.rvMovies.show()
                    this.searchMovieAdapter.updateMovies(state.data)
                }
                is Resource.Failure -> {

                    if (state.error is AppException) {
                        when (state.error.validationType) {
                            AppException.Type.ERROR_NETWORK -> {
                                this.contentSearchMoviesBinding.txtEmpty.show()
                                this.contentSearchMoviesBinding.txtEmpty.text =
                                    state.error.message
                                this.contentSearchMoviesBinding.rvMovies.hide()

                            }
                        }
                    } else {
                        Snackbar.make(
                            this.binding.root,
                            state.error.message ?: "",
                            Snackbar.LENGTH_LONG
                        ).show()
                    }

                }
            }
        })
    }

    private fun initButtons() {
        this.searchMovieAdapter.clickMovie = { movie ->
            val intent = Intent(this, DetailMovieActivity::class.java)
            intent.putExtra("movie", movie)
            startActivity(intent)
        }
    }

}
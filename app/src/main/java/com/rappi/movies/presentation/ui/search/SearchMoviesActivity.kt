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
import com.rappi.movies.data.api.exceptions.AppException
import com.rappi.movies.databinding.ActivitySearchMoviesBinding
import com.rappi.movies.databinding.ContentSearchMoviesBinding
import com.rappi.movies.extensions.hide
import com.rappi.movies.extensions.show
import com.rappi.movies.extensions.showIf
import com.rappi.movies.presentation.base.Resource
import com.rappi.movies.presentation.ui.detailmovie.DetailMovieActivity
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
        binding = ActivitySearchMoviesBinding.inflate(layoutInflater)
        contentSearchMoviesBinding = binding.contentSearchMovies
        setContentView(binding.root)
        initToolbar()
        initAdapter()
        initObserveViewModel()
        initButtons()

        binding.searchView.setOnQueryTextListener(object :
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
        binding.toolbar.title = ""
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initAdapter() {
        contentSearchMoviesBinding.rvMovies.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = searchMovieAdapter
        }
    }


    private fun initObserveViewModel() {
        viewModel.resource.observe(this, Observer { state ->
            when (state) {
                is Resource.Loading -> {
                    contentSearchMoviesBinding.apply {
                        progressBar.showIf { state.visible }
                        rvMovies.showIf { !state.visible }
                    }
                }
                is Resource.Success -> {
                    if (state.data.isEmpty()) {
                        contentSearchMoviesBinding.apply {
                            txtEmpty.show()
                            rvMovies.hide()
                            txtEmpty.text = resources.getString(R.string.no_results)
                        }
                        return@Observer
                    }
                    contentSearchMoviesBinding.apply {
                        txtEmpty.hide()
                        rvMovies.show()
                    }
                    searchMovieAdapter.updateMovies(state.data)
                }
                is Resource.Failure -> {
                    if (state.error is AppException) {
                        when (state.error.validationType) {
                            AppException.Type.ERROR_NETWORK -> {
                                contentSearchMoviesBinding.apply {
                                    txtEmpty.show()
                                    txtEmpty.text =
                                        resources.getString(R.string.connection_error_description)
                                    rvMovies.hide()
                                }
                            }
                        }
                    } else {
                        Snackbar.make(
                            binding.root,
                            state.error.message ?: "",
                            Snackbar.LENGTH_LONG
                        ).show()
                    }

                }
            }
        })
    }

    private fun initButtons() {
        searchMovieAdapter.clickMovie = { movie ->
            val intent = Intent(this, DetailMovieActivity::class.java)
            intent.putExtra("movie", movie)
            startActivity(intent)
        }
    }
}
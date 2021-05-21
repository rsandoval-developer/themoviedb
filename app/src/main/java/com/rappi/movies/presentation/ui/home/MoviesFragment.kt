package com.rappi.movies.presentation.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.google.android.material.snackbar.Snackbar
import com.rappi.movies.BuildConfig
import com.rappi.movies.R
import com.rappi.movies.data.api.exceptions.AppException
import com.rappi.movies.data.api.request.GetMoviesRequestParams
import com.rappi.movies.databinding.FragmentMoviesBinding
import com.rappi.movies.extensions.*
import com.rappi.movies.presentation.base.Resource
import com.rappi.movies.presentation.ui.DividerItemDecoration
import com.rappi.movies.presentation.ui.detailmovie.DetailMovieActivity
import com.rappi.movies.presentation.ui.utils.PaginationScrollListener
import com.rappi.movies.presentation.ui.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MoviesFragment : Fragment() {

    private var isLastPage: Boolean = false
    private var isLoading: Boolean = false
    private var idMovies: String? = null
    private var page: Int = 1
    private var binding: FragmentMoviesBinding by autoCleared()

    private val movieAdapter: MovieAdapter by lazy {
        MovieAdapter(requireContext())
    }

    private val divider: DividerItemDecoration by lazy {
        DividerItemDecoration(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.list_divider
            ), DividerItemDecoration.VERTICAL
        )
    }

    private val viewModel: MoviesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            idMovies = it.getString("id_movies")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initObserveViewModel()
        initButtons()
        viewModel.getMovies(
            GetMoviesRequestParams(
                idMovies ?: "",
                BuildConfig.API_KEY,
                page
            ),
        )
    }

    private fun initButtons() {
        movieAdapter.clickMovie = { movie ->
            val intent = Intent(context, DetailMovieActivity::class.java)
            intent.putExtra("movie", movie)
            startActivity(intent)
        }
    }

    private fun initObserveViewModel() {
        viewModel.resource.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                is Resource.Loading -> {
                    binding.apply {
                        progressBar.showIf { state.visible }
                        rvMovies.showIf { !state.visible }
                    }
                }
                is Resource.Success -> {
                    if (state.data.isEmpty()) {
                        binding.apply {
                            txtEmpty.show()
                            rvMovies.hide()
                            txtEmpty.text = resources.getString(R.string.no_results)
                        }
                        return@Observer
                    }
                    binding.txtEmpty.hide()
                    movieAdapter.updateMovies(state.data)
                    isLoading = false
                }
                is Resource.Failure -> {
                    if (state.error is AppException) {
                        when (state.error.validationType) {
                            AppException.Type.ERROR_NETWORK -> {
                                binding.apply {
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

    private fun initAdapter() {

        binding.rvMovies.apply {
            removeItemDecoration(divider)
            layoutManager = GridLayoutManager(context, 3, LinearLayoutManager.VERTICAL, false)
            itemAnimator?.changeDuration = 0
            (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
            adapter = movieAdapter
            addOnScrollListener(object :
                PaginationScrollListener(layoutManager as GridLayoutManager) {

                override fun isLastPage(): Boolean = isLastPage

                override fun isLoading(): Boolean = isLoading

                override fun loadMoreItems() {
                    isLoading = true
                    //you have to call loadmore items to get more data
                    page++
                    viewModel.getMovies(
                        GetMoviesRequestParams(
                            idMovies ?: "",
                            BuildConfig.API_KEY,
                            page,
                            isPagination = true
                        )
                    )
                }
            })
        }

    }
}
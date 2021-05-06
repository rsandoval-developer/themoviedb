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
import com.rappi.movies.data.remote.exceptions.AppException
import com.rappi.movies.data.remote.request.GetMoviesRequestParams
import com.rappi.movies.databinding.FragmentMoviesBinding
import com.rappi.movies.presentation.base.Resource
import com.rappi.movies.presentation.ui.DividerItemDecoration
import com.rappi.movies.presentation.ui.detailmovie.DetailMovieActivity
import com.rappi.movies.utils.*
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MoviesFragment : Fragment() {

    private var isLastPage: Boolean = false
    private var isLoading: Boolean = false
    private var idMovies: String? = null
    private var page: Int = 1
    private lateinit var binding: FragmentMoviesBinding

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
        this.binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.initAdapter()
        this.initObserveViewModel()
        this.initButtons()
        this.viewModel.getMovies(
            GetMoviesRequestParams(
                this.idMovies ?: "",
                BuildConfig.API_KEY,
                page
            ),
        )
    }

    private fun initButtons() {
        this.movieAdapter.clickMovie = { movie ->
            val intent = Intent(context, DetailMovieActivity::class.java)
            intent.putExtra("movie", movie)
            startActivity(intent)
        }
    }

    private fun initObserveViewModel() {
        this.viewModel.moviesViewModelState.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                is Resource.Loading -> {
                    this.binding.progressBar.showIf { state.visible }
                    this.binding.rvMovies.showIf { !state.visible }
                }
                is Resource.Success -> {
                    if (state.data.isEmpty()) {
                        this.binding.txtEmpty.show()
                        this.binding.rvMovies.hide()
                        this.binding.txtEmpty.text =
                            this.resources.getString(R.string.no_results)
                        return@Observer
                    }
                    this.binding.txtEmpty.hide()
                    this.movieAdapter.updateMovies(state.data)
                    this.isLoading = false
                }
                is Resource.Failure -> {

                    if (state.error is AppException) {
                        when (state.error.validationType) {
                            AppException.Type.ERROR_NETWORK -> {
                                this.binding.txtEmpty.show()
                                this.binding.txtEmpty.text =
                                    state.error.message
                                this.binding.rvMovies.hide()

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

    private fun initAdapter() {
        this.binding.rvMovies.layoutManager =
            GridLayoutManager(this.context, 3, LinearLayoutManager.VERTICAL, false)
        this.binding.rvMovies.removeItemDecoration(this.divider)

        this.binding.rvMovies.apply {
            itemAnimator?.changeDuration = 0
            (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        }

        this.binding.rvMovies.adapter = this.movieAdapter

        this.binding.rvMovies.addOnScrollListener(object :
            PaginationScrollListener(this.binding.rvMovies.layoutManager as GridLayoutManager) {
            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

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
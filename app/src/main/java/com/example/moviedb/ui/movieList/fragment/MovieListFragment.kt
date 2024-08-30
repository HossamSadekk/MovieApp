package com.example.moviedb.ui.movieList.fragment

import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import com.example.moviedb.core.base.fragment.BaseFragment
import com.example.moviedb.databinding.FragmentMovieListBinding
import com.example.moviedb.ui.movieList.adapter.MovieAdapter
import com.example.moviedb.ui.movieList.viewmodel.MovieListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieListFragment : BaseFragment<FragmentMovieListBinding, MovieListViewModel>() {
    private val movieAdapter by lazy {
        MovieAdapter(
            onClickShowDetails = {

            }, onClickLike = {

            }
        )
    }

    override fun setupUI() {
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.movieRecyclerView.adapter = movieAdapter

        movieAdapter.addLoadStateListener { loadState ->
            when (val refreshState = loadState.refresh) {
                is LoadState.Loading -> {
                    viewModel.startLoading()
                }

                is LoadState.Error -> {
                    val error = refreshState.error
                    binding.tvError.isVisible = true
                    binding.tvError.text = error.message
                    viewModel.stopLoading()
                }

                else -> {
                    viewModel.stopLoading()
                }
            }
        }
    }

    override fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            // This block runs only when the lifecycle is in the STARTED state
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.movies.collectLatest { pagingData ->
                    movieAdapter.submitData(pagingData)
                }
            }
        }

        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressCircular.isVisible = isLoading
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            binding.tvError.isVisible = true
            binding.tvError.text = error
        }
    }

}
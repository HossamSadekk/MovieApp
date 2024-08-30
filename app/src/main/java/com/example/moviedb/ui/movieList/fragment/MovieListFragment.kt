package com.example.moviedb.ui.movieList.fragment

import androidx.lifecycle.lifecycleScope
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

            }
        )
    }

    override fun setupUI() {
        binding.apply {
            movieRecyclerView.adapter = movieAdapter
        }
    }

    override fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.movies.collectLatest { pagingData ->
                movieAdapter.submitData(pagingData)
            }
        }
    }

}
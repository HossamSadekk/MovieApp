package com.example.moviedb.ui.movieDetails.fragment

import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.moviedb.R
import com.example.moviedb.core.base.fragment.BaseFragment
import com.example.moviedb.databinding.FragmentMovieDetailsBinding
import com.example.moviedb.ui.movieDetails.viewmodel.MovieDetailsViewModel
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetails : BaseFragment<FragmentMovieDetailsBinding, MovieDetailsViewModel>() {
    private val args: MovieDetailsArgs by navArgs()

    override fun setupUI() {
        binding.apply {
            val movie = args.movie
            Picasso.get()
                .load("https://image.tmdb.org/t/p/w500${movie.posterPath}")
                .into(posterMovie)

            movieName.text = movie.title
            movieDate.text = movie.releaseDate
            movieRate.text = movie.voteAverage.toString()
            movieDetails.text = movie.overview
            btnLike.setImageResource(
                if (movie.isLiked) R.drawable.ic_full_heart else R.drawable.ic_empty_heart
            )
            handleClicks()
        }
    }

    private fun handleClicks(){
        binding.apply {
            btnBack.setOnClickListener {
                findNavController().navigateUp()
            }
            btnLike.setOnClickListener {
                val movie = args.movie
                movie.isLiked = !movie.isLiked
                val newLikeStatus = movie.isLiked
                btnLike.setImageResource(
                    if (newLikeStatus) R.drawable.ic_full_heart else R.drawable.ic_empty_heart
                )
                viewModel.updateMovieState(movie)
            }
        }
    }
    override fun observeViewModel() {}

}
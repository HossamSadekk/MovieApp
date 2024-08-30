package com.example.moviedb.ui.movieList.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedb.data.model.Movie
import com.example.moviedb.databinding.MovieItemBinding
import com.squareup.picasso.Picasso

class MovieAdapter(
    private val onClickShowDetails: (Movie) -> Unit,
    private val onClickLike: (Movie) -> Unit,
) : PagingDataAdapter<Movie, MovieAdapter.MovieViewHolder>(MovieDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding, onClickShowDetails, onClickLike)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        if (movie != null) {
            holder.bind(movie)
        }
    }

    inner class MovieViewHolder(
        private val binding: MovieItemBinding,
        private val onClickShowDetails: (Movie) -> Unit,
        private val onClickLike: (Movie) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            with(binding) {
                // Bind movie details here
                movieName.text = movie.title
                movieRate.text = "Rating: ${movie.voteAverage}"
                movieData.text = movie.releaseDate

                Picasso.get()
                    .load("https://image.tmdb.org/t/p/w500${movie.posterPath}")
                    .into(posterMovie)

                root.setOnClickListener {
                    onClickShowDetails(movie)
                }
                btnLike.setOnClickListener {
                    onClickLike(movie)
                }
            }
        }
    }

    class MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }
}

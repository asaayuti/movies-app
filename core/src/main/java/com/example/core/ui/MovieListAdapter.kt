package com.example.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.core.databinding.ItemListMovieBinding
import com.example.core.domain.model.Movie
import com.example.core.utils.loadImage

class MovieListAdapter(
    private val onItemClick: (Int) -> Unit
) : ListAdapter<Movie, MovieListAdapter.MovieViewHolder>(MovieComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding =
            ItemListMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie)
    }

    class MovieViewHolder(
        private val binding: ItemListMovieBinding,
        private val onItemClick: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            binding.apply {
                if (movie.posterPath.isNotEmpty()) {
                    ivFilm.loadImage(movie.posterPath)
                }
                tvTitleFilm.text = movie.title
                root.setOnClickListener {
                    onItemClick(movie.id)
                }
            }
        }
    }

    class MovieComparator : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }
}

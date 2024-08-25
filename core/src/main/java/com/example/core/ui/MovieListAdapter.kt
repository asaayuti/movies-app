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
) : ListAdapter<Movie, MovieListAdapter.WordViewHolder>(WordsComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val binding =
            ItemListMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WordViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie)
        holder.binding.root.setOnClickListener {
            onItemClick(movie.id)
        }
    }

    override fun onViewRecycled(holder: WordViewHolder) {
        super.onViewRecycled(holder)
        holder.binding.root.setOnClickListener(null)
    }

    class WordViewHolder(val binding: ItemListMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            if (movie.posterPath.isNotEmpty()) {
                binding.apply {
                    ivFilm.loadImage(movie.posterPath)
                    tvTitleFilm.text = movie.title
                }
            }
        }
    }

    class WordsComparator : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }
}
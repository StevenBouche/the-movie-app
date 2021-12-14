package com.miage.movieapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.example.core.models.api.Movie
import com.miage.movieapp.databinding.HomeMovieItemBinding
import com.miage.movieapp.extension.setPosterImage
import com.miage.movieapp.handlers.IHandlerHome
import com.miage.movieapp.utils.RecyclerScrollListener

class HomeMoviesAdapter(
    private val handler: IHandlerHome,
    infiniteContentScrollListener: RecyclerScrollListener
) : ListRecyclerScrollAdapter<Movie, HomeMovieItemBinding>(DiffCallback(), infiniteContentScrollListener) {

    override fun bindingInflate(layout: LayoutInflater, parent: ViewGroup): HomeMovieItemBinding {
        return HomeMovieItemBinding.inflate(layout, parent, false)
    }

    override fun bindViewHolder(item: Movie, viewHolder: ListRecyclerAdapter<Movie, HomeMovieItemBinding>.ViewHolder) {
        val binding = viewHolder.binding
        binding.movie = item
        binding.image.setPosterImage(item.posterPath)
        binding.root.setOnClickListener {
            handler.onClickMovie(item)
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }
    }
}
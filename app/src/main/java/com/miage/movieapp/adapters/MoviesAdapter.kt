package com.miage.movieapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.miage.movieapp.adapters.models.MovieView
import com.miage.movieapp.databinding.MovieItemBinding
import com.miage.movieapp.extension.setPosterImage
import com.miage.movieapp.handlers.IHandlerMovies
import com.miage.movieapp.utils.RecyclerScrollListener

class MoviesAdapter(
    private val handler: IHandlerMovies,
    private val infiniteContentScrollListener: RecyclerScrollListener
)  : ListRecyclerScrollAdapter<MovieView, MovieItemBinding>(DiffCallback(), infiniteContentScrollListener)  {

    override fun bindingInflate(layout: LayoutInflater, parent: ViewGroup): MovieItemBinding {
        return MovieItemBinding.inflate(layout, parent, false);
    }

    override fun bindViewHolder(
        item: MovieView,
        viewHolder: ListRecyclerAdapter<MovieView, MovieItemBinding>.ViewHolder
    ) {
        val binding = viewHolder.binding
        binding.movie = item
        binding.root.setOnClickListener { handler.onClick(item) }
        binding.image.setPosterImage(item.posterPath)
    }

    private class DiffCallback : DiffUtil.ItemCallback<MovieView>() {
        override fun areItemsTheSame(oldItem: MovieView, newItem: MovieView): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: MovieView, newItem: MovieView): Boolean {
            return oldItem.id == newItem.id
        }
    }
}
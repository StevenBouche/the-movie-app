package com.miage.movieapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.example.core.models.api.MovieVideo
import com.miage.movieapp.databinding.VideoItemBinding
import com.miage.movieapp.extension.setVideoThumbnail
import com.miage.movieapp.handlers.IHandlerMovieDetails

class VideosAdapter(
    private val handler: IHandlerMovieDetails
) : ListRecyclerAdapter<MovieVideo, VideoItemBinding>(DiffCallback())
{
    override fun bindingInflate(layout: LayoutInflater, parent: ViewGroup): VideoItemBinding {
        return VideoItemBinding.inflate(layout, parent, false)
    }

    override fun bindViewHolder(item: MovieVideo, viewHolder: ListRecyclerAdapter<MovieVideo, VideoItemBinding>.ViewHolder) {
        val binding = viewHolder.binding
        //holder.itemView.setOnClickListener { handler.onClickVideo(item) }
        binding.playImageButton.setOnClickListener { handler.onClickVideo(item) }
        binding.video = item
        binding.image.setVideoThumbnail(item.key)
    }

    private class DiffCallback : DiffUtil.ItemCallback<MovieVideo>() {
        override fun areItemsTheSame(oldItem: MovieVideo, newItem: MovieVideo): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: MovieVideo, newItem: MovieVideo): Boolean {
            return oldItem.id == newItem.id
        }
    }
}
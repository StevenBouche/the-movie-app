package com.miage.movieapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.example.core.models.api.Cast
import com.miage.movieapp.databinding.CastItemBinding
import com.miage.movieapp.extension.setProfileImage

class CastsAdapter : ListRecyclerAdapter<Cast, CastItemBinding>(DiffCallback()) {

    override fun bindingInflate(layout: LayoutInflater, parent: ViewGroup): CastItemBinding {
        return CastItemBinding.inflate(layout, parent, false)
    }

    override fun bindViewHolder(item: Cast, viewHolder: ListRecyclerAdapter<Cast, CastItemBinding>.ViewHolder) {
        val binding = viewHolder.binding
        binding.cast = item
        binding.image.setProfileImage(item.profilePath)
        binding.titleText.text = item.name
    }

    private class DiffCallback : DiffUtil.ItemCallback<Cast>() {
        override fun areItemsTheSame(oldItem: Cast, newItem: Cast): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Cast, newItem: Cast): Boolean {
            return oldItem.id == newItem.id
        }
    }
}
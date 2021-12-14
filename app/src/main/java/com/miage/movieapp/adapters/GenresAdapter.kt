package com.miage.movieapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.core.models.api.Genre
import com.miage.movieapp.databinding.GenreItemBinding
import com.miage.movieapp.handlers.IHandlerGenres

class GenresAdapter(
    private val items: List<Genre>,
    private val handler: IHandlerGenres
) : RecyclerView.Adapter<GenresAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(GenreItemBinding.inflate(inflater, parent, false))
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class ViewHolder(private val binding: GenreItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Genre) {
            binding.item = item
            binding.root.setOnClickListener { handler.onClick(item) }
        }
    }
}
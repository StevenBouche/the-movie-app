package com.miage.movieapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

abstract class ListRecyclerAdapter<T, K>(
    callbackDiff : DiffUtil.ItemCallback<T>
) : ListAdapter<(T), ListRecyclerAdapter<T, K>.ViewHolder>(callbackDiff) where K : ViewDataBinding
{

    inner class ViewHolder(val binding: K) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: T) {
            bindViewHolder(item, this)
            binding.executePendingBindings()
        }
    }

    override fun submitList(list: List<T>?) {
        val newList: MutableList<T> = arrayListOf()
        if (list != null) newList.addAll(list)
        super.submitList(newList)
    }

    fun clearList() {
        super.submitList(null)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(bindingInflate(inflater, parent))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    abstract fun bindingInflate(layout: LayoutInflater, parent: ViewGroup): K
    abstract fun bindViewHolder(item: T, viewHolder: ViewHolder)

}
package com.miage.movieapp.adapters

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.miage.movieapp.utils.RecyclerScrollListener

abstract class ListRecyclerScrollAdapter<T, K>(
    callbackDiff : DiffUtil.ItemCallback<T>,
    private val infiniteContentScrollListener: RecyclerScrollListener
) : ListRecyclerAdapter<T, K>(callbackDiff) where K : ViewDataBinding {

    override fun submitList(list: List<T>?) {
        super.submitList(list)
        infiniteContentScrollListener.itemsLoaded()
    }

    override fun onCurrentListChanged(previousList: List<T>, currentList: List<T>) {
        infiniteContentScrollListener.active = !currentList.isNullOrEmpty() && previousList.size < currentList.size
    }
}
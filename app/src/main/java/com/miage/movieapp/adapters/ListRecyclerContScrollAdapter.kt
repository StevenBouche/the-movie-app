package com.miage.movieapp.adapters

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.miage.movieapp.utils.RecyclerScrollListener

abstract class ListRecyclerContScrollAdapter<T,K,Z>(
    callbackDiff : DiffUtil.ItemCallback<T>,
    private val infiniteContentScrollListener: RecyclerScrollListener
) : ListRecyclerContAdapter<T, K, Z>(callbackDiff) where K : ViewDataBinding {

    override fun submitList(list: List<T>?) {
        super.submitList(list)
        infiniteContentScrollListener.itemsLoaded()
    }

    override fun onCurrentListChanged(previousList: List<T>, currentList: List<T>) {
        infiniteContentScrollListener.active = currentList.isNullOrEmpty()
    }

}
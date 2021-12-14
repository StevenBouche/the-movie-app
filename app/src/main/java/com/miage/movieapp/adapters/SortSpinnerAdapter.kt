package com.miage.movieapp.adapters

import android.app.Activity
import android.provider.Settings.System.getString
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.miage.movieapp.databinding.SpinnerSortItemBinding.*
import com.miage.movieapp.handlers.IHandlerSpinnerSort
import com.miage.movieapp.models.DiscoverSortBy

class SortSpinnerAdapter(
    private val handler: IHandlerSpinnerSort,
    private val list: Array<DiscoverSortBy>
)
    : BaseAdapter() {

    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(p0: Int): DiscoverSortBy {
        return list[p0]
    }

    override fun getItemId(p0: Int): Long {
        return list[p0].id
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val binding = inflate(handler.getLayoutInflater(), p2, false)
        val item = getItem(p0)
        binding.textViewItemName.text = handler.getLabel(item.titleRes)
        return binding.root
    }
}
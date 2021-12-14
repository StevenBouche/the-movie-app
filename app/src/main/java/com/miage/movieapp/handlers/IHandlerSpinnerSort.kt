package com.miage.movieapp.handlers

import android.view.LayoutInflater
import com.miage.movieapp.models.DiscoverSortBy

interface IHandlerSpinnerSort {
    fun getLayoutInflater(): LayoutInflater
    fun getLabel(value: Int): String
    fun onClickItem(value: DiscoverSortBy)
}
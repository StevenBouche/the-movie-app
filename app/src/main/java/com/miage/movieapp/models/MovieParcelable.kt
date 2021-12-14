package com.miage.movieapp.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieParcelable(
    val id: Int,
    val title: String
) : Parcelable
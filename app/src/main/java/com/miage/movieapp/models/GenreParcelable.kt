package com.miage.movieapp.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GenreParcelable(
    val id: Int,
    val name: String
) : Parcelable
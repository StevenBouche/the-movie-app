package com.miage.movieapp.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class VideoParcelable (
    val id: String
) : Parcelable
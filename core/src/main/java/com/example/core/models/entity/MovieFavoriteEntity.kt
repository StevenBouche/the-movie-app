package com.example.core.models.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.core.models.api.Movie

@Entity(
    tableName = "idb_movies_favorite"
)
data class MovieFavoriteEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val posterPath: String?,
    val createdAt: Long = System.currentTimeMillis(),
){
    constructor(movie: Movie) : this(movie.id, movie.title, movie.posterPath)
}
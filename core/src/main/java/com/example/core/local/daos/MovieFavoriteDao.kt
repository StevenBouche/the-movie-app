package com.example.core.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.core.models.entity.MovieFavoriteEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

@Dao
interface MovieFavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: MovieFavoriteEntity)

    @Query("SELECT * from idb_movies_favorite WHERE id = :id")
    fun retrieve(id: Int): Observable<MovieFavoriteEntity>

    @Query("SELECT * from idb_movies_favorite ORDER BY createdAt DESC")
    fun retrieveAll(): Observable<List<MovieFavoriteEntity>>

    @Query("DELETE FROM idb_movies_favorite WHERE id = :id")
    fun delete(id: Int) : Completable

}
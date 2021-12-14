package com.example.core.local.databases

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.core.local.daos.MovieFavoriteDao
import com.example.core.local.daos.SessionDao
import com.example.core.local.daos.TokenDao
import com.example.core.models.entity.MovieFavoriteEntity
import com.example.core.models.entity.SessionEntity
import com.example.core.models.entity.TokenEntity

/**
 * Modélise la base de données ainsi que les tables de la BDD
 */
@Database(
    entities = [TokenEntity::class, MovieFavoriteEntity::class, SessionEntity::class ],
    version = 3
)
abstract class IdbDataBase : RoomDatabase() {
    abstract fun tokenDao(): TokenDao
    abstract fun movieFavoriteDao(): MovieFavoriteDao
    abstract fun sessionDao(): SessionDao
}
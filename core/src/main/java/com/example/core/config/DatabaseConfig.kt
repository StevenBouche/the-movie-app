package com.example.core.config

import android.content.Context
import androidx.room.Room
import com.example.core.local.daos.MovieFavoriteDao
import com.example.core.local.daos.SessionDao
import com.example.core.local.daos.TokenDao
import com.example.core.local.databases.IdbDataBase

object DatabaseConfig {
    fun buildDatabase(context: Context): IdbDataBase {
        return Room.databaseBuilder(
            context,
            IdbDataBase::class.java, "idb_database.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    fun getTokenDao(db: IdbDataBase): TokenDao = db.tokenDao()
    fun getMovieFavoriteDao(db: IdbDataBase): MovieFavoriteDao = db.movieFavoriteDao()
    fun getSessionDao(db: IdbDataBase): SessionDao = db.sessionDao()

}
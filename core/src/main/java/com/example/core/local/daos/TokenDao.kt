package com.example.core.local.daos

import androidx.room.*
import com.example.core.models.entity.TokenEntity

@Dao
interface TokenDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: TokenEntity)

    @Query("SELECT * from idb_tokens ORDER BY expiresAt DESC LIMIT 1")
    fun retrieve(): TokenEntity?

    @Query("DELETE FROM idb_tokens")
    fun delete()
}
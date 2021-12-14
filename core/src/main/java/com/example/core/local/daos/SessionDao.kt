package com.example.core.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.core.models.entity.SessionEntity

@Dao
interface SessionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: SessionEntity)

    @Query("SELECT * from idb_sessions ORDER BY createAt DESC LIMIT 1")
    fun retrieve(): SessionEntity?

    @Query("DELETE FROM idb_tokens")
    fun delete()
}
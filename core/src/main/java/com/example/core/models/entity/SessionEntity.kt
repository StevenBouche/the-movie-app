package com.example.core.models.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "idb_sessions"
)
data class SessionEntity(
    @PrimaryKey
    val id: String,
    val createAt: Long = System.currentTimeMillis()
) {
}
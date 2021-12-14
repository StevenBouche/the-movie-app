package com.example.core.models.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import com.example.core.models.api.Token

/**
 * Modélise les tokens dans la base de données
 */
@Entity(
    tableName = "idb_tokens"
)
data class TokenEntity(
    @PrimaryKey
    val token: String,
    val expiresAt: Long
) {
    fun hasExpired(): Boolean {
        return Instant.now().epochSecond < expiresAt
    }

    companion object {
        fun fromApi(token: Token): TokenEntity {
            val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z")
            val zonedDateTime: ZonedDateTime = ZonedDateTime.parse(token.expiresAt, formatter)
            val test = zonedDateTime.toEpochSecond();
            return TokenEntity(
                expiresAt = zonedDateTime.toEpochSecond(),
                token = token.requestToken!!
            )
        }
    }

}
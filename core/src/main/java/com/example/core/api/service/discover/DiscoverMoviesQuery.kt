package com.example.core.api.service.discover

import com.example.core.api.service.discover.enumeration.DiscoverQueryString
import java.util.*
import kotlin.collections.HashMap

class QueryValue<T>(val queryString: DiscoverQueryString, var value: T, val adapter: (value: T) -> String){
    override fun toString(): String {
        return "${this.queryString.queryName}=${this.adapter(this.value)}"
    }
}

class DiscoverAdapter{
    companion object {
        fun adaptString(value: String): String = value
        fun adaptListOfString(value: List<String>, separator: String): String = value.joinToString(separator)
    }
}

internal class DiscoverMoviesQuery {

    private val map: EnumMap<DiscoverQueryString, QueryValue<*>> = EnumMap(DiscoverQueryString::class.java)

    private val language: QueryValue<String> = QueryValue(DiscoverQueryString.LANGUAGE, "en-US") { DiscoverAdapter.adaptString(it) }
    private val withGenres: QueryValue<MutableList<String>> = QueryValue(DiscoverQueryString.WITH_GENRES, mutableListOf()) { DiscoverAdapter.adaptListOfString(it, ",") }

    init {
        addQuery(language)
        addQuery(withGenres)
    }

    private fun addQuery(queryValue: QueryValue<*>){
        map[queryValue.queryString] = queryValue;
    }

    fun getQuery(): String {
        return map.values.map { it.toString() }
            .filter { it.isNotEmpty() }
            .joinToString("&")
    }

}
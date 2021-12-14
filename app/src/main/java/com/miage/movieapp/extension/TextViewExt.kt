package com.miage.movieapp.extension

import android.view.View
import android.widget.TextView
import com.example.core.api.service.TheMovieAPI
import com.example.core.models.api.Genre
import java.text.SimpleDateFormat
import java.util.*

fun appendZeroBeforeNumber(num: Int): String {
    return if (num < 10) "0$num" else num.toString()
}

fun TextView.setMovieRuntime(dateString: String?) {
    if (dateString.isNullOrBlank()) return
    val date = SimpleDateFormat(TheMovieAPI.getRuntimeDateFormat()).parse(dateString)
    val pat = SimpleDateFormat().toLocalizedPattern().replace("\\W?[HhKkmsSzZXa]+\\W?".toRegex(), "")
    val localFormatter = SimpleDateFormat(pat, Locale.getDefault())
    this.text = localFormatter.format(date)
}

fun TextView.setMovieRuntime(runtimeInMinutes: Int?) {
    runtimeInMinutes?.let {
        val hoursText: String = appendZeroBeforeNumber((it / 60f).toInt())
        val minutesText: String = appendZeroBeforeNumber((it % 60f).toInt())
        val text = "$hoursText:$minutesText / $runtimeInMinutes min"
        this.text = text
    }
}

fun TextView.setMovieLanguage(languageCode: String?) {
    languageCode?.let { this.text = Locale(languageCode).getDisplayLanguage(Locale("en")) }
}

fun TextView.setGenresText(genres: List<Genre>?) {
    if (genres == null) return

    val maxNumOfGenres = 3
    var text = ""
    val appendText = " / "

    val loopCount = if (genres.size <= maxNumOfGenres) genres.size else maxNumOfGenres
    for (i in 0 until loopCount) {
        text = text + genres[i].name + appendText
    }
    this.text = text.dropLast(appendText.length)
}

fun TextView.setVisibilityList(list: List<Any>){
    this.visibility = if(list.isNullOrEmpty()) View.GONE else View.VISIBLE
}

fun TextView.setVisibilityListEmpty(list: List<Any>){
    this.visibility = if(list.isNullOrEmpty()) View.VISIBLE else View.GONE
}
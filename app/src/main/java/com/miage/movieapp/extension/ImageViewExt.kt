package com.miage.movieapp.extension

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import com.example.core.api.service.TheMovieAPI
import com.example.core.models.api.Movie
import com.miage.movieapp.R
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.squareup.picasso.Transformation
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation

private fun ImageView.setDefaultIfNullOrBlank(path: String?): Boolean {
    if (path.isNullOrBlank()) {
        this.setImageResource(R.drawable.ic_baseline_image_24)
        return true
    }
    return false
}

private fun ImageView.loadPicasso(url: String, transformation: Transformation? = null, callback: Callback? = null) {

    var request = Picasso.get().load(url).fit()
                    .error(R.drawable.ic_baseline_image_24)

    transformation?.let { request = request.transform(transformation) }

    if(callback == null)
        request.into(this)
    else
        request.into(this, callback)
}

fun ImageView.loadPicassoWithProgress(url: String, progressBar: ProgressBar, transformation: Transformation? = null) {
    progressBar.visibility = View.VISIBLE
    val callback = object : Callback {
        override fun onSuccess() { progressBar.visibility = View.GONE }
        override fun onError(e: Exception?) { progressBar.visibility = View.GONE }
    }
    this.loadPicasso(url, transformation, callback)
}

fun ImageView.setPosterImage(path: String?){
    if(this.setDefaultIfNullOrBlank(path)){
        return
    }
    val url = TheMovieAPI.getPosterUrl(path!!)
    this.loadPicasso(url, RoundedCornersTransformation(4, 1))
}

fun ImageView.setAvatarImage(path: String?){
    if(this.setDefaultIfNullOrBlank(path)){
        return
    }
    val url = TheMovieAPI.getAvatarUrl(path!!);
    this.loadPicasso(url, RoundedCornersTransformation(4, 1))
}

fun ImageView.setProfileImage(path: String?) {
    if(this.setDefaultIfNullOrBlank(path)){
        return
    }
    val url = TheMovieAPI.getProfileUrl(path!!);
    this.loadPicasso(url)
}

fun ImageView.setBackdropImageWithLoading(path: String?, progressBar: ProgressBar) {
    if(this.setDefaultIfNullOrBlank(path)){
        return
    }
    val url = TheMovieAPI.getBackdropUrl(path!!);
    this.loadPicassoWithProgress(url, progressBar, RoundedCornersTransformation(4, 1))
}

fun ImageView.setVideoThumbnail(youtubeId: String?) {
    if(this.setDefaultIfNullOrBlank(youtubeId)){
        return
    }
    val url =TheMovieAPI.getYoutubeImageUrl(youtubeId!!)
    this.loadPicasso(url)
}

fun ImageView.setBackdropMovieImageWithLoading(movie: Movie?, progressBar: ProgressBar) {
    movie?.let {
        if(this.setDefaultIfNullOrBlank(it.backdropPath)){
            return
        }
        val url = TheMovieAPI.getBackdropUrl(it.backdropPath!!)
        this.loadPicassoWithProgress(url, progressBar, RoundedCornersTransformation(4, 1))
    }
}
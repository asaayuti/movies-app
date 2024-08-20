package com.example.core.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions


fun ImageView.loadImage(url: String) {
    val posterUrl = "https://image.tmdb.org/t/p/w500$url"
    var requestOptions = RequestOptions()
    requestOptions = requestOptions.transform(RoundedCorners(30))

    Glide.with(this.context)
        .load(posterUrl)
        .apply(requestOptions)
        .into(this)
}

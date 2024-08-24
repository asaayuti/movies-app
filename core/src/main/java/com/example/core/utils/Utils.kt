package com.example.core.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions


fun ImageView.loadImage(url: String) {
    val posterUrl = Constants.IMAGE_URL + url
    val requestOptions = RequestOptions().transform(RoundedCorners(30))

    Glide.with(this.context)
        .load(posterUrl)
        .override(500, 600)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .apply(requestOptions)
        .placeholder(android.R.color.darker_gray)
        .into(this)
}

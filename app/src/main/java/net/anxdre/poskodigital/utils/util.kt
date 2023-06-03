package net.anxdre.poskodigital.utils

import android.widget.ImageView
import com.squareup.picasso.Picasso
import net.anxdre.poskodigital.R

fun getImage(imageUrl: String?, imageView: ImageView) {
    Picasso.get().load("http://poskodigitalsatgaspangan.net/assets/images/dokumentasi/$imageUrl")
        .into(imageView)
}

fun fetchImage(imageUrl: String?, imageView: ImageView) {
    Picasso.get().load(imageUrl)
        .centerCrop()
        .resize(1280,1280)
        .placeholder(R.drawable.ic_baseline_broken_image_24)
        .into(imageView)
}
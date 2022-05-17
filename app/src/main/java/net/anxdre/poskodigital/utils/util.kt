package net.anxdre.poskodigital.utils

import android.widget.ImageView
import com.squareup.picasso.Picasso

fun getImage(imageUrl: String?, imageView: ImageView) {
    Picasso.get().load("http://poskodigitalsatgaspangan.net/assets/images/dokumentasi/$imageUrl")
        .into(imageView)
}

package com.example.myapplication.presentation.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

@BindingAdapter("srcUrl")
fun setAvatarSrcUrl(imageView: ImageView, srcUrl: String?) {
    Glide.with(imageView.context)
        .load(srcUrl)
        .circleCrop()
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(imageView)
}
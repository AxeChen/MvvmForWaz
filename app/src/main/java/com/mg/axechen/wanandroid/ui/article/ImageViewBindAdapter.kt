package com.mg.axechen.wanandroid.ui.article

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object ImageViewBindAdapter {

    @BindingAdapter("android:imageUrl")
    @JvmStatic
    fun loadImage(imageView: ImageView, url: String) {
        Glide.with(imageView.getContext()).asBitmap()
            .load(url)
            .into(imageView);
    }

}
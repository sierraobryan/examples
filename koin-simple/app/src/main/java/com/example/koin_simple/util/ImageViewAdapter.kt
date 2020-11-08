package com.example.koin_simple.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter

object ImageViewAdapter {

    @JvmStatic
    @BindingAdapter("imageUrl", "crossFade", requireAll = false)
    fun setImage(view: ImageView, imageUrl: String, crossFade: Boolean = true) {
        // set my image view
    }

}
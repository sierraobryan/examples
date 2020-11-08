package com.example.koin_simple.util

import android.view.View
import androidx.databinding.BindingAdapter

object ViewAdapters {
    @JvmStatic
    @BindingAdapter("visible")
    fun setVisible(view: View, visible: Boolean) {
        if (visible && view.visibility != View.VISIBLE) {
            view.visibility = View.VISIBLE
        }
        view.visibility = if (visible) View.VISIBLE else View.INVISIBLE
    }
}
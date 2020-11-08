package com.example.hilt_simple.util

import androidx.recyclerview.widget.RecyclerView

abstract class ArrayAdapter<Item, ItemViewHolder : RecyclerView.ViewHolder> : RecyclerView.Adapter<ItemViewHolder>() {

    private var items: List<Item> = ArrayList()

    fun setItems(items: List<Item>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun getItemCount() = items.size

    protected fun getItemAtPosition(position: Int) = items[position]
}
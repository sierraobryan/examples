package com.sierraobryan.example.mlkitvision

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.tensorflow.lite.support.label.Category

class TFImageAdapter(val data: List<Category>) : RecyclerView.Adapter<TFImageAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.row_item_label, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val label: TextView = itemView.findViewById(R.id.label)
        private val confidence: TextView = itemView.findViewById(R.id.confidence)

        fun bind(category: Category) {
            label.text = category.label
            confidence.text = String.format(
                itemView.resources.getString(R.string.confidence_format),
                category.score * 100
            )
        }

    }

}
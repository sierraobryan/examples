package com.sierraobryan.example.mlkitvision

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.mlkit.vision.label.ImageLabel

class LabelAdapter(val data: List<ImageLabel>) : RecyclerView.Adapter<LabelAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LabelAdapter.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.row_item_label, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: LabelAdapter.ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val label: TextView = itemView.findViewById(R.id.label)
        private val confidence: TextView = itemView.findViewById(R.id.confidence)
        private val index: TextView = itemView.findViewById(R.id.index)

        fun bind(imageLabel: ImageLabel) {
            label.text = imageLabel.text
            confidence.text = String.format(
                itemView.resources.getString(R.string.confidence_format),
                imageLabel.confidence.toString()
            )
            index.text = imageLabel.index.toString()
        }

    }

}
package ru.practicum.android.diploma.ui.country

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R

class OtherRegionsViewHolder(
    itemView: View,
    private val onOtherRegionsClick: () -> Unit
) : RecyclerView.ViewHolder(itemView) {
    private val otherRegions: TextView = itemView.findViewById(R.id.tvOtherRegions)

    fun bind() {
        otherRegions.setText(R.string.other_regions)
        itemView.setOnClickListener {
            onOtherRegionsClick()
        }
    }
}

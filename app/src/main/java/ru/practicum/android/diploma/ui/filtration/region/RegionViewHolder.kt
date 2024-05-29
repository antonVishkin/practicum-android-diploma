package ru.practicum.android.diploma.ui.filtration.region

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R

class RegionViewHolder(
    itemView: View,
    private val onClick: (String) -> Unit,
) : RecyclerView.ViewHolder(itemView) {
    private val regionName: TextView = itemView.findViewById(R.id.tvRegionName)

    fun bind(region: String) {
        regionName.text = region
        itemView.setOnClickListener {
            onClick(region)
        }
    }
}

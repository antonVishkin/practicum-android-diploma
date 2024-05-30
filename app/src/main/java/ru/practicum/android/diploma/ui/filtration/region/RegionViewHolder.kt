package ru.practicum.android.diploma.ui.filtration.region

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.Country

class RegionViewHolder(
    itemView: View,
    private val onClick: (String, String) -> Unit,
) : RecyclerView.ViewHolder(itemView) {
    private val regionName: TextView = itemView.findViewById(R.id.tvRegionName)

    fun bind(name: String, id: String) {
        regionName.text = name
        itemView.setOnClickListener { onClick(name, id) }
    }
}

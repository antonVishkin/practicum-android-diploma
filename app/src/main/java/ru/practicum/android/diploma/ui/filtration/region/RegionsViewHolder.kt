package ru.practicum.android.diploma.ui.filtration.region

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.Country

class RegionsViewHolder(
    itemView: View,
    private val onClick: (String, String) -> Unit,
) : RecyclerView.ViewHolder(itemView) {
    private val regionName: TextView = itemView.findViewById(R.id.tvRegionName)

    fun bind(region: Country) {
        regionName.text = region.name
        itemView.setOnClickListener {
            onClick(region.name, region.id)
        }
    }
}

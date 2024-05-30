package ru.practicum.android.diploma.ui.filtration.country

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R

class CountryViewHolder(
    itemView: View,
    private val onClick: (String, String) -> Unit,
) : RecyclerView.ViewHolder(itemView) {
    private val countryName: TextView = itemView.findViewById(R.id.tvCountryName)

    fun bind(country: String, countryId: String) {
        countryName.text = country
        itemView.setOnClickListener {
            onClick(country, countryId)
        }
    }
}

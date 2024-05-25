package ru.practicum.android.diploma.ui.country

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R

class CountryAdapter(
    private val countries: List<String>,
    private val onClick: (String) -> Unit,
    private val onOtherRegionsClick: () -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_COUNTRY = 0
        private const val TYPE_OTHER_REGIONS = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == TYPE_COUNTRY) {
            val view = inflater.inflate(R.layout.country_view, parent, false)
            CountryViewHolder(view, onClick)
        } else {
            val view = inflater.inflate(R.layout.other_regions_view, parent, false)
            OtherRegionsViewHolder(view, onOtherRegionsClick)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CountryViewHolder) {
            holder.bind(countries[position])
            holder.itemView.setOnClickListener { onClick(countries[position]) }
        } else if (holder is OtherRegionsViewHolder) {
            holder.bind()
            holder.itemView.setOnClickListener { onOtherRegionsClick() }
        }
    }

    override fun getItemCount(): Int = minOf(countries.size, 8) + 1

    override fun getItemViewType(position: Int): Int {
        return if (position < 8 && position < countries.size) {
            TYPE_COUNTRY
        } else {
            TYPE_OTHER_REGIONS
        }
    }
}

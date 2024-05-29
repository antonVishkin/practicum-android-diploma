package ru.practicum.android.diploma.ui.filtration.region

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.Country

class RegionsAdapter(
    private var regions: List<Country>,
    private val onClick: (String, String) -> Unit
) : RecyclerView.Adapter<RegionsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegionsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.region_view, parent, false)
        return RegionsViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: RegionsViewHolder, position: Int) {
        val region = regions[position]
        holder.bind(region)
    }

    override fun getItemCount(): Int = regions.size

    fun updateRegions(newRegions: List<Country>) {
        regions = newRegions
        notifyDataSetChanged()
    }
}

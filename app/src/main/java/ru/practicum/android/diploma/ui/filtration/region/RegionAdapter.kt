package ru.practicum.android.diploma.ui.filtration.region

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.Region

class RegionAdapter(
    private var regions: List<Region>,
    private val onClick: (String, String) -> Unit
) : RecyclerView.Adapter<RegionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.region_view, parent, false)
        return RegionViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: RegionViewHolder, position: Int) {
        val region = regions[position]
        holder.bind(region.name, region.id)
    }

    override fun getItemCount(): Int = regions.size

    fun updateRegions(newRegions: List<Region>) {
        regions = newRegions
        notifyDataSetChanged()
    }
}

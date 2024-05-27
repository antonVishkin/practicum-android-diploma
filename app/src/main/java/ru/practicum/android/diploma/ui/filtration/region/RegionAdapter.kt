package ru.practicum.android.diploma.ui.filtration.region

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.Country
import ru.practicum.android.diploma.ui.filtration.country.CountryViewHolder

class RegionAdapter (
    private var countries: List<Country>,
    private val onClick: (String, String) -> Unit
) : RecyclerView.Adapter<RegionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.country_view, parent, false)
        return RegionViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: RegionViewHolder, position: Int) {
        val country = countries[position]
        holder.bind(country)
    }

    override fun getItemCount(): Int = countries.size

    fun updateCountries(newCountries: List<Country>) {
        countries = newCountries
        notifyDataSetChanged()
    }
}

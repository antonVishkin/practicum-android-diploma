package ru.practicum.android.diploma.ui.filtration.industry

import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.IndustryViewBinding
import ru.practicum.android.diploma.domain.models.Industry

class IndustryViewHolder(private val binding: IndustryViewBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Industry, isSelected: Boolean) {
        binding.tvIndustryName.text = item.name
        binding.cbSelectIndustry.isChecked = isSelected
    }
}

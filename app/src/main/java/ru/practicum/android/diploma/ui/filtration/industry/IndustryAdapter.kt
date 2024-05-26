package ru.practicum.android.diploma.ui.filtration.industry

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.practicum.android.diploma.databinding.IndustryViewBinding
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.domain.models.IndustryUi
import ru.practicum.android.diploma.util.IndustryDiffCallback

class IndustryAdapter(private val onClick: (Industry) -> Unit) : ListAdapter<IndustryUi, IndustryViewHolder>(
    IndustryDiffCallback()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IndustryViewHolder {
        val binding = IndustryViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return IndustryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IndustryViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item.industry, item.isSelected)
        holder.itemView.setOnClickListener { onClick.invoke(currentList[holder.adapterPosition].industry) }
    }
}

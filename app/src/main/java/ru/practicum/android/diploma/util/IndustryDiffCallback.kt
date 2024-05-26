package ru.practicum.android.diploma.util

import androidx.recyclerview.widget.DiffUtil
import ru.practicum.android.diploma.domain.models.IndustryUi

class IndustryDiffCallback : DiffUtil.ItemCallback<IndustryUi>() {
    override fun areItemsTheSame(oldItem: IndustryUi, newItem: IndustryUi): Boolean {
        return oldItem.industry.id == newItem.industry.id
    }

    override fun areContentsTheSame(oldItem: IndustryUi, newItem: IndustryUi): Boolean {
        return oldItem == newItem
    }
}

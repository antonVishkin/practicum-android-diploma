package ru.practicum.android.diploma.ui.vacancies

import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.ContactsViewBinding
import ru.practicum.android.diploma.domain.models.Phone
import ru.practicum.android.diploma.util.ContactsFormat

class ContactsViewHolder(private val binding: ContactsViewBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(phone: Phone) {
        val formatNumber = ContactsFormat.numberFormat(phone)
        binding.apply {
            tvPhoneNumber.text = formatNumber
            tvComment.text = phone.comment
        }
    }
}

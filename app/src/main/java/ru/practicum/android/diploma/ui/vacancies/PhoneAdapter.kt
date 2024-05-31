package ru.practicum.android.diploma.ui.vacancies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.ContactsViewBinding
import ru.practicum.android.diploma.domain.models.Phone

class PhoneAdapter(
    private val phones: List<Phone?>,
    private val onClickListener: (Phone) -> Unit
) : RecyclerView.Adapter<PhoneViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhoneViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PhoneViewHolder(ContactsViewBinding.inflate(layoutInflater, parent, false))
    }

    override fun getItemCount() = phones.size

    override fun onBindViewHolder(holder: PhoneViewHolder, position: Int) {
        phones[position]?.let { holder.bind(it) }
        holder.itemView.setOnClickListener {
            phones[position]?.let { phone -> onClickListener.invoke(phone) }
        }
    }
}

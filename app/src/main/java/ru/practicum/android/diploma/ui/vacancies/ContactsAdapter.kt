package ru.practicum.android.diploma.ui.vacancies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.ContactsViewBinding
import ru.practicum.android.diploma.domain.models.Phone

class ContactsAdapter(
    private val phones: List<Phone?>,
    private val onClickListener: (Phone) -> Unit
) : RecyclerView.Adapter<ContactsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ContactsViewHolder(ContactsViewBinding.inflate(layoutInflater, parent, false))
    }

    override fun getItemCount() = phones.size

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        phones[position]?.let { holder.bind(it) }
        holder.itemView.setOnClickListener {
            phones[position]?.let { phone -> onClickListener.invoke(phone) }
        }
    }
}
